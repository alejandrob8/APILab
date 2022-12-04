package com.manage.apilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.manage.apilab.model.Affiliate;

@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, Integer>{

}
