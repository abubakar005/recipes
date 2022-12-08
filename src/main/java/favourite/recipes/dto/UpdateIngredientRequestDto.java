package favourite.recipes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Tag(name = "UpdateIngredientRequestDto", description = "Ingredient Update Request DTO")
public class UpdateIngredientRequestDto {

    @Schema(name = "id", description = "Ingredient Id", type = "int", required = true)
    private long id;

    @NotBlank(message = "Ingredient name required")
    @Schema(name = "name", description = "Ingredient Name", type = "String", required = true)
    private String name;
}
