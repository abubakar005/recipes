package favourite.recipes.resource;

import favourite.recipes.dto.CreateRecipeRequestDto;
import favourite.recipes.dto.RecipeResponseDto;
import favourite.recipes.dto.RecipeSearchCriteriaDto;
import favourite.recipes.dto.UpdateRecipeRequestDto;
import favourite.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
@Tag(name = "Recipe Controller", description = "Controller to handle all about the Recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Recipe API",
            description = "This API will create new recipe")
    public RecipeResponseDto createRecipe(@RequestBody CreateRecipeRequestDto requestDto) {
        return recipeService.createRecipe(requestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value = "recipes", key = "#requestDto.id")
    @Operation(summary = "Update Recipe API",
            description = "This API will update a recipe by Id")
    public RecipeResponseDto updateRecipe(@RequestBody UpdateRecipeRequestDto requestDto) {
        return recipeService.updateRecipe(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find Recipes API",
            description = "This API will find the list of recipes")
    public List<RecipeResponseDto> findRecipes(RecipeSearchCriteriaDto searchCriteria) {
        return recipeService.findAll(searchCriteria);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "recipes", key = "#id")
    @Operation(summary = "Get Recipe API",
            description = "This API will return a recipe by Id")
    public RecipeResponseDto getRecipeById(@PathVariable("id") long id) {
        return recipeService.getRecipeById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "recipes", key = "#id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Recipe API",
            description = "This API will delete recipe by Id")
    public void deleteRecipeById(@PathVariable("id") long id) {
        recipeService.deleteRecipeById(id);
    }
}
