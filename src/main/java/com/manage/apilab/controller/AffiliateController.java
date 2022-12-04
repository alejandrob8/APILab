package com.manage.apilab.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.manage.apilab.exceptions.CreatedException;
import com.manage.apilab.exceptions.NoContentException;
import com.manage.apilab.exceptions.NotFoundException;
import com.manage.apilab.exceptions.OKException;
import com.manage.apilab.model.Affiliate;
import com.manage.apilab.service.AffiliateService;
import com.manage.apilab.service.dto.AffiliateDTO;

@RestController
@RequestMapping("/api/controller/affiliates")
public class AffiliateController {

	@Autowired
	private AffiliateService affiliateService;

	@GetMapping
	private ResponseEntity<?> getlist() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(affiliateService.getlist());
		} catch (Exception e) {
			throw new NoContentException();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getbyid(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(affiliateService.getbyid(id));
		} catch (Exception e) {
			throw new NotFoundException("Affiliate id " + "[" + id + "]" + " not found");
		}
	}

	@PostMapping
	public void post(@RequestBody AffiliateDTO affiliateDTO) {
		try {
			affiliateService.post(affiliateDTO);
		} catch (Exception e) {
			throw new NotFoundException("Insertion failed");
		}
		throw new CreatedException("Insertion sussccesful");
	}

	@PutMapping
	public void put(@RequestBody Affiliate affiliate) {
		try {
			affiliateService.put(affiliate);
		} catch (Exception e) {
			throw new NotFoundException("Update failed");
		}
		throw new CreatedException("Update successful");

	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) {
		try {
			affiliateService.delete(id);
		} catch (Exception e) {
			throw new NoContentException();
		}
		throw new OKException("Affiliate id " + "[" + id + "]" + " removed");
	}

}
