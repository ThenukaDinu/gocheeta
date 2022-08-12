package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.CategoryDTO;
import com.thenuka.gocheetaproject.modals.Category;
import com.thenuka.gocheetaproject.requests.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    CategoryDTO findOne(int id);
    CategoryDTO save(Category category);
    CategoryDTO update(int id, CategoryRequest category);
    void delete(int id);
    List<CategoryDTO> findAll();
    CategoryDTO convertEntityToDto(Category category);
    Category convertRequestToEntity(CategoryRequest categoryRequest, Category category);
}
