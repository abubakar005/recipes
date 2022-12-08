package favourite.recipes.service.impl;

import favourite.recipes.converter.RecipeConverter;
import favourite.recipes.dto.*;
import favourite.recipes.model.Ingredient;
import favourite.recipes.model.Recipe;
import favourite.recipes.repository.RecipeRepository;
import favourite.recipes.service.IngredientService;
import favourite.recipes.specification.RecipeSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeConverter recipeConverter;

    @Mock
    private RecipeSpecification recipeSpecification;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private RecipeServiceImpl recipeService = new RecipeServiceImpl();


    @Test
    public void test_create_recipe_successfully() {

        when(recipeRepository.findByName(any())).thenReturn(Optional.empty());
        when(recipeRepository.save(any())).thenReturn(getMockedRecipe());
        when(ingredientService.getIngredientsByIds(any())).thenReturn(getIngredientSet());
        when(recipeConverter.recipeToRecipeResponseDto(any(), any())).thenReturn(getMockedRecipeResponse());

        RecipeResponseDto response = recipeService.createRecipe(createRecipeRequest());
        assertThat(response.getName()).isSameAs("Recipe");
    }

    @Test
    public void test_update_recipe_successfully() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(getMockedRecipe()));
        when(recipeRepository.save(any())).thenReturn(getMockedRecipe());
        when(ingredientService.getIngredientsByIds(any())).thenReturn(getIngredientSet());
        when(recipeConverter.recipeToRecipeResponseDto(any(), any())).thenReturn(getMockedUpdateRecipeResponse());

        RecipeResponseDto response = recipeService.updateRecipe(updateRecipeRequest());
        assertThat(response.getName()).isSameAs("Recipe 1");
    }

    @Test
    public void test_get_recipe_successfully() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(getMockedRecipe()));
        when(recipeConverter.recipeToRecipeResponseDto(any(), any())).thenReturn(getMockedRecipeResponse());

        RecipeResponseDto response = recipeService.getRecipeById(1);
        assertThat(response.getId()).isSameAs(1l);
    }

    @Test
    public void test_get_all_recipe_successfully() {

        when(recipeSpecification.getRecipes(any())).thenReturn(getMockedSpecification());
        List<RecipeResponseDto> response = recipeService.findAll(new RecipeSearchCriteriaDto());
        //assertThat(response.get(0).getId()).isSameAs(1l);
    }

    @Test
    public void test_delete_recipe_successfully() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(getMockedRecipe()));
        when(recipeConverter.recipeToRecipeResponseDto(any(), any())).thenReturn(getMockedRecipeResponse());

        recipeService.deleteRecipeById(100l);
        verify(recipeRepository, times(1)).deleteById(100l);
    }

    private Set<Ingredient> getIngredientSet() {
        Set<Ingredient> set = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1l);
        ingredient.setName("Potatoes");
        set.add(ingredient);

        return set;
    }

    private CreateRecipeRequestDto createRecipeRequest() {
        return CreateRecipeRequestDto.builder()
                .recipeName("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .ingredients(Arrays.asList(1l, 2l))
                .build();
    }

    private UpdateRecipeRequestDto updateRecipeRequest() {
        return UpdateRecipeRequestDto.builder()
                .id(1l)
                .recipeName("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .ingredients(Arrays.asList(1l, 2l))
                .build();
    }

    private RecipeResponseDto getMockedRecipeResponse() {
        return RecipeResponseDto.builder()
                .id(1l)
                .name("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .recipeIngredients(Arrays.asList("Potatoes"))
                .build();
    }

    private RecipeResponseDto getMockedUpdateRecipeResponse() {
        return RecipeResponseDto.builder()
                .id(1l)
                .name("Recipe 1")
                .vegetarian(true)
                .numberOfServings(3)
                .instructions("No")
                .build();
    }

    private Recipe getMockedRecipe() {
        return Recipe.builder()
                .id(1l)
                .name("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .recipeIngredients(getIngredientSet())
                .build();
    }

    private Specification<Recipe> getMockedSpecification() {
        return (Specification<Recipe>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}