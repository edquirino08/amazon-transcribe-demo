package com.transcribe.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transcribe.aws.dto.ErrorDTO;
import com.transcribe.aws.model.ErrorModel;
import com.transcribe.aws.repository.ErrorRepository;

@Service
public class ErrorService {
	
	@Autowired
	private ErrorRepository repository;

	public ErrorDTO addError(ErrorDTO dto) {

		ErrorModel error = new ErrorModel(dto.getErro());

		repository.save(error);

		return dto;
	}

}
