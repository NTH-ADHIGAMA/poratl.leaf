package com.leaf.api.service;



import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaf.api.modal.ProjectDatasetLink;
import com.leaf.api.repository.ProjectDatasetLinkRepository;



@Service
public class ProjectDatasetLinkService {
	
	@Autowired
	private ProjectDatasetLinkRepository projectDatasetLinkRepository;

    // Save a new ProjectDatasetLink
    public ProjectDatasetLink saveProjectDatasetLink(ProjectDatasetLink projectDatasetLink) {
        return projectDatasetLinkRepository.save(projectDatasetLink);
    }

    // Get all ProjectDatasetLinks
    public List<ProjectDatasetLink> getAllProjectDatasetLinks() {
        return (List<ProjectDatasetLink>) projectDatasetLinkRepository.findAll();
    }

    // Get ProjectDatasetLinks by ProjectId
    public List<ProjectDatasetLink> getProjectDatasetLinksByProjectId(int projectId) {
    	
    	 if (projectDatasetLinkRepository != null) {
             return (List<ProjectDatasetLink>) projectDatasetLinkRepository.findByProjectId(projectId);
         } else {
        	 
             // Handle the case when the repository is null
             return Collections.emptyList(); // or throw an exception, log an error, etc.
         }
    }

    // Get ProjectDatasetLink by Id
    public Optional<ProjectDatasetLink> getProjectDatasetLinkById(int id) {
        return projectDatasetLinkRepository.findById(id);
    }

    // Update a ProjectDatasetLink
    public ProjectDatasetLink updateProjectDatasetLink(ProjectDatasetLink projectDatasetLink) {
        return projectDatasetLinkRepository.save(projectDatasetLink);
    }

    // Delete a ProjectDatasetLink by Id
    public void deleteProjectDatasetLinkById(int id) {
        projectDatasetLinkRepository.deleteById(id);
    }

    // Other methods as needed...

}
