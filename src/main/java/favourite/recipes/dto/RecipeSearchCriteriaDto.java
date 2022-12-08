package favourite.recipes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Tag(name = "RecipeSearchCriteriaDto", description = "Recipe Search Criteria DTO - Search on the basis of criteria or return all Recipes")
public class RecipeSearchCriteriaDto {

    @Schema(name = "vegetarian", description = "Is Vegetarian (true Or false)", type = "boolean", required = true)
    private Boolean vegetarian;

    @Schema(name = "numberOfServings", description = "Number Of Servings like 2", type = "int", required = true)
    private Integer numberOfServings;

    @Schema(name = "ingredientsInclude", description = "Ingredients must be include - should be like 'Eggs,Onions'", type = "String", required = true)
    private String ingredientsInclude;

    @Schema(name = "ingredientsExclude", description = "Ingredients not part of recipe - should be like 'Eggs,Onions'", type = "String", required = true)
    private String ingredientsExclude;

    @Schema(name = "instructions", description = "Instructions like 'No Chicken'", type = "String", required = true)
    private String instructions;
}
