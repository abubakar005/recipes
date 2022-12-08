package favourite.recipes.service.impl;

/**
 * @author abubakar
 */

import favourite.recipes.converter.RecipeConverter;
import favourite.recipes.dto.CreateRecipeRequestDto;
import favourite.recipes.dto.RecipeResponseDto;
import favourite.recipes.dto.RecipeSearchCriteriaDto;
import favourite.recipes.dto.UpdateRecipeRequestDto;
import favourite.recipes.exception.AlreadyExistsException;
import favourite.recipes.exception.NotFoundException;
import favourite.recipes.model.Ingredient;
import favourite.recipes.model.Recipe;
import favourite.recipes.enums.Error;
import favourite.recipes.repository.RecipeRepository;
import favourite.recipes.service.IngredientService;
import favourite.recipes.service.RecipeService;
import favourite.recipes.specification.RecipeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeConverter recipeConverter;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeSpecification recipeSpecification;


    @Override
    public RecipeResponseDto createRecipe(CreateRecipeRequestDto request) {

        // Checking duplicate Recipe name
        Optional<Recipe> oldRecipe = recipeRepository.findByName(request.getRecipeName());

        if(oldRecipe.isPresent())
            throw new AlreadyExistsException(Error.RECIPE_ALREADY_EXISTS.getCode(), String.format(Error.RECIPE_ALREADY_EXISTS.getMsg(), oldRecipe.get().getName()));

        // Getting ingredients by list of Ids
        Set<Ingredient> ingredients = Optional.ofNullable(request.getIngredients())
                .map(ingredientService::getIngredientsByIds)
                .orElse(null);

        Recipe recipe = recipeConverter.createRecipeRequestDtoToRecipe(request, ingredients);

        recipeRepository.save(recipe);

        return getRecipeResponseDto(recipe, ingredients);
    }

    @Override
    public RecipeResponseDto updateRecipe(UpdateRecipeRequestDto request) {

        Recipe recipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(Error.RECIPE_NOT_FOUND.getCode(), String.format(Error.RECIPE_NOT_FOUND.getMsg(), request.getId())));

        // Checking duplicate ingredient name
        if(!recipe.getName().equals(request.getRecipeName())) {
            Optional<Recipe> oldRecipe = recipeRepository.findByName(request.getRecipeName());

            if(oldRecipe.isPresent())
                throw new AlreadyExistsException(Error.RECIPE_ALREADY_EXISTS.getCode(), String.format(Error.RECIPE_ALREADY_EXISTS.getMsg(), oldRecipe.get().getName()));
        }

        // Getting ingredients by list of Ids
        Set<Ingredient> ingredients = Optional.ofNullable(request.getIngredients())
                .map(ingredientService::getIngredientsByIds)
                .orElse(null);

        recipe = recipeConverter.updateRecipeRequestDtoToRecipe(request, ingredients, recipe);
        recipeRepository.save(recipe);

        return getRecipeResponseDto(recipe, ingredients);
    }

    @Override
    public RecipeResponseDto getRecipeById(long id) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Error.RECIPE_NOT_FOUND.getCode(), String.format(Error.RECIPE_NOT_FOUND.getMsg(), id)));

        return getRecipeResponseDto(recipe, recipe.getRecipeIngredients());
    }

    @Override
    public void deleteRecipeById(long id) {
        // Checking if exists with the input Id
        getRecipeById(id);
        recipeRepository.deleteById(id);
    }

    @Override
    public List<RecipeResponseDto> findAll(RecipeSearchCriteriaDto searchCriteria) {

        // It will return the results based on (isVegetarian, numberOfServings, instructions)
        List<Recipe> list =  recipeRepository.findAll(recipeSpecification.getRecipes(searchCriteria));

        Stream<RecipeResponseDto> recipeResponseDtoStream = list.stream()
                .map(recipe -> getRecipeResponseDto(recipe, recipe.getRecipeIngredients()));

        if(searchCriteria.getIngredientsInclude() != null && !searchCriteria.getIngredientsInclude().isEmpty())
            recipeResponseDtoStream = recipeResponseDtoStream.filter(recipe -> recipe.getRecipeIngredients().containsAll(Arrays.asList(searchCriteria.getIngredientsInclude().split(","))));

        if(searchCriteria.getIngredientsExclude() != null && !searchCriteria.getIngredientsExclude().isEmpty())
            recipeResponseDtoStream = recipeResponseDtoStream.filter(recipe -> recipe.getRecipeIngredients().stream().noneMatch(l -> l.matches(searchCriteria.getIngredientsExclude().replace(",", "|"))));

        return recipeResponseDtoStream.collect(Collectors.toList());
    }

    public RecipeResponseDto getRecipeResponseDto(Recipe recipe, Set<Ingredient> ingredients) {

        List<String> ingredientList = ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());

        return recipeConverter.recipeToRecipeResponseDto(recipe, ingredientList);
    }
}
