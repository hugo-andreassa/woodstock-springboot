package com.hyperdrive.woodstock.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	private Logger log = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uploadFile(String localPath) {
		
		try {
			File file = new File(localPath);
			
			log.info("Iniciando Upload");
			s3client.putObject(new PutObjectRequest(bucketName, "teste", file));
			log.info("Upload finalizado");
			
		} catch (AmazonServiceException e) {
			log.info("AmazonServiceException: " + e.getMessage());
			log.info("Erro Code: " + e.getErrorCode());
		} catch (AmazonClientException e) {
			log.info("AmazonClientException: " + e.getMessage());
			log.info("Cause: " + e.getCause());
		}
	}
}
