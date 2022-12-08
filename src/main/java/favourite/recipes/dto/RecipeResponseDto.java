package favourite.recipes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Tag(name = "RecipeResponseDto", description = "Recipe Response DTO")
public class RecipeResponseDto {

    @Schema(name = "id", description = "Recipe Id", type = "int", required = true)
    private long id;

    @Schema(name = "name", description = "Recipe Name", type = "String", required = true)
    private String name;

    @Schema(name = "vegetarian", description = "Is Vegetarian", type = "boolean", required = true)
    private boolean vegetarian;

    @Schema(name = "numberOfServings", description = "Number Of Servings", type = "int", required = true)
    private int numberOfServings;

    @Schema(name = "instructions", description = "Instructions", type = "String", required = true)
    private String instructions;

    @Schema(name = "recipeIngredients", description = "Recipe Ingredients", type = "String", required = true)
    private List<String> recipeIngredients;

    @Schema(name = "creationDate", description = "Creation Date", type = "Date", required = true)
    private LocalDateTime creationDate;

    @Schema(name = "createdBy", description = "Created By", type = "String", required = true)
    private String createdBy;

    @Schema(name = "updatedDate", description = "Updated Date", type = "Date", required = true)
    private LocalDateTime updatedDate;

    @Schema(name = "updatedBy", description = "Updated By", type = "String", required = true)
    private String updatedBy;
}
