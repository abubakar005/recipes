package favourite.recipes.specification;

import favourite.recipes.dto.RecipeSearchCriteriaDto;
import favourite.recipes.model.Recipe;

import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeSpecification {

    public Specification<Recipe> getRecipes(final RecipeSearchCriteriaDto searchCriteria) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (searchCriteria.getVegetarian() != null)
                predicates.add(criteriaBuilder.equal(root.get("vegetarian"), searchCriteria.getVegetarian()));

            if (searchCriteria.getNumberOfServings() != null && searchCriteria.getNumberOfServings() != 0)
                predicates.add(criteriaBuilder.equal(root.get("numberOfServings"), searchCriteria.getNumberOfServings()));

            if (searchCriteria.getInstructions() != null && !searchCriteria.getInstructions().isEmpty())
                predicates.add(criteriaBuilder.like(root.get("instructions"), "%" + searchCriteria.getInstructions() + "%"));

            query.orderBy(criteriaBuilder.asc(root.get("id")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
