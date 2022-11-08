package com.transcribe.aws.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transcribe.aws.dto.ErrorDTO;
import com.transcribe.aws.dto.LogsDTO;
import com.transcribe.aws.dto.TranscribeDTO;
import com.transcribe.aws.service.ErrorService;
import com.transcribe.aws.service.LogsService;
import com.transcribe.aws.service.TranscribeService;

import software.amazon.awssdk.services.transcribestreaming.model.ConflictException;

@RestController
@RequestMapping("/amazon")
public class TranscribeController {

	@Autowired
	private TranscribeService transcribeService;

	@Autowired
	private LogsService logService;
	
	@Autowired
	private ErrorService errorService; 

	@PostMapping
	public String transcribe(@RequestBody TranscribeDTO dto) throws SQLException, ConflictException {
		
		String transcript  = "";

		try {

			transcript = this.transcribeService.transcribe(dto);
			LogsDTO logDto = new LogsDTO("foi realizada a transcrição do arquivo " + dto.getAudioName()
					+ " ,para o bucket: " + dto.getS3BucketOutput());
			logService.addLog(logDto);

		} catch (RuntimeException | IOException e) {
			
			ErrorDTO error = new ErrorDTO(e.getMessage());
			errorService.addError(error);
			return ResponseEntity.internalServerError().body(e.getMessage()).getBody();
		}

		return ResponseEntity.ok().body(transcript).getBody();
	}

}
