package Ultimate.huh.core.recipes;

import Ultimate.huh.core.recipes.impl.Recipe;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.shell.component.view.event.KeyEvent.Key.a;

/**
 * 读取并转为recipe
 */
public class RecipeReader {
    public static Recipe readRecipes() {
        // 遍历并读取recipes路径下所有yml文件

        String filepath = "plugins/UltimateRPG/recipes/recipes.yml";
        File file = new File(filepath);
        if (!file.exists()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            YamlConfiguration config = new YamlConfiguration();
            // recipe structure:
            // ingredients: [a, b, c, a, b, c, a, b, c]
            // result: d
            String ingredientsStr = config.getString("ingredients");
            String resultStr = config.getString("result");

            String[] ingredients = ingredientsStr.split(",");
            return new Recipe(file.getName(), ingredients, resultStr);
        } catch (IOException e) {
            return null;
        }

    }
}
