package com.hyperdrive.woodstock.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.repositories.AddressRepository;
import com.hyperdrive.woodstock.repositories.CompanyRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** CompanyService
 * 
 * @author Hugo Andreassa Amaral
 */
@Service
public class CompanyService {
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.company.profile}")
	private String prefix;
	
	@Autowired
	private CompanyRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Company findById(Long id) {
		Optional<Company> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Company insert(Company entity) {	
		try {
			
			if(entity.getAddress() != null) {
				entity.getAddress().setId(null);
				Address adrs = addressRepository.save(entity.getAddress());
				entity.setAddress(adrs);
			}
			
			return repository.save(entity);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		} 
		
	}
	
	public Company update(Long id, Company entity) {
		try {
			entity.setId(id);
			
			if(entity.getAddress() != null) {
				Address adrs = addressRepository.save(entity.getAddress());
				entity.setAddress(adrs);
			}
			
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public URI uploadPicture(MultipartFile multipartFile, Long companyId) {
		Company comp = repository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException(companyId));		
		
		BufferedImage jpgImage= imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + companyId + ".jpg";
		
		URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, ".jpg"), fileName, "image");
		
		comp.setLogo(uri.toString());
		repository.save(comp);
		
		return uri;
	}
}
