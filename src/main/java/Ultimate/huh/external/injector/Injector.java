package Ultimate.huh.external.injector;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.external.impl.ServerCoreInject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * This function is used to inject files to the server core
 */
public class Injector implements ServerCoreInject {
    private Buffer buffer;
    private Writer writer;
    private File serverCore = new File("");
    private UltimateRPGPlugin instance;

    private Injector(){

    }

    /**
     * 获取插件位置
     * @return 插件位置
     */
    public File fileTracker(){
        File file= new File(instance.getClass().getClassLoader().getResource("").getFile());
        String tarPath = file.getAbsolutePath();
        try{
            tarPath = URLDecoder.decode(tarPath, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(tarPath);
    }

    /**
     * 读取jar包所有的文件内容，显示JAR文件内容列表
     * @param jarFileName
     * @throws IOException
     */
    public static void readJARList(String jarFilePath) throws IOException {
        JarFile jarFile = new JarFile(jarFilePath);
        // 枚举获得JAR文件内的实体,即相对路径
        Enumeration en = jarFile.entries();
        // 遍历显示JAR文件中的内容信息
        while (en.hasMoreElements()) {
            // 调用方法显示内容
            process(en.nextElement());
        }
    }

    // 显示对象信息
    private static void process(Object obj) {
        // 对象转化成Jar对象
        JarEntry entry = (JarEntry) obj;
        String name = entry.getName();
        long size = entry.getSize();
        long compressedSize = entry.getCompressedSize();
    }

    /**
     * 读取jar包里面指定文件的内容
     * @param jarFileName jar包文件路径
     * @param fileName  文件名
     * @throws IOException
     */
    public static void readJarFile(String jarFilePath,String fileName) throws IOException{
        JarFile jarFile = new JarFile(jarFilePath);
        JarEntry entry = jarFile.getJarEntry(fileName);
        InputStream input = jarFile.getInputStream(entry);
        readFile(input);
        jarFile.close();
    }


    public static void readFile(InputStream input) throws IOException{
        InputStreamReader in = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(in);
        String line ;
        while((line = reader.readLine())!=null){
            System.out.println(line);
        }
        reader.close();
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    /**
     * 修改Jar包里的文件或者添加文件
     * @param jarFile jar包路径
     * @param entryName 要写的文件名
     * @param data   文件内容
     * @throws Exception
     */
    public static void writeJarFile(String jarFilePath,String entryName,byte[] data) throws Exception{
        //1、首先将原Jar包里的所有内容读取到内存里，用TreeMap保存
        JarFile  jarFile = new JarFile(jarFilePath);
        //可以保持排列的顺序,所以用TreeMap 而不用HashMap
        TreeMap tm = new TreeMap();
        Enumeration es = jarFile.entries();
        while(es.hasMoreElements()){
            JarEntry je = (JarEntry)es.nextElement();
            byte[] b = readStream(jarFile.getInputStream(je));
            tm.put(je.getName(),b);
        }
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFilePath));
        Iterator it = tm.entrySet().iterator();
        boolean has = false;

        //2、将TreeMap重新写到原jar里，如果TreeMap里已经有entryName文件那么覆盖，否则在最后添加
        while(it.hasNext()){
            Map.Entry item = (Map.Entry) it.next();
            String name = (String)item.getKey();
            JarEntry entry = new JarEntry(name);
            jos.putNextEntry(entry);
            byte[] temp ;
            if(name.equals(entryName)){
                //覆盖
                temp = data;
                has = true ;
            }else{
                temp = (byte[])item.getValue();
            }
            jos.write(temp, 0, temp.length);
        }

        if(!has){
            //最后添加
            JarEntry newEntry = new JarEntry(entryName);
            jos.putNextEntry(newEntry);
            jos.write(data, 0, data.length);
        }
        jos.finish();
        jos.close();

    }

    @Deprecated
    @Override
    public File open(File file) {
        return serverCore;
    }

    @Deprecated
    @Override
    public boolean write(File targetFile, Writer writer) {
        return false;
    }
}
