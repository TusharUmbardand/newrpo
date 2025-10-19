package com.ecommerce.controller;

import com.ecommerce.configuration.AppConst;
import com.ecommerce.exception.ResourceNotFound;
import com.ecommerce.model.Category;
import com.ecommerce.payload.CategoryDTO;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
  private ModelMapper mapper;
    @GetMapping("api/public/getCategories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam (name = "pageNumber" , defaultValue = AppConst.pageNumber , required = false) Integer pagerNumber ,
                                                             @RequestParam(name="pageSize",  defaultValue = AppConst.pageSize , required = false) Integer pageSize,
                                                             @RequestParam(name="sortDir" , defaultValue = AppConst.sortDir , required = false) String sortDir,
                                                             @RequestParam(name="sort",  defaultValue = AppConst.basedOn , required = false) String sortBy) {

      CategoryResponse categoryResponse =categoryService.getAllCategories(pagerNumber,pageSize , sortBy , sortDir);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PostMapping("api/admin/addCategory")
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(mapper.map(categoryDTO,CategoryResponse.class),HttpStatus.CREATED);
    }

    @DeleteMapping("api/admin/deleteCategory/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {

        CategoryDTO categoryDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(mapper.map(categoryDTO,CategoryResponse.class),HttpStatus.OK);

    }

    @PutMapping("api/admin/updateCategory")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO ) {

            String status = categoryService.updateCategory(categoryDTO);
            return new ResponseEntity<>(status,HttpStatus.OK);

    }
}
