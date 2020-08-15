package com.hyperdrive.woodstock.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

/** Serviço responsável por fazer upload das imagens para o bucket da Amazon
 * 
 * @author Hugo Andreassa Amaral
 */
@Service
public class S3Service {

	private Logger log = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) {

		try {
			String filename = multipartFile.getName();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();

			return uploadFile(is, filename, contentType);
		} catch (IOException e) {
			throw new RuntimeException("Error IO");
		}
	}

	private URI uploadFile(InputStream is, String filename, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);

			log.info("Iniciando Upload");
			s3client.putObject(bucketName, filename, is, meta);
			log.info("Upload finalizado");

			return s3client.getUrl(bucketName, filename).toURI();

		} /* catch (AmazonServiceException e) {
			log.info("AmazonServiceException: " + e.getMessage());
			log.info("Erro Code: " + e.getErrorCode());
		} catch (AmazonClientException e) {
			log.info("AmazonClientException: " + e.getMessage());
			log.info("Cause: " + e.getCause());
		} */ catch (URISyntaxException e) {
			throw new RuntimeException("Error converting URI");
		}
	}
}
