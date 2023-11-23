package Ultimate.huh.core.expansion;

public final class Environment {
    private final boolean isSpigot;
    private final String version;
    private Boolean matchVersion;

    public Environment(String version, boolean isSpigot) {
        this.version = version;
        this.isSpigot = isSpigot;
    }

    public String getVersion() {
        return this.version == null ? "unknown" : this.version;
    }

    public Boolean getVersionMatch() {
        return this.version == "1.16.5" ? true : false;
    }
    public boolean isSpigot() {
        return this.isSpigot;
    }
}
