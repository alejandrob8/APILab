package com.manage.apilab.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.manage.apilab.model.Appoinment;

@Repository
public interface AppoinmentRepository extends JpaRepository<Appoinment, Integer> {

	@Query(value = "SELECT * FROM db_lab.appoinments WHERE date=:date order by id_affiliate", nativeQuery = true)
	public ArrayList<Appoinment> getbydate(@Param("date") LocalDate date);

	@Query(value = "SELECT * FROM db_lab.appoinments WHERE id_affiliate=:id_affiliatte", nativeQuery = true)
	public ArrayList<Appoinment> getbyaffiliates(@Param("id_affiliatte") Integer idAffiliatte);

}
