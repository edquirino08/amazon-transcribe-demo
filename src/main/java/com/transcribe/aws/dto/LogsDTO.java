package com.transcribe.aws.dto;

public class LogsDTO {

	private String alteracao;

	public LogsDTO() {
	}

	public LogsDTO(String alteracao) {
		this.alteracao = alteracao;
	}

	public String getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(String alteracao) {
		this.alteracao = alteracao;
	}

}
