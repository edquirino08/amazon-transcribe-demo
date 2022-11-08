package com.transcribe.aws.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.transcribe.aws.dto.TranscribeDTO;
import com.transcribe.aws.model.TranscribeModel;
import com.transcribe.aws.repository.TranscribeRepository;

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

@Service
public class TranscribeService  {

	@Autowired
	private TranscribeRepository repository;

	
	public String transcribe(TranscribeDTO dto) throws IOException {

		Region REGION = Region.SA_EAST_1;
		TranscribeClient client;
		
		String s3AudioPath = "s3://spring-transcribe/"+dto.getAudioName()+".mp3";

		AwsBasicCredentials aswCredentials = AwsBasicCredentials.create("",
				"");

		client = TranscribeClient.builder().credentialsProvider(StaticCredentialsProvider.create(aswCredentials))
				.region(REGION).build();

		String transcriptionJobName = dto.getJobName();
		String mediaType = "mp3";
		// can be other types 
		Media myMedia = Media.builder().mediaFileUri(s3AudioPath).build();

		String outputS3BucketName = dto.getS3BucketOutput();
		// Create the transcription job request
		StartTranscriptionJobRequest request = StartTranscriptionJobRequest.builder()
				.transcriptionJobName(transcriptionJobName).languageCode(LanguageCode.PT_BR.toString())
				.mediaFormat(mediaType).media(myMedia).outputBucketName(outputS3BucketName).build();

		// send the request to start the transcription job
		StartTranscriptionJobResponse startJobResponse = client.startTranscriptionJob(request);

		System.out.println("Created the transcription job");
		System.out.println(startJobResponse.transcriptionJob());

		// Create the get job request 
		GetTranscriptionJobRequest getJobRequest = GetTranscriptionJobRequest.builder().transcriptionJobName(transcriptionJobName).build();

		// send the request to get the transcription job including the job status
		GetTranscriptionJobResponse getJobResponse = client.getTranscriptionJob(getJobRequest);

		System.out.println("Get the transcription job request");
		System.out.println(getJobResponse.transcriptionJob());

		String result = getTranscrip(dto.getS3BucketOutput(), dto.getJobName() + ".json");


		TranscribeModel transcribe = new TranscribeModel(dto.getS3BucketOutput(), dto.getJobName(), result);

		repository.save(transcribe);

		return transcribe.getTranscript();

	}

	public String getTranscrip(String bucketName, String key) throws IOException {

		Regions clientRegion = Regions.SA_EAST_1;

		S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
		String result = "";
		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
					.withCredentials(new ProfileCredentialsProvider()).build();

			// Get an object and print its contents.
			System.out.println("Downloading an object");
			fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
			System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
			System.out.println("Content: ");
			fullObject.getObjectMetadata();
			result = displayTextInputStream(fullObject.getObjectContent());

		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		} finally {
			// To ensure that the network connection doesn't remain open, close any open
			// input streams.
			if (fullObject != null) {
				fullObject.close();
			}
			if (objectPortion != null) {
				objectPortion.close();
			}
			if (headerOverrideObject != null) {
				headerOverrideObject.close();
			}
		}

		return result;

	}

	private String displayTextInputStream(InputStream input) throws IOException {
		// Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;

		line = reader.readLine();

		return returnTranscript(line);

	}

	private static String returnTranscript(String str) {

		JSONObject jsonObject = new JSONObject(str);

		String results = jsonObject.optJSONObject("results").getJSONArray("transcripts").getJSONObject(0).get("transcript").toString();


		return results;
	}

}
