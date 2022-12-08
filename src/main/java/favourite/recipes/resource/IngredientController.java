package favourite.recipes.resource;

import favourite.recipes.dto.CreateIngredientRequestDto;
import favourite.recipes.dto.UpdateIngredientRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.service.IngredientService;
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
@RequestMapping(value = "api/v1/ingredient")
@Tag(name = "Ingredient Controller", description = "Controller to handle all about the Ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Ingredient API",
            description = "This API will create new Ingredient")
    public Ingredient createIngredient(@RequestBody CreateIngredientRequestDto request) {
        return ingredientService.createIngredient(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value = "ingredients", key = "#request.id")
    @Operation(summary = "Update Ingredient API",
            description = "This API will update an Ingredient by Id")
    public Ingredient updateIngredient(@RequestBody UpdateIngredientRequestDto request) {
        return ingredientService.updateIngredient(request);
    }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
   @Cacheable(value = "ingredients", key = "#id")
   @Operation(summary = "Get Ingredient API",
           description = "This API will return a Ingredient by Id")
    public Ingredient getIngredient(@PathVariable(name = "id") long id) {
        return ingredientService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find All Ingredients API",
            description = "This API will find all the available Ingredients")
    public List<Ingredient> findAllIngredients() {
        return ingredientService.findAll();
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "ingredients", key = "#id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Ingredient API",
            description = "This API will delete Ingredient by Id")
    public void deleteIngredient(@PathVariable("id") long id) {
        ingredientService.deleteById(id);
    }
}
