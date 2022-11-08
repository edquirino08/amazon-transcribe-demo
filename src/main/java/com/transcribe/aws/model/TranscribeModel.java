package com.transcribe.aws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transcribe")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscribeModel {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "bucket_location")
	private String bucket_location;

	@Column(name = "job_name")
	private String job_name;

	@Column(name = "transcript")
	private String transcript;

	public TranscribeModel(String bucket_location, String job_name, String transcript) {
		this.bucket_location = bucket_location;
		this.job_name = job_name;
		this.transcript = transcript;
	}

}
