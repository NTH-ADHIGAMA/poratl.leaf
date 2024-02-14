package com.leaf.api.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leaf.api.modal.Roles;




@Repository
public interface RolesRepository extends CrudRepository<Roles, Integer> {

}
