package favourite.recipes.converter;

import favourite.recipes.dto.CreateRecipeRequestDto;
import favourite.recipes.dto.RecipeResponseDto;
import favourite.recipes.dto.UpdateRecipeRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.model.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeConverter {

    Recipe createRecipeRequestDtoToRecipe(CreateRecipeRequestDto request, Set<Ingredient> ingredients);
    Recipe updateRecipeRequestDtoToRecipe(UpdateRecipeRequestDto request, Set<Ingredient> ingredients, Recipe recipe);
    RecipeResponseDto recipeToRecipeResponseDto(Recipe recipe, List<String> ingredientList);
}
