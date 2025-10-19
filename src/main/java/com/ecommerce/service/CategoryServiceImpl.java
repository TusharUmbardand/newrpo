package com.ecommerce.service;

import com.ecommerce.exception.APIException;
import com.ecommerce.exception.ResourceNotFound;
import com.ecommerce.model.Category;
import com.ecommerce.payload.CategoryDTO;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        Category category1 = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (category1==null){
            categoryRepository.save(modelMapper.map(categoryDTO,Category.class));
            return;
        }
        throw new APIException("Category with the name "+ categoryDTO.getCategoryName()+" is already present");
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize ,String sortBy , String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("acs")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category,CategoryDTO.class)).toList();
        if(categories.isEmpty()){
            throw  new APIException("No categories are created till now");
        }
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(pageDetails.getPageNumber());
        categoryResponse.setPageSize(pageDetails.getPageSize());
        categoryResponse.setTotaleElements(categoryPage.getTotalElements());
        categoryResponse.setTotalePages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("NOT Found","NOT Found",id));

        categoryRepository.deleteById(id);
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public String updateCategory(CategoryDTO categoryDTO) {
        Category categoryTobeUpdated = categoryRepository.findById(categoryDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFound("NOT Found","NOT Found",categoryDTO.getCategoryId())) ;
        categoryTobeUpdated.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(categoryTobeUpdated);


        return "Category with id : " + categoryTobeUpdated.getCategoryId() + " is Updated";
    }

}
