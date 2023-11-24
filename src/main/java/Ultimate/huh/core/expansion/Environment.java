package Ultimate.huh.core.expansion;

public final class Environment {
    private final boolean isSpigot;
    private final String version;

    public Environment(String version, boolean isSpigot) {
        this.version = version;
        this.isSpigot = isSpigot;
    }

    public boolean matchEnv() {
        if (version == "v1_16_R3" || isSpigot == false) {
            return false;
        } else {
            return true;
        }
    }
}
