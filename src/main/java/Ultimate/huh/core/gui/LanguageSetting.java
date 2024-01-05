package Ultimate.huh.core.gui;

import sun.util.locale.LanguageTag;

public class LanguageSetting {
    enum Language{
        ENGLISH,
        CHINESE
    }
    private String language;
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
