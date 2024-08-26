package Ultimate.huh.core.recipes;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.recipes.impl.KeyFactory;
import Ultimate.huh.core.recipes.impl.Recipe;
import Ultimate.huh.core.utils.UtilGetJarLocation;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.util.Scanner;

public class RecipeFactory extends Recipe {
    KeyFactory keyFactory = new KeyFactory();
    UtilGetJarLocation util;

    public RecipeFactory(String name, Object[] recipe, Object result, NamespacedKey key) {
        super(name, recipe, result, key);
    }

    /**
     * 读取自定义文件并返回recipe
     * @return
     * @throws IOException
     */
    public Object[][] readRecipes() throws IOException, InvalidConfigurationException, URISyntaxException {
        String jarPath = util.getJarLocationByURL() + "/UltimateRPGPlugin/recipes";
        List<String> configPath = this.listYamlFiles(jarPath);
        FileConfiguration file[] = null;

        Object[][] data = new Object[configPath.size()][3];

        for (int i = 0; i < configPath.size(); i++) {
            file[i].load(configPath.get(i));

            // file structure:
            // Recipe:
            //  Name: abc
            //  Result: abc
            //  Resource: , , , , , , , ,

            // data structure: [[name, result, NameSpacedKey, material1, material2, material3, material4, material5, material6, material7, material8, material9], ...]

            if (file[i].getConfigurationSection("Recipe") != null) {
                String name = file[i].getString("Recipe.Name");
                String result = file[i].getString("Recipe.Result");
                String resource = file[i].getString("Recipe.Resource");

                // 将字符串转换为数组
                String[] resourceArray = resource.split(",");
                Object[] resourceObject = new Object[resourceArray.length];
                for (int j = 0; j < resourceArray.length; j++) {
                    resourceObject[j] = resourceArray[j];
                }

                data[i][0] = name;
                data[i][1] = result;
                data[i][2] = keyFactory.generateKey(name);
                for (int j = 0; j < resourceObject.length; j++) {
                    data[i][2+j] = resourceObject[j];
                }
            }

        }
        return data;
    }

    public void registerRecipes() throws IOException, InvalidConfigurationException, URISyntaxException {
        // 列为2维Obj组
        Object[][] data = readRecipes();
        try {
            for (Object[] recipeData : data) {

                String name = (String) recipeData[0];
                Object[] recipe = new Object[8];
                for (int i = 3; i < 12; i++) {
                    recipe[i] = recipeData[i];
                }
                Object result = recipeData[1];
                NamespacedKey key = (NamespacedKey) recipeData[2];
                RecipeFactory recipeFactory = new RecipeFactory(name, recipe, result, key);
                recipeFactory.registerRecipes();

            }
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
