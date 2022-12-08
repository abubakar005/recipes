package favourite.recipes.service;

import favourite.recipes.dto.CreateIngredientRequestDto;
import favourite.recipes.dto.UpdateIngredientRequestDto;
import favourite.recipes.model.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    Ingredient createIngredient(CreateIngredientRequestDto request);
    Ingredient updateIngredient(UpdateIngredientRequestDto request);
    Set<Ingredient> getIngredientsByIds(List<Long> ingredientIds);
    Ingredient findById(long id);
    List<Ingredient> findAll();
    void deleteById(long id);
}
