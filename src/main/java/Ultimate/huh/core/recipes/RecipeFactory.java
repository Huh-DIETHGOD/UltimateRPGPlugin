package Ultimate.huh.core.recipes;

import Ultimate.huh.core.recipes.impl.KeyFactory;
import Ultimate.huh.core.recipes.impl.Recipe;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.util.Scanner;

public class RecipeFactory extends Recipe {
    static KeyFactory keyFactory = new KeyFactory();

    public RecipeFactory(String name, Object[] recipe, Object result, NamespacedKey key) {
        super(name, recipe, result, key);
    }

    /**
     * 读取自定义文件并返回recipe
     * @return
     * @throws IOException
     */
    public static List<Object> readRecipes() throws IOException, InvalidConfigurationException {
        String filePath = "";
        List<Object> data = new ArrayList<>();
        FileConfiguration file = null;
        file.load(filePath);

        // file structure:
        // Recipe:
        //  Name: abc
        //  Result: abc
        //  Resource: , , , , , , , ,

        if (file.getConfigurationSection("Recipe") != null) {
            String name = file.getString("Recipe.Name");
            String result = file.getString("Recipe.Result");
            String resource = file.getString("Recipe.Resource");

            // 将字符串转换为数组
            String[] resourceArray = resource.split(",");
            Object[] resourceObject = new Object[resourceArray.length];
            for (int i = 0; i < resourceArray.length; i++) {
                resourceObject[i] = resourceArray[i];
            }

            data.add(name);
            for (int i = 0; i < resourceObject.length; i++) {
                data.add(resourceObject[i]);
            }
            data.add(result);
            data.add(keyFactory.generateKey(name));
        }
        // data structure: [name, result, material1, material2, material3, material4, material5, material6, material7, material8, material9]
        return data;
    }

    public void registerRecipes() throws IOException, InvalidConfigurationException {
        List<Object> data = readRecipes();

        try {
            String name = (String) data.get(0);
            Object[] recipe = new Object[data.size() - 3];
            for (int i = 0; i < recipe.length; i++) {
                recipe[i] = data.get(i + 1);
            }
            Object result = data.get(data.size() - 2);
            NamespacedKey key = (NamespacedKey) data.get(data.size() - 1);
            RecipeFactory recipeFactory = new RecipeFactory(name, recipe, result, key);
            recipeFactory.registerRecipes();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }





    private static List<String> listYamlFiles(String directoryPath) {
        try {
            return Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .map(path -> path.toAbsolutePath().toString())
                    .filter(path -> path.endsWith(".yaml"))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
