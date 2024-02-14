package com.leaf.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.ProjectTablesLink;



@Repository
public interface ProjectTablesLinkRepository extends CrudRepository<ProjectTablesLink, Integer> {
	
	@Query("SELECT u FROM ProjectTablesLink u WHERE u.dataset = ?1")
	public List<ProjectTablesLink> findByDatasetId(int datasetId);

}
