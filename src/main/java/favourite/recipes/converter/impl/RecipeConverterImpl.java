package favourite.recipes.converter.impl;

import favourite.recipes.converter.RecipeConverter;
import favourite.recipes.dto.CreateRecipeRequestDto;
import favourite.recipes.dto.RecipeResponseDto;
import favourite.recipes.dto.UpdateRecipeRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.model.Recipe;
import favourite.recipes.util.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class RecipeConverterImpl implements RecipeConverter {

    @Override
    public Recipe createRecipeRequestDtoToRecipe(CreateRecipeRequestDto request, Set<Ingredient> ingredients) {

        Recipe recipe = new Recipe();
        recipe.setName(request.getRecipeName());
        recipe.setVegetarian(request.isVegetarian());
        recipe.setNumberOfServings(request.getNumberOfServings());
        recipe.setRecipeIngredients(ingredients);
        recipe.setInstructions(request.getInstructions());
        recipe.setCreatedBy(Constants.SYSTEM_USER);
        recipe.setCreationDate(LocalDateTime.now());

        return recipe;
    }

    @Override
    public Recipe updateRecipeRequestDtoToRecipe(UpdateRecipeRequestDto request, Set<Ingredient> ingredients, Recipe recipe) {

        recipe.setName(request.getRecipeName());
        recipe.setVegetarian(request.isVegetarian());
        recipe.setNumberOfServings(request.getNumberOfServings());
        recipe.setRecipeIngredients(ingredients);
        recipe.setInstructions(request.getInstructions());
        recipe.setUpdatedBy(Constants.SYSTEM_USER);
        recipe.setUpdatedDate(LocalDateTime.now());

        return recipe;
    }

    @Override
    public RecipeResponseDto recipeToRecipeResponseDto(Recipe recipe, List<String> ingredientList) {

         return RecipeResponseDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .numberOfServings(recipe.getNumberOfServings())
                .vegetarian(recipe.isVegetarian())
                .instructions(recipe.getInstructions())
                .recipeIngredients(ingredientList)
                .creationDate(recipe.getCreationDate())
                .createdBy(recipe.getCreatedBy())
                .updatedDate(recipe.getUpdatedDate())
                .updatedBy(recipe.getUpdatedBy())
                .build();
    }
}
