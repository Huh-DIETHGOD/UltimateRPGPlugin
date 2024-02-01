package Ultimate.huh.core.config.language;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class LanguageSetting {
    private String language = "English";
    private static UltimateRPGPlugin instance;
    FileConfiguration config = instance.getConfig();

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        if (language != null) {
            config.set("Ultimate.language.language", language);
        }
    }
}
