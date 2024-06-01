package Ultimate.huh.external.injector;

import Ultimate.huh.external.impl.ServerCoreInject;

import java.awt.*;
import java.io.File;
import java.io.Writer;
import java.nio.Buffer;

/**
 * This function is used to inject files to the server core
 *
 */
public class Injector implements ServerCoreInject {
    private Buffer buffer;
    private Writer writer;
    private File serverCore = new File("");
    private Container container;
    private Injector(){

    }

    @Override
    public File open(File file) {
        return serverCore;
    }

    @Override
    public File write(File targetFile, Writer writer) {
        return null;
    }
}
