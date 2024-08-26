package Ultimate.huh.core.utils;

import java.io.File;
import java.net.URL;

public class UtilGetJarLocation {
    public String getJarLocationByClass() {
        return UtilGetJarLocation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    public String getJarLocationByURL(){
        URL jar = UtilGetJarLocation.class.getProtectionDomain().getCodeSource().getLocation();
        File tar = new File(jar.getPath());
        return tar.getAbsolutePath();
    }
}
