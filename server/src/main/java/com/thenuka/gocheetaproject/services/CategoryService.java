package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.CategoryDTO;
import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.interfaces.ICategoryService;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import com.thenuka.gocheetaproject.modals.Category;
import com.thenuka.gocheetaproject.modals.Vehicle;
import com.thenuka.gocheetaproject.repositories.ICategoryRepository;
import com.thenuka.gocheetaproject.requests.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IVehicleService vehicleService;

    @Override
    public CategoryDTO findOne(int id) {
        return convertEntityToDto(categoryRepository.findById(id).get());
    }

    @Override
    public boolean existsById(int id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public CategoryDTO save(Category category) {
        Category categorySaved = categoryRepository.save(category);
        return convertEntityToDto(categorySaved);
    }

    @Override
    public CategoryDTO update(int id, CategoryRequest categoryRequest) {
        if (categoryRepository.existsById(id)) {
            Category category = categoryRepository.findById(id).get();
            return save(convertRequestToEntity(categoryRequest, category));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO convertEntityToDto(Category category) {
        if (category == null) return null;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    @Override
    public Category convertRequestToEntity(CategoryRequest categoryRequest, Category category) {
        category.setDescription(categoryRequest.getDescription());
        category.setName(categoryRequest.getName());
        return category;
    }
}
