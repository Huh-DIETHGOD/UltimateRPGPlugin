package Ultimate.huh.core.recipes.impl;

import org.bukkit.NamespacedKey;

public class Recipe {

    private String name;
    private Object[] recipe;
    private Object result;
    private NamespacedKey key;

    public Recipe(String name, Object[] recipe, Object result) {
        this.name = name;
        this.recipe = recipe;
        this.result = result;
    }

    public Recipe(String name, Object[] recipe, Object result, NamespacedKey key) {
        this.name = name;
        this.recipe = recipe;
        this.result = result;
        this.key = key;
    }

    public String getName() {
        return name;
    }

}
