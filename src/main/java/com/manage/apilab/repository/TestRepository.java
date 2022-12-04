package com.manage.apilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.manage.apilab.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

}
