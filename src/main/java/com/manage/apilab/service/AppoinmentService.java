package com.manage.apilab.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.apilab.model.Appoinment;
import com.manage.apilab.repository.AppoinmentRepository;
import com.manage.apilab.service.dto.AppoinmentDTO;
import com.manage.apilab.service.dto.mapper.AppoinmentDTOMap;

@Service
public class AppoinmentService {

	@Autowired
	private AppoinmentRepository appoinmentRepo;

	@Autowired
	private AppoinmentDTOMap mapper;

	public List<Appoinment> getlist() throws Exception {
		List<Appoinment> listAppoinments = appoinmentRepo.findAll();
		if (listAppoinments.isEmpty()) {
			throw new Exception();
		}
		return listAppoinments;
	}

	public Appoinment getbyid(Integer id) throws Exception {
		Optional<Appoinment> appoinmentId = appoinmentRepo.findById(id);
		if (appoinmentId.isEmpty()) {
			throw new Exception();
		}
		return appoinmentRepo.findById(id).get();
	}

	public Appoinment post(AppoinmentDTO appoinmentDTO) throws Exception {
		Appoinment postAppoinment = mapper.map(appoinmentDTO);
		if (appoinmentDTO.getDate().toString().isBlank()) {
			throw new Exception();
		}
		return appoinmentRepo.save(postAppoinment);
	}

	public void put(Appoinment appoinment) throws Exception {
		Optional<Appoinment> putAppoinment = appoinmentRepo.findById(appoinment.getId());
		if (putAppoinment.isPresent()) {
			appoinmentRepo.save(appoinment);
		} else {
			throw new Exception();
		}
	}

	public void delete(Integer id) throws Exception {
		Optional<Appoinment> deleteAppoinment = appoinmentRepo.findById(id);
		if (deleteAppoinment.isPresent()) {
			appoinmentRepo.deleteById(id);
		} else {
			throw new Exception();
		}
	}

	public List<Appoinment> getbydate(LocalDate date) throws Exception {
		List<Appoinment> listByDate = appoinmentRepo.getbydate(date);
		if (listByDate.isEmpty()) {
			throw new Exception();
		}
		return (List<Appoinment>) appoinmentRepo.getbydate(date);
	}

	public List<Appoinment> getbyaffiliates(Integer idAffiliatte) throws Exception {
		List<Appoinment> listByAffiliate = appoinmentRepo.getbyaffiliates(idAffiliatte);
		if (listByAffiliate.isEmpty()) {
			throw new Exception();
		}
		return (List<Appoinment>) appoinmentRepo.getbyaffiliates(idAffiliatte);
	}

}
