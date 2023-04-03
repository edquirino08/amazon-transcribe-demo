package com.transcribe.aws.batch;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.GetTranscriptionJobRequest;
import software.amazon.awssdk.services.transcribe.model.GetTranscriptionJobResponse;
import software.amazon.awssdk.services.transcribe.model.Media;
import software.amazon.awssdk.services.transcribe.model.StartTranscriptionJobRequest;
import software.amazon.awssdk.services.transcribe.model.StartTranscriptionJobResponse;
import software.amazon.awssdk.services.transcribestreaming.model.LanguageCode;

public class TranscribeDemoApp {
	
	
	private static final Region REGION = Region.SA_EAST_1;
	private static TranscribeClient client;

	public void Transcribe() {

		AwsBasicCredentials aswCredentials = AwsBasicCredentials.create("AKIA5K6AKHX4LV6HWOP4",
				"3kWEplbEcI0qQCgJ2sQbWA4H5RolxawUZcWjIij5");

		client = TranscribeClient.builder().credentialsProvider(StaticCredentialsProvider.create(aswCredentials))
				.region(REGION).build();
		
		

		String transcriptionJobName = "teste12"; 
		String mediaType = "mp3"; // can be other types
		Media myMedia = Media.builder().mediaFileUri("s3://conversor-audio-texto/voice_to_text/audioteste.mp3").build();

		String outputS3BucketName = "conversor-audio-texto2";
		// Create the transcription job request
		StartTranscriptionJobRequest request = StartTranscriptionJobRequest.builder()
				.transcriptionJobName(transcriptionJobName).languageCode(LanguageCode.PT_BR.toString())
				.mediaFormat(mediaType).media(myMedia).outputBucketName(outputS3BucketName).build();

		// send the request to start the transcription job
		StartTranscriptionJobResponse startJobResponse = client.startTranscriptionJob(request);

		System.out.println("Created the transcription job");
		System.out.println(startJobResponse.transcriptionJob());

		// Create the get job request
		GetTranscriptionJobRequest getJobRequest = GetTranscriptionJobRequest.builder()
				.transcriptionJobName(transcriptionJobName).build();

		// send the request to get the transcription job including the job status
		GetTranscriptionJobResponse getJobResponse = client.getTranscriptionJob(getJobRequest);

		System.out.println("Get the transcription job request");
		System.out.println(getJobResponse.transcriptionJob());
		
		client.getTranscriptionJob(getJobRequest).getValueForField(transcriptionJobName, null);
		
		client.getTranscriptionJob(getJobRequest);
		
		
		
		

	}

}