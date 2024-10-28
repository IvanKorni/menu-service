package com.menuservice.dto;

import lombok.Builder;
import lombok.Data;
import com.menuservice.storage.model.Category;
import com.menuservice.storage.model.IngredientCollection;

import java.math.BigDecimal;

@Data
@Builder
public class CreateMenuRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private long timeToCook;
    private double weight;
    private String imageUrl;
    private IngredientCollection ingredientCollection;
}
