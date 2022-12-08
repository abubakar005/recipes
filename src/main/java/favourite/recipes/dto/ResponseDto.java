package favourite.recipes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ResponseDto", description = "Error Response DTO")
public class ResponseDto {

    @Schema(name = "code", description = "Error Code", type = "int", required = true)
    private int code;

    @Schema(name = "message", description = "Error Message", type = "String", required = true)
    private String message;
}
