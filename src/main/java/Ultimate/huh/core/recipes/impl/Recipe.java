package Ultimate.huh.core.recipes.impl;

import org.bukkit.NamespacedKey;

import java.io.File;

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

    public File getFile(){
        String filepath = "plugins/UltimateRPG/recipes/recipes.yml";
        return new File(filepath);
    }

    public String getName() {
        return name;
    }

}
