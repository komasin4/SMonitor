package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {

}
