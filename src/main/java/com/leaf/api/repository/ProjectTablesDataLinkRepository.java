package com.leaf.api.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.ProjectTablesDataLink;



@Repository
public interface ProjectTablesDataLinkRepository extends CrudRepository<ProjectTablesDataLink, Integer>{
	
	@Query("SELECT u FROM ProjectTablesDataLink u WHERE u.table = ?1")
	public List<ProjectTablesDataLink> getResultsByTable(int tableId);

}
