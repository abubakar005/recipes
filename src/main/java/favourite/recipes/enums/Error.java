package favourite.recipes.enums;

public enum Error {

    RECIPE_NOT_FOUND(1001, "Recipe not found against the Id %s"),
    INGREDIENT_NOT_FOUND(1002, "Ingredient not found against the Id %s"),
    INGREDIENT_ALREADY_EXISTS(1003, "Ingredient already exists with the name %s"),
    RECIPE_ALREADY_EXISTS(1004, "Recipe already exists with the name %s"),

    ;

    private final int code;
    private final String msg;

    Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
