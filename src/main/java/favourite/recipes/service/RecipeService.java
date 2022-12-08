package favourite.recipes.service;

import favourite.recipes.dto.CreateRecipeRequestDto;
import favourite.recipes.dto.RecipeResponseDto;
import favourite.recipes.dto.RecipeSearchCriteriaDto;
import favourite.recipes.dto.UpdateRecipeRequestDto;

import java.util.List;

public interface RecipeService {

    RecipeResponseDto createRecipe(CreateRecipeRequestDto request);
    RecipeResponseDto updateRecipe(UpdateRecipeRequestDto request);
    RecipeResponseDto getRecipeById(long id);
    void deleteRecipeById(long id);
    List<RecipeResponseDto> findAll(RecipeSearchCriteriaDto searchCriteria);
}
