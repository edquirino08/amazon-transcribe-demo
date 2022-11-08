package com.transcribe.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transcribe.aws.dto.LogsDTO;
import com.transcribe.aws.model.LogsModel;
import com.transcribe.aws.repository.LogsRepository;

@Service
public class LogsService {
	
	@Autowired
	private LogsRepository repository;
	
	
	public LogsDTO addLog (LogsDTO dto) {
		
		LogsModel log = new LogsModel();
		
		log.setAlteracao(dto.getAlteracao());
		repository.save(log);
		return dto;
	}
	
	

}
