package favourite.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "recipe")
public class Recipe extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String name;

    @Column(name = "is_vegetarian")
    private boolean vegetarian;

    @Column
    private int numberOfServings;

    @Column
    private String instructions;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Ingredient> recipeIngredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return vegetarian == recipe.vegetarian &&
                numberOfServings == recipe.numberOfServings &&
                Objects.equals(id, recipe.id) &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
