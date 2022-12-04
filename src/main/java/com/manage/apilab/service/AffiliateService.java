package com.manage.apilab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.apilab.model.Affiliate;
import com.manage.apilab.repository.AffiliateRepository;
import com.manage.apilab.service.dto.AffiliateDTO;
import com.manage.apilab.service.dto.mapper.AffiliateDTOMap;

@Service
public class AffiliateService {

	@Autowired
	private AffiliateRepository affiliateRepo;

	@Autowired
	private AffiliateDTOMap mapper;

	public List<Affiliate> getlist() throws Exception {
		List<Affiliate> listAffiliates = affiliateRepo.findAll();
		if (listAffiliates.isEmpty()) {
			throw new Exception();
		}
		return listAffiliates;
	}

	public Affiliate getbyid(Integer id) throws Exception {
		Optional<Affiliate> AffiliateId = affiliateRepo.findById(id);
		if (AffiliateId.isEmpty()) {
			throw new Exception();
		}
		return affiliateRepo.findById(id).get();
	}

	public Affiliate post(AffiliateDTO affiliateDTO) throws Exception {
		Affiliate postAffiliate = mapper.map(affiliateDTO);

		if (affiliateDTO.getName().isBlank()) {
			throw new Exception();
		}
		return affiliateRepo.save(postAffiliate);
	}

	public Affiliate put(Affiliate affiliate) throws Exception {
		Optional<Affiliate> putAffiliate = affiliateRepo.findById(affiliate.getId());
		if (putAffiliate.isPresent()) {
			return affiliateRepo.save(affiliate);
		} else {
			throw new Exception();
		}
	}

	public void delete(Integer id) throws Exception {
		Optional<Affiliate> deleteAffiliate = affiliateRepo.findById(id);
		if (deleteAffiliate.isPresent()) {
			affiliateRepo.deleteById(id);
		} else {
			throw new Exception();
		}
	}

}
