package com.leaf.api.service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leaf.api.modal.ProjectTablesColumnsLink;
import com.leaf.api.modal.ProjectTablesDataLink;
import com.leaf.api.modal.ProjectTablesLink;
import com.leaf.api.repository.ProjectDatasetLinkRepository;
import com.leaf.api.repository.ProjectTablesColumnsLinkRepository;
import com.leaf.api.repository.ProjectTablesDataLinkRepository;
import com.leaf.api.repository.ProjectTablesLinkRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
@Service
public class ExcelService {
    @Autowired  private EntityManager entityManager;
    @Autowired  private ProjectDatasetLinkRepository projectDatasetRepo;
    @Autowired  private ProjectTablesLinkRepository projectTableRepo;
    @Autowired  private ProjectTablesColumnsLinkRepository projectTablesColRepo;
    @Autowired  private ProjectTablesDataLinkRepository projectTablesDataRepo;

    @Value("${media.path}")  private String mediaPath;

    @Transactional
	public void processAndCopyFile(InputStream fileStream, int projectId, int dataSetId, String fileName, String fileExtension) throws IOException, CsvValidationException {
        // The implementation of processAndCopyFile method
        if ("xls".equals(fileExtension) || "xlsx".equals(fileExtension)) {
            processAndCopyExcelFile(fileStream, projectId, dataSetId, fileName);
        } else if ("csv".equals(fileExtension)) {
        	processAndCopyCsvFile(fileStream, projectId, dataSetId, fileName);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    public void processAndCopyExcelFile(InputStream fileStream, int projectId, int datasetId, String fileName) throws IOException {
        Workbook workbook = WorkbookFactory.create(fileStream);
        Workbook cleansedWorkbook = new XSSFWorkbook();
        System.out.println("processAndCopyExcelFile");
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Future<Void>> futures = new ArrayList<>();

        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet originalSheet = workbook.getSheetAt(sheetIndex);
            Sheet cleansedSheet = cleansedWorkbook.createSheet(originalSheet.getSheetName());

            Future<Void> future = executorService.submit(() -> {
                processExcelSheet(originalSheet, cleansedSheet, projectId, datasetId);
                return null;
            });

            futures.add(future);
        }

        // Shutdown the executor service
        executorService.shutdown();

        try {
            // Wait for all threads to finish
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Your existing file copy logic
        Path outputPath = Paths.get(mediaPath+"/"+"leaf-doc-copy", String.valueOf(projectId), "cleansed_" + fileName);
        Files.createDirectories(outputPath.getParent());

        try (FileOutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
            cleansedWorkbook.write(outputStream);
        }

        // Database operations for the cleansed data
        for (int sheetIndex = 0; sheetIndex < cleansedWorkbook.getNumberOfSheets(); sheetIndex++) {
            Sheet cleansedSheet = cleansedWorkbook.getSheetAt(sheetIndex);
            processSheet(cleansedSheet, projectId, datasetId);
        }

        System.out.println("Cleansing process for Excel file completed. Output file: " + outputPath);

        workbook.close();
    }

    private void processExcelSheet(Sheet originalSheet, Sheet cleansedSheet, int projectId, int datasetId) {
        Set<String> uniqueRows = new HashSet<>();
        AtomicInteger rowIndex = new AtomicInteger(0);
        System.out.println("processExcelSheet");
        for (Row row : originalSheet) {
            if (isRowEmpty(row)) {
                System.out.println("Skipping empty row at index " + rowIndex.get());
                continue;
            }

            List<String> rowValues = new ArrayList<>();
            boolean isDuplicate = false;

            for (Cell cell : row) {
                String cellValue = cell.toString();
                String cleansedValue = cellValue.trim();

                rowValues.add(cleansedValue);
            }

            String rowString = String.join(",", rowValues);

            if (!uniqueRows.contains(rowString)) {
                uniqueRows.add(rowString);
                Row cleansedRow = cleansedSheet.createRow(rowIndex.getAndIncrement());

                for (int i = 0; i < rowValues.size(); i++) {
                    cleansedRow.createCell(i).setCellValue(rowValues.get(i));
                }
            } else {
                System.out.println("Duplicate row detected and not added to the cleansed data: " + rowString);
                isDuplicate = true;
            }

            System.out.println("Sheet: " + originalSheet.getSheetName() +
                    ", Row " + rowIndex.get() + " Cleansed: " + rowValues + (isDuplicate ? " (Duplicate)" : ""));
        }
        // Remove the following line, as it's not needed
        // processSheet(cleansedSheet, projectId, datasetId);
    }

    private boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell.getCellType() != CellType.BLANK && !cell.toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void processSheet(Sheet sheet, int projectId, int datasetId) {
        ProjectTablesLink projectTable = new ProjectTablesLink();
        projectTable.setProject(projectId);
        projectTable.setDataSet(datasetId);
        projectTable.setName(sheet.getSheetName());
        projectTable.setStatus("open");
        projectTable.setRecordStatus(true);
        ProjectTablesLink ptId = projectTableRepo.save(projectTable);

        List<ProjectTablesColumnsLink> columnsToSave = new ArrayList<>();
        List<ProjectTablesDataLink> dataToSave = new ArrayList<>();

        // Process header row
        Row headerRow = sheet.getRow(0); // Assuming the first row is the header
        int numColumns = headerRow.getPhysicalNumberOfCells();
        for (int i = 0; i < numColumns; i++) {
            Cell headerCell = headerRow.getCell(i);
            String columnName = headerCell.getStringCellValue();
            ProjectTablesColumnsLink ptcl = new ProjectTablesColumnsLink();
            ptcl.setTable(ptId.getId());
            ptcl.setStatus("open");
            ptcl.setRecordStatus(true);
            ptcl.setColumnIndex(i + 1);
            ptcl.setName(columnName);
            ptcl.setDataType(headerCell.getCellType().toString());
            columnsToSave.add(ptcl);
            System.out.println("Processing header column: " + columnName);
        }

        // Process data rows
        int batchSize = 1000; // Batch size for inserting data
        int rowNum = 0;
        for (Row currentRow : sheet) {
            if (rowNum > 0) { // Skip the header row
                for (int i = 0; i < numColumns; i++) {
                    Cell currentCell = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String cellValue = currentCell != null ? currentCell.toString() : null;
                    ProjectTablesDataLink ptdl = new ProjectTablesDataLink();
                    ptdl.setRecordStatus(true);
                    ptdl.setRowIndex(rowNum);
                    ptdl.setColumnIndex(i + 1);
                    ptdl.setTable(ptId.getId());
                    ptdl.setData(cellValue);
                    dataToSave.add(ptdl);

                    if (dataToSave.size() >= batchSize) {
                        batchInsertColumnsAndData(columnsToSave, dataToSave);
                        dataToSave.clear();
                    }
                    System.out.println("Processing data row: " + rowNum);
                }
            }
            rowNum++;
        }
        // Insert any remaining data
        batchInsertColumnsAndData(columnsToSave, dataToSave);
        System.out.println("Sheet processing completed for sheet: " + sheet.getSheetName());
    }



    public void processAndCopyCsvFile(InputStream fileStream, int projectId, int dataSetId, String fileName)
            throws IOException, CsvValidationException {
        // Read the CSV file, perform cleansing, and copy the cleansed file
    	System.out.println("processAndCopyCsvFile:");
        // Declare outputPath variable
        Path outputPath = null;

        // Your cleansing logic (modify as needed)
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(fileStream))) {
            List<String[]> cleansedData = new ArrayList<>();
            Set<String> uniqueRows = new HashSet<>();

            String[] nextLine;
            int rowIndex = 0;

            // Use ExecutorService for multithreading
            ExecutorService executorService = Executors.newFixedThreadPool(5);

            while ((nextLine = csvReader.readNext()) != null) {
                final int currentIndex = rowIndex;
                final String[] currentLineCopy = Arrays.copyOf(nextLine, nextLine.length);
                executorService.submit(() -> processRowAndCopy(currentIndex, currentLineCopy, cleansedData, uniqueRows, projectId, fileName));
                rowIndex++;
            }

            // Shutdown the executor service to wait for all threads to complete
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Specify the output file path
            outputPath = Paths.get(mediaPath+"/"+"leaf-doc-copy", String.valueOf(projectId));
            Files.createDirectories(outputPath);

            // Append the file name to the output path
            outputPath = outputPath.resolve("cleansed_" + fileName);

            // Use FileWriter to write the cleansed data
            try (FileWriter fileWriter = new FileWriter(outputPath.toFile());
                 CSVWriter csvWriter = new CSVWriter(fileWriter)) {

                // Write the cleansed data to the output CSV file
                csvWriter.writeAll(cleansedData);
            }
            processCsvFile(projectId, dataSetId, fileName, cleansedData);
            // Log the cleansing process completion
            System.out.println("Cleansing process for CSV file completed. Output file: " + outputPath);

        } catch (CsvValidationException e) {
            // Handle CsvValidationException here
            e.printStackTrace();
            throw e;  // rethrow the exception if necessary
        }
    }

    private void processRowAndCopy(int rowIndex, String[] nextLine, List<String[]> cleansedData,
            Set<String> uniqueRows, int projectId, String fileName) {
        // Skip empty rows
        if (isRowEmpty(nextLine)) {
            System.out.println("Skipping empty row at index " + rowIndex);
            return;
        }

        // Convert the row to a string for duplicate checking
        String currentRowString = Arrays.toString(nextLine);

        // Check for duplicate row based on the entire row
        if (uniqueRows.contains(currentRowString)) {
            // Log the duplicate row
            System.out.println("Duplicate row removed: " + String.join(",", nextLine));
            return;
        }

        // Log the cleansing process for each row
        System.out.println("Row::" + rowIndex + " Cleansed: " + String.join(",", nextLine));

        // Perform additional cleansing logic as needed
        // For example, removing leading/trailing whitespaces and converting to lowercase
        String[] cleansedLine = new String[nextLine.length];
        for (int i = 0; i < nextLine.length; i++) {
            cleansedLine[i] = nextLine[i].trim();
        }

        cleansedData.add(cleansedLine);
        uniqueRows.add(currentRowString);  // Add the current row to the set of unique rows
    }

    private boolean isRowEmpty(String[] row) {
        for (String cell : row) {
            if (cell != null && !cell.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }


    public void processCsvFile(int projectId, int dataSetId, String fileName, List<String[]> cleansedData) {
    	System.out.println("processCsvFile:");
    	 String[] headerRow = (cleansedData != null && !cleansedData.isEmpty()) ? cleansedData.get(0) : null;

 	    if (headerRow != null) {
 	    	System.out.println("headerRow: "+headerRow);
 	        ProjectTablesLink projectTable = new ProjectTablesLink();
 	        projectTable.setProject(projectId);
 	        projectTable.setDataSet(dataSetId);
 	        projectTable.setName(fileName.trim());
 	        projectTable.setStatus("open");
 	        projectTable.setRecordStatus(true);
 	        ProjectTablesLink ptId = projectTableRepo.save(projectTable);

 	        List<ProjectTablesColumnsLink> columnsToSave = new ArrayList<>();
 	        List<ProjectTablesDataLink> dataToSave = new ArrayList<>();

 	        for (int i = 0; i < headerRow.length; i++) {
 	            ProjectTablesColumnsLink ptcl = new ProjectTablesColumnsLink();
 	            ptcl.setTable(ptId.getId());
 	            ptcl.setStatus("open");
 	            ptcl.setRecordStatus(true);
 	            ptcl.setColumnIndex(i + 1);
 	            ptcl.setName(headerRow[i]);
 	            ptcl.setDataType("String"); // Assuming all CSV values are strings
 	            columnsToSave.add(ptcl);
 	        }

 	        int batchSize = 1000; // Batch size for inserting data
 	        int rowNum = 0;
 	        for (String[] currentRow : cleansedData) {
 	            for (int i = 0; i < currentRow.length; i++) {
 	                ProjectTablesDataLink ptdl = new ProjectTablesDataLink();
 	                ptdl.setRecordStatus(true);
 	                ptdl.setStatus("open");
 	                ptdl.setRowIndex(rowNum + 1);
 	                ptdl.setTable(ptId.getId());
 	                ptdl.setColumnIndex(i + 1);
 	                ptdl.setData(currentRow[i]);
 	                dataToSave.add(ptdl);

 	                if (dataToSave.size() >= batchSize) {
 	                    batchInsertColumnsAndData(columnsToSave, dataToSave);
 	                    dataToSave.clear();
 	                }
 	            }
 	            rowNum++;
 	        }

 	        // Insert any remaining data
 	        batchInsertColumnsAndData(columnsToSave, dataToSave);
 	    }
    }


    private void batchInsertColumnsAndData(List<ProjectTablesColumnsLink> columns, List<ProjectTablesDataLink> data) {
        // Use EntityManager to persist in batches
        entityManager.unwrap(Session.class).doWork(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO project_tables_columns_link (table_id, name, column_index, data_type, status, record_status) VALUES (?, ?, ?, ?, ?, ?)")) {
                for (ProjectTablesColumnsLink column : columns) {
                    statement.setInt(1, column.getTable());
                    statement.setString(2, column.getName());
                    statement.setInt(3, column.getColumnIndex());
                    statement.setString(4, column.getDataType());
                    statement.setString(5, column.getStatus());
                    statement.setBoolean(6, column.isRecordStatus());
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO project_tables_data_link (table_id, row_index, column_index, data, status, record_status) VALUES (?, ?, ?, ?, ?, ?)")) {
                for (ProjectTablesDataLink datum : data) {
                    statement.setInt(1, datum.getTable());
                    statement.setInt(2, datum.getRowIndex());
                    statement.setInt(3, datum.getColumnIndex());
                    statement.setString(4, datum.getData());
                    statement.setString(5, datum.getStatus());
                    statement.setBoolean(6, datum.isRecordStatus());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        });
    }


}
