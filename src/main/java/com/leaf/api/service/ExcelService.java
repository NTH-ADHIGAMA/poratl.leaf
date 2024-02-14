package com.leaf.api.service;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leaf.api.modal.ProjectDatasetLink;
import com.leaf.api.modal.ProjectTablesColumnsLink;
import com.leaf.api.modal.ProjectTablesLink;
import com.leaf.api.modal.ProjectTablesDataLink;
import com.leaf.api.repository.ProjectDatasetLinkRepository;
import com.leaf.api.repository.ProjectTablesColumnsLinkRepository;
import com.leaf.api.repository.ProjectTablesDataLinkRepository;
import com.leaf.api.repository.ProjectTablesLinkRepository;
import com.leaf.api.controller.DynamicTable;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class ExcelService {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ProjectDatasetLinkRepository projectDatasetRepo;

	@Autowired
	private ProjectTablesLinkRepository projectTableRepo;

	@Autowired
	private ProjectTablesColumnsLinkRepository projectTablesColRepo;

	@Autowired
	private ProjectTablesDataLinkRepository projectTablesDataRepo;

	@Transactional
	public void processFile(InputStream fileStream, int projectId, String extension,String fileName) throws IOException, CsvValidationException {
		// create ProjectDatasetLinkRepository transaction
		ProjectDatasetLink projectDataSet = new ProjectDatasetLink();
		projectDataSet.setName("Need identiFy");
		projectDataSet.setProject(projectId);
		projectDataSet.setDescription("Description");
		projectDataSet.setStatus("open");
		projectDataSet.setRecordStatus(true);
		ProjectDatasetLink pdsId = projectDatasetRepo.save(projectDataSet);
		System.out.println("=Project data set created=====");

		if (extension != null && (extension.equals("xls") || extension.equals("xlsx"))) {

			processExcelFile(fileStream, projectId, pdsId.getId());

		} else if (extension != null && extension.equals("csv")) {
			processCsvFile(fileStream, projectId, pdsId.getId(),fileName);

		} else {
			throw new IllegalArgumentException("Unsupported file format");
		}

	}

	public void processExcelFile(InputStream fileStream, int projectId, int datasetId) throws IOException {

		Workbook workbook = WorkbookFactory.create(fileStream);
		for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName();
			ProjectTablesLink projectTable = new ProjectTablesLink();
			projectTable.setProject(projectId);
			projectTable.setDataSet(datasetId);
			projectTable.setName(sheetName);
			projectTable.setStatus("open");
			projectTable.setRecordStatus(true);
			ProjectTablesLink ptId = projectTableRepo.save(projectTable);
			System.out.println("=Project table created=====");
			Iterator<Row> rowIterator = sheet.iterator();
			if (rowIterator.hasNext()) {
				Row headerRow = rowIterator.next();
				Map<String, String> columnHeaders = new HashMap<>();
				int rowNo = 0;
				while (rowIterator.hasNext()) {
					Row currentRow = rowIterator.next();

					DynamicTable dynamicTable = new DynamicTable();
					Map<String, String> dataMap = new HashMap<>();

					for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
						Cell headerCell = headerRow.getCell(i);
						Cell currentCell = currentRow.getCell(i);
						CellType cellType = currentCell.getCellType();
						String header = getStringValue(headerCell);
						String value = getStringValue(currentCell);
						if (rowNo == 1) {
							ProjectTablesColumnsLink ptcl = new ProjectTablesColumnsLink();
							ptcl.setTable(ptId.getId());
							ptcl.setStatus("open");
							ptcl.setRecordStatus(true);
							ptcl.setColumnIndex(i + 1);
							ptcl.setName(header);
							ptcl.setDataType(cellType.toString());
							System.out.println("=ptcl=====" + ptcl.toString());
							ProjectTablesColumnsLink ptclObj = projectTablesColRepo.save(ptcl);
							System.out.println("=Project data header created=====");
						}
						ProjectTablesDataLink ptdl = new ProjectTablesDataLink();
						ptdl.setRecordStatus(true);
						ptdl.setRowIndex(rowNo + 1);
						ptdl.setColumnIndex(i + 1);
						ptdl.setTable(ptId.getId());
						ptdl.setData(value);
						projectTablesDataRepo.save(ptdl);
						System.out.println("=Project data created=====");
					}

					rowNo++;

				}
			}
		}

		workbook.close();
	}

	public void processCsvFile(InputStream fileStream, int projectId, int datasetId,String fileName)
			throws IOException, CsvValidationException {

		CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(fileStream)).build();

		String[] headerRow = csvReader.readNext();
		if (headerRow != null) {
			Map<Integer, String> columnHeaders = new HashMap<>();

			ProjectTablesLink projectTable = new ProjectTablesLink();
			projectTable.setProject(projectId);
			projectTable.setDataSet(datasetId);
			projectTable.setName(fileName.trim()); // Change this to your desired name
			projectTable.setStatus("open");
			projectTable.setRecordStatus(true);
			ProjectTablesLink ptId = projectTableRepo.save(projectTable);
			System.out.println("=Project table created=====");

			for (int i = 0; i < headerRow.length; i++) {
				String header = headerRow[i];
				columnHeaders.put(i, header);

				ProjectTablesColumnsLink ptcl = new ProjectTablesColumnsLink();
				ptcl.setTable(ptId.getId());
				ptcl.setStatus("open");
				ptcl.setRecordStatus(true);
				ptcl.setColumnIndex(i + 1);
				ptcl.setName(header);
				ptcl.setDataType("String"); // Assuming all CSV values are strings
				ProjectTablesColumnsLink ptclObj = projectTablesColRepo.save(ptcl);
				System.out.println("=Project data header created=====");
			}

			Iterator<String[]> csvIterator = csvReader.iterator();
			int rowNo = 0;

			while (csvIterator.hasNext()) {
				String[] currentRow = csvIterator.next();


				for (int i = 0; i < currentRow.length; i++) {
					String value = currentRow[i];
					ProjectTablesDataLink ptdl = new ProjectTablesDataLink();
					ptdl.setRecordStatus(true);
					ptdl.setStatus("open");
					ptdl.setRowIndex(rowNo + 1);
					ptdl.setTable(ptId.getId());
					ptdl.setColumnIndex(i + 1);
					ptdl.setData(value);
					projectTablesDataRepo.save(ptdl);
					System.out.println("=Project data created=====");
				}

				rowNo++;
			}
		}

		csvReader.close();
	}

	private String getStringValue(Cell cell) {
		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		// Handle other cell types as needed
		default:
			return null;
		}
	}

	private void createDynamicTable(Map<String, String> columnHeaders) {
		StringBuilder query = new StringBuilder(
				"CREATE TABLE IF NOT EXISTS dynamic_table (id BIGINT AUTO_INCREMENT PRIMARY KEY, ");
		for (Map.Entry<String, String> entry : columnHeaders.entrySet()) {
			query.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
		}
		query.delete(query.length() - 2, query.length()); // Remove the last comma and space
		query.append(")");

		entityManager.createNativeQuery(query.toString()).executeUpdate();
	}
}
