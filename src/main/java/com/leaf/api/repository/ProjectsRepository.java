package com.leaf.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.Projects;

@Repository
public interface ProjectsRepository extends CrudRepository<Projects, Integer>{

}
