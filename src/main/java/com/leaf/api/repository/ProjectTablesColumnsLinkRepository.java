package com.leaf.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.ProjectTablesColumnsLink;

@Repository
public interface ProjectTablesColumnsLinkRepository extends CrudRepository<ProjectTablesColumnsLink, Integer> {
	@Query("SELECT u FROM ProjectTablesColumnsLink u WHERE u.table = ?1")
	public List<ProjectTablesColumnsLink> getResultsByTable(int tableId);
}
