package Ultimate.huh.core.utils;

import Ultimate.huh.core.UltimateRPGPlugin;

public class UtilPlayerPermission {
    private UltimateRPGPlugin instance;
    String[] permissions = (String[]) instance.getDescription().getPermissions().stream().toArray();

}
