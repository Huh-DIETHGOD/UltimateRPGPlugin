package Ultimate.huh.core.recipes.impl;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class Recipe {

    private String name;
    private Object[] recipe;
    private ItemStack result;
    private NamespacedKey key;
    private ItemStack stack;

    public Recipe(String name, Object[] recipe, Object result, NamespacedKey key) {
        this.name = name;
        this.recipe = recipe;
        this.result = (ItemStack) result;
        this.key = key;
    }

    public String getName() {
        return name;
    }

}
