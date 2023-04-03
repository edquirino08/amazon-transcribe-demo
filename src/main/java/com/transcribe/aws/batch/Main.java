package com.transcribe.aws.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class Main {

	/*
	 * private static final Region REGION = Region.SA_EAST_1; private static
	 * TranscribeClient client;
	 * 
	 * public static void main(String[] args) throws FileNotFoundException,
	 * IOException, ParseException {
	 * 
	 * AwsBasicCredentials aswCredentials =
	 * AwsBasicCredentials.create("",
	 * "");
	 * 
	 * client =
	 * TranscribeClient.builder().credentialsProvider(StaticCredentialsProvider.
	 * create(aswCredentials)) .region(REGION).build();
	 * 
	 * String transcriptionJobName = "teste13"; String mediaType = "mp3"; // can be
	 * other types Media myMedia = Media.builder().mediaFileUri(
	 * "s3://conversor-audio-texto/voice_to_text/audioteste.mp3").build();
	 * 
	 * String outputS3BucketName = "conversor-audio-texto2"; // Create the
	 * transcription job request StartTranscriptionJobRequest request =
	 * StartTranscriptionJobRequest.builder()
	 * .transcriptionJobName(transcriptionJobName).languageCode(LanguageCode.PT_BR.
	 * toString())
	 * .mediaFormat(mediaType).media(myMedia).outputBucketName(outputS3BucketName).
	 * build();
	 * 
	 * // send the request to start the transcription job
	 * StartTranscriptionJobResponse startJobResponse =
	 * client.startTranscriptionJob(request);
	 * 
	 * System.out.println("Created the transcription job");
	 * System.out.println(startJobResponse.transcriptionJob());
	 * 
	 * // Create the get job request GetTranscriptionJobRequest getJobRequest =
	 * GetTranscriptionJobRequest.builder()
	 * .transcriptionJobName(transcriptionJobName).build();
	 * 
	 * // send the request to get the transcription job including the job status
	 * GetTranscriptionJobResponse getJobResponse =
	 * client.getTranscriptionJob(getJobRequest);
	 * 
	 * System.out.println("Get the transcription job request");
	 * System.out.println(getJobResponse.transcriptionJob());
	 * 
	 * }
	 * 
	 * 
	
	 */
	


	    public static void main(String[] args) throws IOException {
	        Regions clientRegion = Regions.SA_EAST_1;
	        String bucketName = "spring-transcribe";
	        String key = "acidao.json";

	        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
	        try {
	            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                    .withRegion(clientRegion)
	                    .withCredentials(new ProfileCredentialsProvider())
	                    .build();

	            // Get an object and print its contents.
	            System.out.println("Downloading an object");
	            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
	            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
	            System.out.println("Content: ");
	            displayTextInputStream(fullObject.getObjectContent());
	            fullObject.getObjectMetadata();
/*
	            // Get a range of bytes from an object and print the bytes.
	            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key)
	                    .withRange(0, 9);
	            objectPortion = s3Client.getObject(rangeObjectRequest);
	            System.out.println("Printing bytes retrieved.");
	            displayTextInputStream(objectPortion.getObjectContent());

	            // Get an entire object, overriding the specified response headers, and print the object's content.
	            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
	                    .withCacheControl("No-cache")
	                    .withContentDisposition("attachment; filename=example.txt");
	            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
	                    .withResponseHeaders(headerOverrides);
	            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
	            displayTextInputStream(headerOverrideObject.getObjectContent());
	            */
	        } catch (AmazonServiceException e) {
	            // The call was transmitted successfully, but Amazon S3 couldn't process 
	            // it, so it returned an error response.
	            e.printStackTrace();
	        } catch (SdkClientException e) {
	            // Amazon S3 couldn't be contacted for a response, or the client
	            // couldn't parse the response from Amazon S3.
	            e.printStackTrace();
	        } finally {
	            // To ensure that the network connection doesn't remain open, close any open input streams.
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
	    }

	    private static void displayTextInputStream(InputStream input) throws IOException {
	        // Read the text input stream one line at a time and display each line.
	        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	        }
	        System.out.println();
	    }
	}

