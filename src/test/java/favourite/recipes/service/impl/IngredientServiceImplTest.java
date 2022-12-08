package favourite.recipes.service.impl;

import favourite.recipes.dto.CreateIngredientRequestDto;
import favourite.recipes.dto.UpdateIngredientRequestDto;
import favourite.recipes.model.Ingredient;
import favourite.recipes.repository.IngredientRepository;
import favourite.recipes.service.IngredientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService = new IngredientServiceImpl();

    @Test
    public void test_create_ingredient_successfully() {

        CreateIngredientRequestDto request = createIngredientRequest();

        when(ingredientRepository.findByName(any())).thenReturn(Optional.empty());
        when(ingredientRepository.save(any())).thenReturn(createIngredient());

        Ingredient ingredient = ingredientService.createIngredient(request);
        assertThat(ingredient.getName()).isSameAs("Chili Pepper");
    }

    @Test
    public void test_update_ingredient_successfully() {

        UpdateIngredientRequestDto request = updateIngredientRequest();

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(createIngredient()));
        when(ingredientRepository.save(any())).thenReturn(createIngredient());

        Ingredient ingredient = ingredientService.updateIngredient(request);
        assertThat(ingredient.getName()).isSameAs("Chili Pepper");
    }

    @Test
    public void test_get_ingredient_successfully() {

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(createIngredient()));

        Ingredient ingredient = ingredientService.findById(1);
        assertThat(ingredient.getId()).isSameAs(1l);
    }

    @Test
    public void test_get_all_ingredient_successfully() {

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(createIngredient()));

        List<Ingredient> ingredients = ingredientService.findAll();
        assertThat(ingredients.get(0).getId()).isSameAs(1l);
    }

    @Test
    public void test_delete_ingredient_successfully() {

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(createIngredient()));

        ingredientService.deleteById(100l);
        verify(ingredientRepository, times(1)).deleteById(100l);
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

    public static Ingredient createIngredient() {
        return Ingredient.builder()
                .id(1l)
                .name("Chili Pepper")
                .build();
    }
}