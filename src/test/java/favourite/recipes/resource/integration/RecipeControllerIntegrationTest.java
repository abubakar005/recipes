package favourite.recipes.resource.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import favourite.recipes.dto.CreateRecipeRequestDto;
import favourite.recipes.dto.UpdateRecipeRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.model.Recipe;
import favourite.recipes.repository.IngredientRepository;
import favourite.recipes.repository.RecipeRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeControllerIntegrationTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String BASE_URL = "/api/v1/recipe";
    private static Long recipeId;
    private static Long ingredientId;

    @Test
    @DisplayName("test_create_recipe_successfully")
    public void A_test_create_recipe_successfully() throws Exception {

        createIngredient();
        CreateRecipeRequestDto request = createRecipeRequest();

        recipeId =
                objectMapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                post(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(request)))
                                        .andExpect(status().isCreated())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                Ingredient.class)
                        .getId();

        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(
                        () -> new IllegalStateException("New recipe has not been saved in the repository"));

        assertTrue(Optional.ofNullable(recipe).isPresent());
        assertThat(recipe.getName(),
                equalTo(request.getRecipeName()));
    }

    @Test
    @DisplayName("test_create_recipe_failed")
    public void B_test_create_recipe_failed() throws Exception {

        CreateRecipeRequestDto request = createRecipeRequest();

        objectMapper
                .readValue(
                        mockMvc
                                .perform(
                                        post(BASE_URL)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isInternalServerError())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Ingredient.class)
                .getId();
    }

    @Test
    @DisplayName("test_update_recipe_successfully")
    public void C_test_update_recipe_successfully() throws Exception {

        UpdateRecipeRequestDto request = updateRecipeRequest();

        recipeId =
                objectMapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                put(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(request)))
                                        .andExpect(status().isOk())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                Ingredient.class)
                        .getId();

        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(
                        () -> new IllegalStateException("Recipe has not been updated in the repository"));

        assertTrue(Optional.ofNullable(recipe).isPresent());
        assertThat(recipe.getName(),
                equalTo(request.getRecipeName()));
    }

    @Test
    @DisplayName("find_all_recipes")
    public void D_find_all_recipes() throws Exception {
        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize((int) recipeRepository.count())));
    }

    @Test
    @DisplayName("find_recipe_by_id")
    public void E_find_recipe_by_id() throws Exception {
        mockMvc
                .perform(get(BASE_URL+"/"+recipeId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", equalTo("Recipe 2")));
    }

    @Test
    @DisplayName("find_recipe_by_id_failed")
    public void F_find_recipe_by_id_failed() throws Exception {
        mockMvc
                .perform(get(BASE_URL+"/"+0))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete_recipe_by_id")
    public void G_delete_recipe_by_id() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL+"/"+recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        deleteIngredient();
    }

    private CreateRecipeRequestDto createRecipeRequest() {
        return CreateRecipeRequestDto.builder()
                .recipeName("Recipe 1")
                .vegetarian(true)
                .numberOfServings(2)
                .ingredients(Arrays.asList(ingredientId))
                .instructions("No")
                .build();
    }

    private UpdateRecipeRequestDto updateRecipeRequest() {
        return UpdateRecipeRequestDto.builder()
                .id(recipeId)
                .recipeName("Recipe 2")
                .vegetarian(true)
                .numberOfServings(2)
                .ingredients(Arrays.asList(ingredientId))
                .instructions("No")
                .build();
    }

    // For Recipe testing
    private void createIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Potato");
        ingredientRepository.save(ingredient);
        ingredientId = ingredient.getId();
    }

    private void deleteIngredient() {
        ingredientRepository.deleteById(ingredientId);
    }
}
