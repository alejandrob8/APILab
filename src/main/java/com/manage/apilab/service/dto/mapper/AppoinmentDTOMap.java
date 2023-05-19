package com.manage.apilab.service.dto.mapper;

import org.springframework.stereotype.Component;
import com.manage.apilab.model.Appoinment;
import com.manage.apilab.service.dto.AppoinmentDTO;

@Component
public class AppoinmentDTOMap implements Mapper<AppoinmentDTO, Appoinment> {

	@Override
	public Appoinment map(AppoinmentDTO input) {
		Appoinment appoinment = new Appoinment();
		appoinment.setDate(input.getDate());
		appoinment.setHour(input.getHour());
		appoinment.setIdTest(input.getIdTest());
		appoinment.setIdAffiliatte(input.getIdAffiliate());
		return appoinment;
	}

}
