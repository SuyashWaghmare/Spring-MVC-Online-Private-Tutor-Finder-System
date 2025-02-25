package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {

	Tutor findByUsername(String username);

}
