package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.model.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Admin findByUsername(String username);

}
