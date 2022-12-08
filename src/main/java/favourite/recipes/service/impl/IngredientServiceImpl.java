package favourite.recipes.service.impl;

import favourite.recipes.dto.CreateIngredientRequestDto;
import favourite.recipes.dto.UpdateIngredientRequestDto;
import favourite.recipes.enums.Error;
import favourite.recipes.exception.AlreadyExistsException;
import favourite.recipes.exception.NotFoundException;
import favourite.recipes.model.Ingredient;
import favourite.recipes.repository.IngredientRepository;
import favourite.recipes.service.IngredientService;
import favourite.recipes.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;


    @Override
    public Ingredient createIngredient(CreateIngredientRequestDto request) {

        // Checking duplicate ingredient name
        Optional<Ingredient> oldIngredient = ingredientRepository.findByName(request.getName());

        if(oldIngredient.isPresent())
            throw new AlreadyExistsException(Error.INGREDIENT_ALREADY_EXISTS.getCode(), String.format(Error.INGREDIENT_ALREADY_EXISTS.getMsg(), oldIngredient.get().getName()));

        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setCreatedBy(Constants.SYSTEM_USER);
        ingredient.setCreationDate(LocalDateTime.now());

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(UpdateIngredientRequestDto request) {

        Ingredient ingredient = findById(request.getId());

        // Checking duplicate ingredient name
        if(!ingredient.getName().equals(request.getName())) {
            Optional<Ingredient> oldIngredient = ingredientRepository.findByName(request.getName());

            if(oldIngredient.isPresent())
                throw new AlreadyExistsException(Error.INGREDIENT_ALREADY_EXISTS.getCode(), String.format(Error.INGREDIENT_ALREADY_EXISTS.getMsg(), oldIngredient.get().getName()));
        }

        ingredient.setName(request.getName());
        ingredient.setUpdatedBy(Constants.SYSTEM_USER);
        ingredient.setUpdatedDate(LocalDateTime.now());
        ingredientRepository.save(ingredient);

        return ingredient;
    }

    @Override
    public Set<Ingredient> getIngredientsByIds(List<Long> ingredientIds) {
        return ingredientIds.stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }

    @Override
    public Ingredient findById(long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Error.INGREDIENT_NOT_FOUND.getCode(), String.format(Error.INGREDIENT_NOT_FOUND.getMsg(), id)));
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        // Checking if exists
        findById(id);
        ingredientRepository.deleteById(id);
    }
}
