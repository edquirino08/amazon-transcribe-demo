package com.transcribe.aws.dto;

public class TranscribeDTO {

	private String jobName;
	private String audioName;
	private String s3BucketOutput;

	public TranscribeDTO() {

	}

	public TranscribeDTO(String jobName, String audioName, String s3BucketOutput) {

		this.jobName = jobName;
		this.audioName = audioName;
		this.s3BucketOutput = s3BucketOutput;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getS3BucketOutput() {
		return s3BucketOutput;
	}

	public void setS3BucketOutput(String s3BucketOutput) {
		this.s3BucketOutput = s3BucketOutput;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

}
