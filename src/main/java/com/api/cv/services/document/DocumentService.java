package com.api.cv.services.document;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.cv.dto.DocumentDto;
import com.api.cv.entities.Document;
import com.api.cv.entities.User;
import com.api.cv.entities.referentiel.DocumentType;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.helpers.DocumentUtils;
import com.api.cv.mappers.document.DocumentMapper;
import com.api.cv.repositories.DocumentRepository;
import com.api.cv.repositories.UserRepository;
import com.api.cv.repositories.referentiel.DocumentTypeRepository;
import com.api.cv.services.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService{
	
	private final DocumentRepository documentRepository;
	private final DocumentTypeRepository documentTypeRepository;
    private final UserService userService; 
    private final UserRepository userRepository;
    
    protected DocumentType getDocumentTypeByCode(String code) throws RessourceDbNotFoundException {
    	
    	DocumentType docType=documentTypeRepository.getByCode(code)
				.orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
		return docType;

    }
    protected User getUserFromService() throws ApiErrorException {
        return userService.getUserConnected();
    }

	@Override
	@Transactional
	public void delete(String uuid) throws RessourceDbNotFoundException {
		
		Document doc=documentRepository.findByUuid(uuid)
				.orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
		doc.setDelete(true);
		documentRepository.save(doc);
	}
	@Override
	public DocumentDto upload(MultipartFile file,String DocumentTypeCode) throws IOException, ApiErrorException {
		Document doc=documentRepository.save(Document.builder()
				.nomDocument(file.getOriginalFilename())
				.extension(file.getContentType())
				.taille(file.getSize())
				.data(DocumentUtils.compressFile(file.getBytes()))
				.createdUser(getUserFromService())
				.documentType(getDocumentTypeByCode(DocumentTypeCode))
				.build());
		
		if(doc!=null) {
			DocumentDto dto=DocumentMapper.INSTANCE.entityToDto(doc);
			dto.setData(null);
			return dto;

		}
		return null;
	}

	@Override
	public byte[] downloadFile(String uuid) {
		
		Optional<Document> dbDoc=documentRepository.findByUuid(uuid);
		
		byte[] doc=DocumentUtils.decompressFile(dbDoc.get().getData());
		
		return doc;
	}

	@Override
	public List<DocumentDto> getDocsByUser() throws ApiErrorException {
		
		List<Document> documents=documentRepository.findByCreatedUser(getUserFromService());
		return DocumentMapper.INSTANCE.entitiesToDtos(documents);
	}
	@Override
	public DocumentDto getDocByUuid(String uuid) throws RessourceDbNotFoundException {
		
		Document doc=documentRepository.findByUuid(uuid)
				.orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
		
		return DocumentMapper.INSTANCE.entityToDto(doc);
	}
	
	
	}
	
	
	
	
		


