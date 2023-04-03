package com.transcribe.aws.batch;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AmazonReturn {

	public static void getBucketInfo() {

		System.out.println("Objects in S3 bucket %s:\n conversor-audio-texto2");
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).build();

		s3.getUrl("conversor-audio-texto2", "teste7.json").toString();

		ListObjectsV2Result result = s3.listObjectsV2("conversor-audio-texto2");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		for (S3ObjectSummary os : objects) {
			System.out.println("* " + os.getKey());
		}

	}
}
