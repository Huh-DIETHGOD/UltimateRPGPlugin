package Ultimate.huh.external.impl;

import java.io.File;
import java.io.Writer;

public interface ServerCoreInject {
    File open(File file);
    boolean write(File targetFile, Writer writer);


}
