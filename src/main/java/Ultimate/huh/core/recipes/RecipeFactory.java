package Ultimate.huh.core.recipes;

import Ultimate.huh.core.recipes.impl.KeyFactory;
import Ultimate.huh.core.recipes.impl.Recipe;
import org.bukkit.NamespacedKey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.util.Scanner;

public class RecipeFactory extends Recipe {
    KeyFactory keyFactory = new KeyFactory();

    public RecipeFactory(String name, Object[] recipe, Object result, NamespacedKey key) {
        super(name, recipe, result, key);
    }

    /**
     * 读取自定义文件并返回recipe
     * @return
     * @throws IOException
     */
    public static List<Object> readRecipes() throws IOException {
        String filePath = "";
        List<Object> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        String currentLine;
        boolean inList = false;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine().trim();
            if (currentLine.isEmpty()) {
                continue;
            }

            if (currentLine.equals("-")) {
                inList = true;
                continue;
            }

            if (inList) {
                data.add(currentLine);
            } else {
                if (currentLine.startsWith("- ") || currentLine.startsWith("  ")) {
                    inList = true;
                    data.add(currentLine.substring(2));
                } else {
                    String[] keyValue = currentLine.split(":", 2);
                    if (keyValue.length == 2) {
                        data.add(keyValue[0].trim());
                        data.add(keyValue[1].trim());
                    }
                }
            }
        }
        scanner.close();
        return data;
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
