package com.api.cv.services.document;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.api.cv.dto.DocumentDto;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;

public interface IDocumentService {
	
	
	
	 DocumentDto upload(MultipartFile file,String DocumentTypeCode) throws IOException, ApiErrorException;
	 byte[]downloadFile(String uuid);
	 
	 List<DocumentDto> getDocsByUser() throws ApiErrorException;
	 
	 DocumentDto getDocByUuid(String uuid) throws RessourceDbNotFoundException;
	void delete(String uuid) throws RessourceDbNotFoundException;
	
	//public List<DocumentDto> getAllDocuments() throws ApiErrorException;

}
