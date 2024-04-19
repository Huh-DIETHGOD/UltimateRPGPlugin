package Ultimate.huh.core.config.language;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class LanguageSetting {

    private String language;
    private static UltimateRPGPlugin instance;
    FileConfiguration config = instance.getConfig();

    public void Language(String language) {
        language = config.getString("Ultimate.language.language");
        if (language == null) {
            config.set("Ultimate.language.language", "English");
        }
    }

    public static void setInstance(UltimateRPGPlugin instance) {
        LanguageSetting.instance = instance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
