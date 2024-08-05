package Ultimate.huh.core.recipes;

import Ultimate.huh.core.recipes.impl.Recipe;
import org.bukkit.NamespacedKey;

public class RecipeFactory extends Recipe {
    public RecipeFactory(String name, Object[] recipe, Object result, NamespacedKey key) {
        super(name, recipe, result, key);
    }
}
