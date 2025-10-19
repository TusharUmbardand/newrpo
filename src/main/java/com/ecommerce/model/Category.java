package com.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Objects;
@Entity
@Data
@AllArgsConstructor
public class Category {

    @NotBlank
    @Size(min = 4 ,message = "Minimum size should be 4")
    private String categoryName;
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    public Category() {
    }


}
