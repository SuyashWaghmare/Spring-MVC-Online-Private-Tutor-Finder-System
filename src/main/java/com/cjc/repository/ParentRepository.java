package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.model.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

	Parent findByUsername(String username);

}
