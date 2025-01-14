package com.api.cv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaginationResultDto<T> {

    private List<T> result;
    private long totalPages;
    private long totalItems;
    private int page;
    private int size;
    
}