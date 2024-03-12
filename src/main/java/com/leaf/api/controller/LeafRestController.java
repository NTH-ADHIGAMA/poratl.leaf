package com.leaf.api.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leaf.api.modal.ProjectDatasetLink;
import com.leaf.api.modal.ProjectTablesColumnsLink;
import com.leaf.api.modal.ProjectTablesDataLink;
import com.leaf.api.modal.ProjectTablesLink;
import com.leaf.api.modal.Projects;
import com.leaf.api.modal.Roles;
import com.leaf.api.modal.Users;
import com.leaf.api.repository.ProjectDatasetLinkRepository;
import com.leaf.api.repository.ProjectTablesColumnsLinkRepository;
import com.leaf.api.repository.ProjectTablesDataLinkRepository;
import com.leaf.api.repository.ProjectTablesLinkRepository;
import com.leaf.api.repository.ProjectsRepository;
import com.leaf.api.repository.RolesRepository;
import com.leaf.api.repository.UserRepository;
import com.leaf.api.service.ExcelService;
import com.opencsv.exceptions.CsvValidationException;

@CrossOrigin
@RestController
@RequestMapping("/leaf")
public class LeafRestController {

	@Autowired private EntityManager entityManager;
	@Autowired private ExcelService excelService;
	@Autowired private ProjectsRepository projectsRepo;
	@Autowired private ProjectDatasetLinkRepository projectDatasetRepo;
	@Autowired private ProjectTablesLinkRepository projectTableRepo;
	@Autowired private ProjectTablesColumnsLinkRepository projectTablesColRepo;
	@Autowired private ProjectTablesDataLinkRepository projectTablesDataRepo;
	@Autowired private UserRepository userRepository;
	@Autowired private RolesRepository rolesRepository;
	 @Value("${media.path}")  private String mediaPath;

	@GetMapping("/test")
	public String Test() {
		System.out.println("====leaf TEST=====");
		return "leaf test is valid";
	}

	@CrossOrigin
	@ResponseBody
	@PostMapping("/saveUser")
	public ResponseEntity<AjaxResponseBody> saveUser(@RequestParam("id") int id, @RequestBody Users user) {
		AjaxResponseBody result = new AjaxResponseBody();

		if (id > 0) {
			Users users = userRepository.findById(id).get();
			user.setId(id);
			user.setCreatedDate(users.getCreatedDate());
			user.setFirstLoginDate(users.getFirstLoginDate());
		} else {
			user.setCreatedDate(LocalDateTime.now());
			user.setFirstLoginDate(LocalDateTime.now());

		}

		user.setLastLoginDate(LocalDateTime.now());
		user.setLastModifiedDate(LocalDateTime.now());
		user.setStatus("active");
		user.setRecordStatus(true);
		Users newUser = userRepository.save(user);
		System.out.println("Provider Saved : " + newUser.getUsername());
		result.setMsg(newUser);
		return ResponseEntity.ok(result);
	}

	@RequestMapping("/getUsers")
	public ResponseEntity<AjaxResponseBody> getUsers() {
		AjaxResponseBody result = new AjaxResponseBody();
		List<Users> returnList = (List<Users>) userRepository.findAll();
		List<Roles> roles = (List<Roles>) rolesRepository.findAll();
		System.out.println("Users : " + returnList.size());
		result.setMsg(returnList);
		result.setMsg1(roles);
		return ResponseEntity.ok(result);
	}

	@RequestMapping("/getRoles")
	public ResponseEntity<AjaxResponseBody> getRoles() {
		AjaxResponseBody result = new AjaxResponseBody();
		List<Roles> returnList = (List<Roles>) rolesRepository.findAll();

		System.out.println("Users : " + returnList.size());
		result.setMsg(returnList);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/getUserDetails")
	public ResponseEntity<?> getUserDetails(@RequestParam("id") int id) {
		AjaxResponseBody result = new AjaxResponseBody();
		Users users = userRepository.findById(id).get();
		System.out.println("getUsername :: " + users.getUsername());
		System.out.println("getEmail :: " + users.getEmail());
		System.out.println("getPhone :: " + users.getPhone());
		result.setMsg(users);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/deleteUser")
	public ResponseEntity<?> deleteUser(@RequestParam("id") int userId) {
		AjaxResponseBody result = new AjaxResponseBody();

		try {
			userRepository.deleteById(userId);
			result.setMsg("success");
			return ResponseEntity.ok(result);
		} catch (EmptyResultDataAccessException e) {
			result.setMsg("User not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		} catch (Exception e) {
			result.setMsg("Failed to delete user");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@RequestMapping(value = "/getProjectDetails")
	public ResponseEntity<?> getProjectDetails(@RequestParam("id") int id) {
		AjaxResponseBody result = new AjaxResponseBody();
		Projects projects = projectsRepo.findById(id).get();
		System.out.println("getUsername :: " + projects.getName());
		System.out.println("getEmail :: " + projects.getCode());
		System.out.println("getPhone :: " + projects.getDescription());
		result.setMsg(projects);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/deleteProjects")
	public ResponseEntity<?> deleteProjects(@RequestParam("id") int projectId) {
		AjaxResponseBody result = new AjaxResponseBody();

		try {
			projectsRepo.deleteById(projectId);
			result.setMsg("success");
			return ResponseEntity.ok(result);
		} catch (EmptyResultDataAccessException e) {
			result.setMsg("User not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		} catch (Exception e) {
			result.setMsg("Failed to delete user");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@RequestMapping("/getProjects")
	public ResponseEntity<AjaxResponseBody> getProjects() {
		AjaxResponseBody result = new AjaxResponseBody();
		Iterable<Projects> returnList = projectsRepo.findAll();
		System.out.println("Project List====" + returnList);
		result.setMsg(returnList);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/saveProject")
	public ResponseEntity<AjaxResponseBody> saveProject(@RequestParam("id") int id, @RequestBody Projects projects) {
		AjaxResponseBody result = new AjaxResponseBody();

		if (id > 0) {
			Projects prjt = projectsRepo.findById(id).get();
			projects.setId(id);
			projects.setDateOfCreation(prjt.getDateOfCreation());
			projects.setLastModifiedDate(prjt.getLastModifiedDate());
		} else {
			projects.setDateOfCreation(LocalDateTime.now());
			projects.setLastModifiedDate(LocalDateTime.now());
		}
		projects.setStartDate(LocalDateTime.now());
		projects.setEndDate(LocalDateTime.now());
		projects.setLastModifiedDate(LocalDateTime.now());
		projects.setStatus("open");
		projects.setRecordStatus(true);
		Projects pj = projectsRepo.save(projects);
		result.setMsg(pj);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/upload1")
	public ResponseEntity<AjaxResponseBody> handleFileUpload1(@RequestParam("projectId") int projectId,
			@RequestPart("files") MultipartFile[] files) {
		AjaxResponseBody result = new AjaxResponseBody();
		try {
			System.out.println("Project ID: " + projectId);
			if (projectId <= 0 || files == null || files.length == 0) {
				result.setMsg("Invalid projectId or no files uploaded");
				return ResponseEntity.badRequest().body(result);
			}

			System.out.println("Number of Files: " + files.length);
			for (MultipartFile file : files) {
				MultipartFile copyFile = file;
				if (file.isEmpty()) {
					continue; // Skip empty files
				}

				String originalFilename = file.getOriginalFilename();
				if (originalFilename == null || originalFilename.isEmpty()) {
					continue; // Skip files with no original filename
				}

				// Original file path
				Path originalFilePath = Paths.get(mediaPath+"/"+"leaf-doc", String.valueOf(projectId), originalFilename);
				Files.createDirectories(originalFilePath.getParent()); // Create parent directories if they don't exist
				file.transferTo(originalFilePath.toFile()); // Save the original file

				// Modified file path
				Path modifiedFilePath = Paths.get(mediaPath+"/"+"leaf-doc-modified", String.valueOf(projectId),
						originalFilename);
				Files.createDirectories(modifiedFilePath.getParent()); // Create parent directories if they don't exist

				// Reset the input stream to read it again for processing
				byte[] fileBytes = copyFile.getBytes();
				try (InputStream inputStream = new ByteArrayInputStream(fileBytes);
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

					// Read lines, remove duplicates, and write to the modified file
					Set<String> uniqueLines = new HashSet<>();
					String line;
					while ((line = reader.readLine()) != null) {
						uniqueLines.add(line); // Add line to set to remove duplicates
					}

					// Write unique lines to the modified file
					try (BufferedWriter writer = Files.newBufferedWriter(modifiedFilePath)) {
						for (String uniqueLine : uniqueLines) {
							writer.write(uniqueLine);
							writer.newLine();
						}
					}
				}
			}

			result.setMsg("File(s) uploaded successfully!");
			return ResponseEntity.ok(result);
		} catch (IOException e) {
			e.printStackTrace();
			result.setMsg("Failed to upload files: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@PostMapping("/upload")
    public ResponseEntity<AjaxResponseBody> handleFileUpload(@RequestParam("projectId") int projectId,
            @RequestPart("files") MultipartFile[] files) {
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            System.out.println("Project ID: " + projectId);
            if (projectId <= 0 || files == null || files.length == 0) {
                result.setMsg("Invalid projectId or no files uploaded");
                return ResponseEntity.badRequest().body(result);
            }
			System.out.println("Number of Files: " + files.length);

			try {
				for (MultipartFile file : files) {
					if (file.isEmpty()) {
						continue; // Skip empty files
					}

					String originalFilename = file.getOriginalFilename();
					if (originalFilename == null || originalFilename.isEmpty()) {
						continue; // Skip files with no original filename
					}

					String ext = "";
					String fileName = "";
					if (originalFilename.contains(".")) {
						ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
						fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
					}

					// Save the file to the specified location
					Path projectFolder = Paths.get(mediaPath+"/"+"leaf-doc", String.valueOf(projectId));
					if (Files.notExists(projectFolder)) {
						Files.createDirectories(projectFolder);
					}

					String fileNameWithExtension = fileName + "." + ext;
					Path filePath = projectFolder.resolve(fileNameWithExtension);
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

					// Create and save ProjectDatasetLink entity
					ProjectDatasetLink projectDataSet = new ProjectDatasetLink();
					projectDataSet.setName(fileName); // Set the filename as the name
					projectDataSet.setProject(projectId);
					projectDataSet.setDescription("Description");
					projectDataSet.setStatus("Process");
					projectDataSet.setRecordStatus(true);
					projectDataSet.setFileName(fileNameWithExtension);
					projectDataSet.setFilePath(filePath.toString());

					// Assuming you have a repository for ProjectDatasetLink
					ProjectDatasetLink savedProjectDataset = projectDatasetRepo.save(projectDataSet);

					// Close the input stream
					file.getInputStream().close();

					System.out.println("Saved ProjectDatasetLink ID: " + savedProjectDataset.getId());
				}

				result.setMsg("File(s) uploaded successfully!");
			} finally {
				System.out.println("Code inside the finally block.");
			}

			return ResponseEntity.ok(result);
		} catch (IOException e) {
			e.printStackTrace();
			result.setMsg("Failed to upload files: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@CrossOrigin
	@GetMapping("/getProjectDatasetLink")
	public ResponseEntity<AjaxResponseBody> getProjectDatasetLink(@RequestParam int projectId) {
	    try {
	        List<ProjectDatasetLink> projectDatasetLinks = projectDatasetRepo.findByProjectId(projectId);
	        AjaxResponseBody result = new AjaxResponseBody();
	        Map finalMap = new HashMap<>();

	        Map tableListByDataset = new HashMap<>();
	        Map<Integer, String> datasetMap = new HashMap<>();
	        Map<Integer, String> statusMap = new HashMap<>(); // New map for status

	        if (projectDatasetLinks.isEmpty()) {
	            result.setMsg("No ProjectDataSetLink available for Project ID: " + projectId);
	            return ResponseEntity.notFound().build();
	        } else {
	            Set<Integer> datasetIdsList = new HashSet<>();
	            for (ProjectDatasetLink projectDatasetLink : projectDatasetLinks) {
	                Map<Integer, String> tablemap = new HashMap<>();
	                int datasetId = projectDatasetLink.getId();
	                if (datasetIdsList.add(datasetId)) {
	                    datasetMap.put(datasetId, projectDatasetLink.getFileName());
	                    statusMap.put(datasetId, projectDatasetLink.getStatus()); // Add status to statusMap
	                    System.out.println("projectDatasetLink.getName()=====" + projectDatasetLink.getFileName());

	                    List<ProjectTablesLink> tablesByDataset = projectTableRepo.findByDatasetId(datasetId);
	                    System.out.println("tablesByDataset=====" + tablesByDataset);
	                    for (ProjectTablesLink table : tablesByDataset) {
	                        int tableId = table.getId();
	                        tablemap.put(tableId, table.getName());
	                    }
	                    tableListByDataset.put(datasetId, tablemap);
	                }
	            }
	            finalMap.put("datasetIdsList", datasetIdsList);
	            finalMap.put("datasetMap", datasetMap);
	            finalMap.put("tableListByDataset", tableListByDataset);
	            finalMap.put("statusMap", statusMap); // Include statusMap in the finalMap

	            System.out.println("==tableListByDataset==" + tableListByDataset);
	            result.setMsg(finalMap);
	            return ResponseEntity.ok(result);
	        }
	    } catch (Exception e) {
	        System.err.println("Error fetching ProjectDatasetLink: " + e.getMessage());
	        return ResponseEntity.status(500).build();
	    }
	}




	@PostMapping("/processFileData")
	public ResponseEntity<AjaxResponseBody> processFileData(@RequestParam int projectId, @RequestParam int dataSetId,
			@RequestParam String fileName) {
		AjaxResponseBody result = new AjaxResponseBody();
		try {
			// Construct the file path using a correct separator for Windows
			System.out.println("=====fileName===="+fileName);
			Path filePath = Paths.get(mediaPath+"/"+"leaf-doc", String.valueOf(projectId), fileName);

			// Log the file path
			System.out.println("File path: " + filePath);

			// Check if the file exists before attempting to process it
			if (Files.exists(filePath)) {
				try (InputStream fileStream = new FileInputStream(filePath.toFile())) {
					// Use try-with-resources to automatically close the stream
					String fileExtension = getFileExtension(fileName);

					// Process and copy the file
					excelService.processAndCopyFile(fileStream, projectId, dataSetId, fileName, fileExtension);
				} catch (CsvValidationException e) {
					// Handle CsvValidationException here
					e.printStackTrace();
					result.setMsg("Error processing file data: " + e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
				} catch (Exception e) {
					// Handle other exceptions
					e.printStackTrace();
					result.setMsg("Error processing file data: " + e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
				}
			} else {
				// Handle the case where the file does not exist
				result.setMsg("File not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			}

			// Set success message if processing is successful
			result.setMsg("Success");
			//ProjectDatasetLink projectDataSet = new ProjectDatasetLink();

			ProjectDatasetLink projectDataSet = projectDatasetRepo.findByProjectIdAndDataSet(projectId,dataSetId);
			projectDataSet.setStatus("Processed");
			projectDataSet.setRecordStatus(true);
			ProjectDatasetLink savedProjectDataset = projectDatasetRepo.save(projectDataSet);

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("Error processing file data: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// Method to extract the file extension from a given fileName
	private String getFileExtension(String fileName) {
		if (fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			throw new IllegalArgumentException("File name does not have a valid extension");
		}
	}

	@GetMapping("/getProjectData")
	public ApiResponseBody getProjectData(@RequestParam int projectId,
			@RequestParam(required = false) Integer selectedTableId) {
		ApiResponseBody result = new ApiResponseBody();
		try {
			List<ProjectDatasetLink> response = projectDatasetRepo.findByProjectId(projectId);
			Map finalMap = new HashMap<>();
			Map<Integer, String> tablemap = new HashMap<>();
			Set<Integer> tableIdsList = new HashSet<>();
			Map tableDataMap = new HashMap<>();
			Map tablecolumnMap = new HashMap<>();

			int outerLoopCount = 0;

			// Step 1: Retrieve the list of tables based on projectId
			for (ProjectDatasetLink projectDatasetLink : response) {
				outerLoopCount++;
				int datasetId = projectDatasetLink.getId();
				// System.out.println("datasetId=====" + datasetId);

				List<ProjectTablesLink> tablesByDataset = projectTableRepo.findByDatasetId(datasetId);
				for (ProjectTablesLink table : tablesByDataset) {
					int tableId = table.getId();
					tableIdsList.add(tableId);
					if (datasetId == table.getDataSet())
						tablemap.put(tableId, table.getName());
				}
			}

			finalMap.put("tablemap", tablemap);
			finalMap.put("tableIdsList", tableIdsList);

			// Step 2: Fetch data for the selected table based on selectedTableId
			if (selectedTableId != null && tableIdsList.contains(selectedTableId)) {
				List<ProjectTablesDataLink> tableData = projectTablesDataRepo.getResultsByTable(selectedTableId);
				List<ProjectTablesColumnsLink> tableColumnData = projectTablesColRepo
						.getResultsByTable(selectedTableId);

				Map<Integer, Map<Integer, String>> ColWiseMap = tableColumnData.stream()
						.collect(Collectors.toMap(ProjectTablesColumnsLink::getTable,
								// Value mapper function: Convert to Map<Integer, String>
								dataLink -> {
									Map<Integer, String> innerMap = new HashMap<>();
									innerMap.put(dataLink.getColumnIndex(), dataLink.getName());
									return innerMap;
								},
								// Merge function: Combine maps for duplicate keys
								(existingMap, newMap) -> {
									existingMap.putAll(newMap);
									return existingMap;
								}));

				tablecolumnMap.put(selectedTableId, ColWiseMap);
				Map<Integer, Map<Integer, String>> rowColWiseMap = tableData.stream()
						.collect(Collectors.toMap(ProjectTablesDataLink::getRowIndex,
								// Value mapper function: Convert to Map<Integer, String>
								dataLink -> {
									Map<Integer, String> innerMap = new HashMap<>();
									innerMap.put(dataLink.getColumnIndex(), dataLink.getData());
									return innerMap;
								},
								// Merge function: Combine maps for duplicate keys
								(existingMap, newMap) -> {
									existingMap.putAll(newMap);
									return existingMap;
								}));

				tableDataMap.put(selectedTableId, rowColWiseMap);
			}

			finalMap.put("tableDataMap", tableDataMap);
			finalMap.put("tablecolumnMap", tablecolumnMap);
			result.setResponse(finalMap);
			result.setStatus("success");
			result.setStatusCode(200);

		} catch (Exception e) {
			result.setStatus("error");
			result.setStatusCode(500); // Internal Server Error
		}

		return result;
	}

}
