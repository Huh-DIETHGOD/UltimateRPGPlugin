package Ultimate.huh.external.replacefiles.terminal;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.io.File;

/**
 * 通过JFrame创建URPG控制台
 */
public class Terminal extends JFrame{
    private static UltimateRPGPlugin instance;
    private static String location;

    public Terminal(){
        String htmlPath = instance.getClass().getResource("").getPath() .replace("core", "external/replacefiles/terminal/webUI.html");
        location = htmlPath;
        try{
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JEditorPane editorPane = new JEditorPane();
            JScrollPane scrollPane = new JScrollPane(editorPane);
            editorPane.setContentType("text/html");
            editorPane.setEditable(false);
            editorPane.addHyperlinkListener(this::hyperlinkUpdate);
            File file = new File(htmlPath);
            String path = file.getAbsolutePath();
            editorPane.setPage(path);
        } catch (Exception e){
        }
    }

    /**
     * 超链接点击事件
     * @param e
     */
    public void hyperlinkUpdate(HyperlinkEvent e){
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
            try{
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }

    }

    public void showTerm(){
        SwingUtilities.invokeLater(() -> {
            Terminal frame = new Terminal();
            frame.setVisible(true);
        });
    }

    public String getFileLocation(){
        return location;
    }
}
