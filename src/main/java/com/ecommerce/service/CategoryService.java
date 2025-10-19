package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.payload.CategoryDTO;
import com.ecommerce.payload.CategoryResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {
    public void createCategory(CategoryDTO category);

    public CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);

    public CategoryDTO deleteCategory(Long id);

    String updateCategory(CategoryDTO categoryDTO);
}
