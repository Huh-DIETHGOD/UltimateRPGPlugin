package Ultimate.huh.external.replacefiles.terminal;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.Bukkit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.Desktop;
import java.awt.Window;

/**
 * 通过JFrame enableEvents和processWindowEvents实现原生窗口的隐藏
 */
public class originalTerminalBlocker extends JFrame{
    public originalTerminalBlocker() {
        Window tarWindow = termTracker();
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        tarWindow.setVisible(false);
    }

    /**
     * 通过Java Swing的getName()函数找寻原生控制台所在窗口并返回
     * @return
     */
    protected static Window termTracker(){
        //String path = instance.getClass().getResource("").getPath();
        String core = "spigot-" + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".jar";
        //String termPath = path.replace("plugins", core);
        Window[] windows = Window.getWindows();
        Window tarWindow = null;
        for(Window window : windows) {
            if (window.getName() != null && window.getName().equals(core)) {
                tarWindow = window;
                return tarWindow;
            }
        }
        return tarWindow;
    }
}
