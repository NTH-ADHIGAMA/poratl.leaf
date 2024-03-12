package com.leaf.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.ProjectDatasetLink;



@Repository
public interface ProjectDatasetLinkRepository extends CrudRepository<ProjectDatasetLink, Integer> {

	@Query("SELECT u FROM ProjectDatasetLink u WHERE u.project = ?1")
	public List<ProjectDatasetLink> findByProjectId(int projectId);

	@Query("SELECT u FROM ProjectDatasetLink u WHERE u.project = ?1 and u.id=?2")
    public ProjectDatasetLink findByProjectIdAndDataSet(int projectId, int dataSetId);



}
