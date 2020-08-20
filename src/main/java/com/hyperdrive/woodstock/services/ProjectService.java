package com.hyperdrive.woodstock.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Project;
import com.hyperdrive.woodstock.repositories.BudgetItemRepository;
import com.hyperdrive.woodstock.repositories.ProjectRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** ProjectService
 * 
 * @author Hugo A.
 */
@Service
public class ProjectService {
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.project.profile}")
	private String prefix;
	
	@Autowired
	private ProjectRepository repository;
	
	@Autowired
	private BudgetItemRepository budgetItemRepository;	
	
	public List<Project> findAllByBudgetId(Long budgetItemId) {
		Optional<BudgetItem> budgetItem = budgetItemRepository.findById(budgetItemId);
		
		List<Project> list = repository.findByBudgetItem(budgetItem.orElseThrow(() -> new ResourceNotFoundException(budgetItemId)));
		return list;
	}
	
	public Project findById(Long id) {
		Optional<Project> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Project insert(Project entity) {
		try {
			
			return repository.save(entity);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}	
	}
	
	public Project update(Long id, Project entity) {
		try {
			entity.setId(id);
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public URI uploadPicture(MultipartFile multipartFile, Long projectId) {
		Project prjt = repository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException(projectId));		
		
		BufferedImage jpgImage= imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + projectId + ".jpg";
		
		URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
		prjt.setUrl(uri.toString());
		repository.save(prjt);
		
		return uri;
	}
}
