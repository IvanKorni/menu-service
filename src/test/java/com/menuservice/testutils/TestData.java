package com.menuservice.testutils;

import com.menuservice.dto.CreateMenuRequest;
import com.menuservice.dto.UpdateMenuRequest;
import com.menuservice.storage.model.Category;
import com.menuservice.storage.model.Ingredient;
import com.menuservice.storage.model.IngredientCollection;

import java.math.BigDecimal;
import java.util.List;


public class TestData {

    public static IngredientCollection italianSaladIngredients() {
        return new IngredientCollection(
                List.of(
                        new Ingredient(TestConstants.ITALIAN_SALAD_GREENS_INGREDIENT, TestConstants.ITALIAN_SALAD_GREENS_INGREDIENT_CALORIES),
                        new Ingredient(TestConstants.ITALIAN_SALAD_TOMATOES_INGREDIENT, TestConstants.ITALIAN_SALAD_TOMATOES_INGREDIENT_CALORIES)
                )
        );
    }

    public static UpdateMenuRequest updateMenuFullRequest() {
        return UpdateMenuRequest.builder()
                .name("New Cappuccino")
                .price(BigDecimal.valueOf(100.01))
                .timeToCook(2000L)
                .description("New Cappuccino Description")
                .imageUrl("http://images.com/new_cappuccino.png")
                .build();
    }

    public static CreateMenuRequest createMenuRequest() {
        return CreateMenuRequest.builder()
                .name(TestConstants.ITALIAN_SALAD_NAME)
                .description(TestConstants.ITALIAN_SALAD_DESCRIPTION)
                .price(TestConstants.ITALIAN_SALAD_PRICE)
                .category(Category.SALADS)
                .timeToCook(TestConstants.ITALIAN_SALAD_TIME_TO_COOK)
                .weight(TestConstants.ITALIAN_SALAD_WEIGHT)
                .imageUrl(TestConstants.ITALIAN_SALAD_IMAGE_URL)
                .ingredientCollection(italianSaladIngredients())
                .build();
    }
}
