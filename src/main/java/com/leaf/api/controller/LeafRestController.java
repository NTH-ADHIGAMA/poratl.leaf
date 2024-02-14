package com.leaf.api.controller;



import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ExcelService excelService;

	@Autowired
	private ProjectsRepository projectsRepo;

	@Autowired
	private ProjectDatasetLinkRepository projectDatasetRepo;

	@Autowired
	private ProjectTablesLinkRepository projectTableRepo;

	@Autowired
	private ProjectTablesColumnsLinkRepository projectTablesColRepo;

	@Autowired
	private ProjectTablesDataLinkRepository projectTablesDataRepo;

	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

	@GetMapping("/test")
	public String Test() {
		System.out.println("====leaf TEST=====");
		return "leaf test is valid";
	}

	@CrossOrigin
    @ResponseBody
    @PostMapping("/saveUser")
    public ResponseEntity<AjaxResponseBody> saveUser(@RequestParam ("id") int id,@RequestBody Users user) {
        AjaxResponseBody result = new AjaxResponseBody();


         if (id >0) {
             Users users =  userRepository.findById(id).get();
             user.setId(id);
             user.setCreatedDate(users.getCreatedDate());
             user.setFirstLoginDate(users.getFirstLoginDate());
         }else {
             user.setCreatedDate(LocalDateTime.now());
             user.setFirstLoginDate(LocalDateTime.now());

         }

        user.setLastLoginDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        user.setStatus("active");
        user.setRecordStatus(true);
        Users newUser = userRepository.save(user);
        System.out.println("Provider Saved : "+newUser.getUsername());
        result.setMsg(newUser);
        return ResponseEntity.ok(result);
    }
    @RequestMapping("/getUsers")
    public ResponseEntity<AjaxResponseBody> getUsers() {
        AjaxResponseBody result = new AjaxResponseBody();
        List<Users> returnList = (List<Users>) userRepository.findAll();
        List<Roles> roles = (List<Roles>) rolesRepository.findAll();
        System.out.println("Users : "+returnList.size());
        result.setMsg(returnList);
        result.setMsg1(roles);
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/getRoles")
    public ResponseEntity<AjaxResponseBody> getRoles() {
        AjaxResponseBody result = new AjaxResponseBody();
       List<Roles> returnList = (List<Roles>) rolesRepository.findAll();

        System.out.println("Users : "+returnList.size());
        result.setMsg(returnList);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestParam ("id") int id) {
        AjaxResponseBody result = new AjaxResponseBody();
        Users users = userRepository.findById(id).get();
        System.out.println("getUsername :: "+users.getUsername());
        System.out.println("getEmail :: "+users.getEmail());
        System.out.println("getPhone :: "+users.getPhone());
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
    public ResponseEntity<?> getProjectDetails(@RequestParam ("id") int id) {
        AjaxResponseBody result = new AjaxResponseBody();
        Projects projects = projectsRepo.findById(id).get();
        System.out.println("getUsername :: "+projects.getName());
        System.out.println("getEmail :: "+projects.getCode());
        System.out.println("getPhone :: "+projects.getDescription());
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
    public ResponseEntity<AjaxResponseBody> saveProject(@RequestParam ("id") int id,@RequestBody Projects projects) {
        AjaxResponseBody result = new AjaxResponseBody();


         if (id >0) {
             Projects prjt =  projectsRepo.findById(id).get();
             projects.setId(id);
             projects.setDateOfCreation(prjt.getDateOfCreation());
            projects.setLastModifiedDate(prjt.getLastModifiedDate());
         }else {
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



	@PostMapping("/upload")
	public ResponseEntity<AjaxResponseBody> handleFileUpload(@RequestParam("projectId") int projectId,
			@RequestPart("files") MultipartFile[] files) throws IOException, CsvValidationException {
		System.out.println("Project ID: " + projectId);
		 AjaxResponseBody result = new AjaxResponseBody();
		System.out.println("Number of Files: " + files.length);
		for (MultipartFile file : files) {
			String originalFilename = file.getOriginalFilename();
			String ext = "";
			String fileName = "";
			if (originalFilename != null && originalFilename.contains(".")) {
				ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
				fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
			}
			System.out.println("originalFilename==== " + fileName);
			System.out.println("ext==== " + ext);
			excelService.processFile(file.getInputStream(), projectId,ext,fileName);
		}
		result.setMsg("File(s) uploaded successfully!");
		 return ResponseEntity.ok(result);
	}

	@GetMapping("/getProjectData")
	public ApiResponseBody getProjectData(
	        @RequestParam int projectId,
	        @RequestParam(required = false) Integer selectedTableId
	) {
	    ApiResponseBody result = new ApiResponseBody();
	    try {
	        List<ProjectDatasetLink> response = projectDatasetRepo.findByProjectId(projectId);
	        System.out.println("response size=====" + response.size());
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
	            System.out.println("datasetId=====" + datasetId);

	            List<ProjectTablesLink> tablesByDataset = projectTableRepo.findByDatasetId(datasetId);
	            for (ProjectTablesLink table : tablesByDataset) {
	                int tableId = table.getId();
	                tableIdsList.add(tableId);
	                tablemap.put(tableId, table.getName());
	            }
	        }

	        System.out.println("Outer Loop Iterations: " + outerLoopCount);

	        finalMap.put("tablemap", tablemap);
	        finalMap.put("tableIdsList", tableIdsList);

	        // Step 2: Fetch data for the selected table based on selectedTableId
	        if (selectedTableId != null && tableIdsList.contains(selectedTableId)) {
	            System.out.println("Selected Table ID: " + selectedTableId);
	            List<ProjectTablesDataLink> tableData = projectTablesDataRepo.getResultsByTable(selectedTableId);
	            List<ProjectTablesColumnsLink> tableColumnData = projectTablesColRepo.getResultsByTable(selectedTableId);

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
				System.out.println("ColWiseMap=====" + ColWiseMap);
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
				System.out.println("rowColWiseMap=====" + rowColWiseMap);
				tableDataMap.put(selectedTableId, rowColWiseMap);
	        }

	        finalMap.put("tableDataMap", tableDataMap);
	        finalMap.put("tablecolumnMap", tablecolumnMap);
	        result.setResponse(finalMap);
	        result.setStatus("success");
	        result.setStatusCode(200);

	    } catch (Exception e) {
	        System.err.println("Exception in getProjectData: " + e.getMessage());
	        result.setStatus("error");
	        result.setStatusCode(500); // Internal Server Error
	    }

	    return result;
	}



}
