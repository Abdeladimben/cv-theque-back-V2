package com.api.cv.mappers.document;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.api.cv.dto.DocumentDto;
import com.api.cv.entities.Document;

@Mapper
public interface DocumentMapper {

	DocumentMapper INSTANCE=Mappers.getMapper(DocumentMapper.class);
	 DocumentDto entityToDto(Document document);
	 
	Document DtoToEntity(DocumentDto documentDto);
	
	List<DocumentDto> entitiesToDtos(List<Document> documents);
	
}
