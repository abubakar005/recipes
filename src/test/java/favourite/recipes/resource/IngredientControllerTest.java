package favourite.recipes.resource;

import favourite.recipes.dto.CreateIngredientRequestDto;
import favourite.recipes.dto.UpdateIngredientRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.service.IngredientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @Test
    @DisplayName("test_create_ingredient_successfully")
    public void test_create_ingredient_successfully() {

        when(ingredientService.createIngredient(any(CreateIngredientRequestDto.class))).thenReturn(getMockedIngredient());

        CreateIngredientRequestDto request = createIngredientRequest();
        Ingredient response = ingredientController.createIngredient(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isSameAs(1l);
    }

    @Test
    @DisplayName("test_update_ingredient_successfully")
    public void test_update_ingredient_successfully() {

        when(ingredientService.updateIngredient(any(UpdateIngredientRequestDto.class))).thenReturn(getMockedIngredient());

        UpdateIngredientRequestDto request = updateIngredientRequest();
        Ingredient response = ingredientController.updateIngredient(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isSameAs(1l);
        assertThat(response.getName()).isSameAs("Chili Pepper");
    }

    @Test
    @DisplayName("test_get_ingredient_successfully")
    public void test_get_ingredient_successfully() {

        when(ingredientService.findById(anyLong())).thenReturn(getMockedIngredient());

        Ingredient response = ingredientController.getIngredient(1);
        assertThat(response.getId()).isSameAs(1l);
    }

    @Test
    @DisplayName("test_list_ingredients")
    public void test_list_ingredients() {

        when(ingredientService.findAll()).thenReturn(Arrays.asList(getMockedIngredient()));

        List<Ingredient> ingredientList = ingredientController.findAllIngredients();

        assertThat(1).isSameAs(ingredientList.size());
        assertThat(1l).isSameAs(ingredientList.get(0).getId());
    }

    @Test
    @DisplayName("test_delete_ingredient_successfully")
    public void test_delete_ingredient_successfully() {

        doNothing().when(ingredientService).deleteById(anyLong());
        ingredientController.deleteIngredient(5);
    }

    private Ingredient getMockedIngredient() {
        return Ingredient.builder()
                .id(1l)
                .name("Chili Pepper")
                .build();
    }

    private CreateIngredientRequestDto createIngredientRequest() {
        return CreateIngredientRequestDto.builder()
                .name("Chili Pepper")
                .build();
    }

    private UpdateIngredientRequestDto updateIngredientRequest() {
        return UpdateIngredientRequestDto.builder()
                .id(1)
                .name("Chili Pepper")
                .build();
    }
}