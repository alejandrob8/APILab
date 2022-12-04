package com.manage.apilab.service.dto.mapper;

import org.springframework.stereotype.Component;
import com.manage.apilab.model.Affiliate;
import com.manage.apilab.service.dto.AffiliateDTO;

@Component
public class AffiliateDTOMap implements Mapper<AffiliateDTO, Affiliate> {

	@Override
	public Affiliate map(AffiliateDTO input) {
		Affiliate affiliate = new Affiliate();
		affiliate.setName(input.getName());
		affiliate.setAge(input.getAge());
		affiliate.setMail(input.getMail());
		return affiliate;
	}

}
