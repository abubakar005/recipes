package favourite.recipes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Tag(name = "CreateRecipeRequestDto", description = "Create Recipe Request DTO")
public class CreateRecipeRequestDto {

    @Schema(name = "recipeName", description = "Recipe Name", type = "String", required = true)
    private String recipeName;

    @Schema(name = "vegetarian", description = "Vegetarian", type = "boolean", required = true)
    private boolean vegetarian;

    @Schema(name = "numberOfServings", description = "Number Of Servings", type = "int", required = true)
    private int numberOfServings;

    @Schema(name = "ingredients", description = "Ingredient List", type = "List", required = true)
    private List<Long> ingredients;

    @Schema(name = "instructions", description = "Instructions", type = "String")
    private String instructions;
}
