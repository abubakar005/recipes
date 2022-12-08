package favourite.recipes.resource;

import favourite.recipes.dto.*;
import favourite.recipes.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Test
    @DisplayName("test_create_recipe_successfully")
    public void test_create_recipe_successfully() {

        when(recipeService.createRecipe(any(CreateRecipeRequestDto.class))).thenReturn(getMockedRecipeResponseDto());

        CreateRecipeRequestDto request = createRecipeRequest();
        RecipeResponseDto response = recipeController.createRecipe(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isSameAs(1l);
    }

    @Test
    @DisplayName("test_update_recipe_successfully")
    public void test_update_recipe_successfully() {

        when(recipeService.updateRecipe(any(UpdateRecipeRequestDto.class))).thenReturn(getMockedUpdateRecipeResponseDto());

        UpdateRecipeRequestDto request = updateRecipeRequest();
        RecipeResponseDto response = recipeController.updateRecipe(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isSameAs(1l);
        assertThat(response.getName()).isSameAs("Recipe 1");
    }

    @Test
    @DisplayName("test_get_recipe_successfully")
    public void test_get_recipe_successfully() {

        when(recipeService.getRecipeById(anyLong())).thenReturn(getMockedRecipeResponseDto());

        RecipeResponseDto response = recipeController.getRecipeById(1);
        assertThat(response.getId()).isSameAs(1l);
    }

    @Test
    @DisplayName("test_list_recipes")
    public void test_list_recipes() {

        when(recipeService.findAll(any())).thenReturn(Arrays.asList(getMockedRecipeResponseDto()));

        List<RecipeResponseDto> recipesList = recipeController.findRecipes(new RecipeSearchCriteriaDto());

        assertThat(1).isSameAs(recipesList.size());
        assertThat(1l).isSameAs(recipesList.get(0).getId());
    }

    @Test
    @DisplayName("test_delete_recipe_successfully")
    public void test_delete_recipe_successfully() {

        doNothing().when(recipeService).deleteRecipeById(anyLong());
        recipeController.deleteRecipeById(5);
    }

    private RecipeResponseDto getMockedRecipeResponseDto() {
        return RecipeResponseDto.builder()
                .id(1l)
                .name("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .build();
    }

    private RecipeResponseDto getMockedUpdateRecipeResponseDto() {
        return RecipeResponseDto.builder()
                .id(1l)
                .name("Recipe 1")
                .vegetarian(true)
                .numberOfServings(3)
                .instructions("No")
                .build();
    }

    private CreateRecipeRequestDto createRecipeRequest() {
        return CreateRecipeRequestDto.builder()
                .recipeName("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .build();
    }

    private UpdateRecipeRequestDto updateRecipeRequest() {
        return UpdateRecipeRequestDto.builder()
                .id(1l)
                .recipeName("Recipe")
                .vegetarian(true)
                .numberOfServings(2)
                .instructions("No")
                .build();
    }
}