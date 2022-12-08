package favourite.recipes.resource.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import favourite.recipes.dto.CreateIngredientRequestDto;
import favourite.recipes.dto.UpdateIngredientRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.repository.IngredientRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
public class IngredientControllerIntegrationTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public String BASE_URL = "/api/v1/ingredient";
    static Long ingredientId;


    @Test
    @DisplayName("test_create_ingredient_successfully")
    public void A_test_create_ingredient_successfully() throws Exception {

        CreateIngredientRequestDto request = createIngredientRequest();

        ingredientId =
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

        Ingredient ingredient = ingredientRepository
                .findById(ingredientId)
                .orElseThrow(
                        () -> new IllegalStateException("New ingredient has not been saved in the repository"));

        assertTrue(Optional.ofNullable(ingredient).isPresent());
        assertThat(ingredient.getName(),
                equalTo(request.getName()));
    }

    @Test
    @DisplayName("test_create_ingredient_failed")
    public void B_test_create_ingredient_failed() throws Exception {

        CreateIngredientRequestDto request = createIngredientRequest();

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
    @DisplayName("test_update_ingredient_successfully")
    public void C_test_update_ingredient_successfully() throws Exception {

        UpdateIngredientRequestDto request = updateIngredientRequest();

        ingredientId =
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

        Ingredient ingredient = ingredientRepository
                .findById(ingredientId)
                .orElseThrow(
                        () -> new IllegalStateException("Ingredient has not been updated in the repository"));

        assertTrue(Optional.ofNullable(ingredient).isPresent());
        assertThat(ingredient.getName(),
                equalTo(request.getName()));
    }

    @Test
    @DisplayName("find_all_ingredients")
    public void D_find_all_ingredients() throws Exception {
        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize((int) ingredientRepository.count())));
    }

    @Test
    @DisplayName("find_ingredient_by_id")
    public void E_find_ingredient_by_id() throws Exception {
        mockMvc
                .perform(get(BASE_URL+"/"+ingredientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", equalTo("Green Tomato")));
    }

    @Test
    @DisplayName("find_ingredient_by_id_failed")
    public void F_find_ingredient_by_id_failed() throws Exception {
        mockMvc
                .perform(get(BASE_URL+"/"+0))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete_ingredient_by_id")
    public void G_delete_ingredient_by_id() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL+"/"+ingredientId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private CreateIngredientRequestDto createIngredientRequest() {
        return CreateIngredientRequestDto.builder()
                .name("Tomato")
                .build();
    }

    private UpdateIngredientRequestDto updateIngredientRequest() {
        return UpdateIngredientRequestDto.builder()
                .id(ingredientId)
                .name("Green Tomato")
                .build();
    }
}
