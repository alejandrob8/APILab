package com.manage.apilab.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manage.apilab.exceptions.CreatedException;
import com.manage.apilab.exceptions.NoContentException;
import com.manage.apilab.exceptions.NotFoundException;
import com.manage.apilab.exceptions.OKException;
import com.manage.apilab.model.Appoinment;
import com.manage.apilab.service.AppoinmentService;
import com.manage.apilab.service.dto.AppoinmentDTO;

@RestController
@RequestMapping("/api/controller/appoinments")
public class AppoinmentController {

	@Autowired
	private AppoinmentService appoinmentService;

	@GetMapping
	public ResponseEntity<?> getlist() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(appoinmentService.getlist());
		} catch (Exception e) {
			throw new NoContentException();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getbyid(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(appoinmentService.getbyid(id));
		} catch (Exception e) {
			throw new NotFoundException("Appoinment id " + "[" + id + "]" + " not found");
		}
	}

	@PostMapping
	public void post(@RequestBody AppoinmentDTO appoinmentDTO) {
		try {
			appoinmentService.post(appoinmentDTO);
		} catch (Exception e) {
			throw new NotFoundException("Insertion failed");
		}
		throw new CreatedException("Insertion sussccesful");
	}

	@PutMapping
	public void put(@RequestBody Appoinment appoinment) {
		try {
			appoinmentService.put(appoinment);
		} catch (Exception e) {
			throw new NotFoundException("Update failed");
		}
		throw new CreatedException("Update successful");
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) {
		try {
			appoinmentService.delete(id);
		} catch (Exception e) {
			throw new NoContentException();
		}
		throw new OKException("Appoinment id " + "[" + id + "]" + " removed");
	}

	@GetMapping("/by")
	public ResponseEntity<?> getbydate(@RequestParam(name = "date") String localDate) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(localDate, dateFormat);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(appoinmentService.getbydate(date));
		} catch (Exception e) {
			throw new NotFoundException("No Appoinments found for date: " + "[" + localDate + "]");
		}
	}

	@GetMapping("/affiliate/{idAffiliatte}")
	public ResponseEntity<?> getbyaffiliates(@PathVariable Integer idAffiliatte) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(appoinmentService.getbyaffiliates(idAffiliatte));
		} catch (Exception e) {
			throw new NotFoundException("No Appoinments found for Afilliate Id " + "[" + idAffiliatte + "]");
		}
	}

}
