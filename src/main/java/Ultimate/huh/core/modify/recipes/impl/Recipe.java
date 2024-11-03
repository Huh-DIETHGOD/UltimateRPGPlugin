package Ultimate.huh.core.modify.recipes.impl;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipe {
    private String name;
    private Object[][] recipe;
    private ItemStack result;
    private NamespacedKey key;
    private ItemMeta meta;

    public Recipe(String name, Object[][] recipe, ItemStack result, NamespacedKey key, ItemMeta meta) {
        this.name = name;
        this.recipe = recipe;
        this.result = result;
        this.key = key;
        this.meta = meta;
    }

    public void itemFunction(){

    }

    public String getName() {
        return name;
    }

    public ItemStack getResult() {
        return result;
    }

}
