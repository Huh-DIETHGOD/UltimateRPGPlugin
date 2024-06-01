package Ultimate.huh.external.impl;

import java.io.File;
import java.io.Writer;

public interface ServerCoreInject {
    File open(File file);
    File write(File targetFile, Writer writer);


}
