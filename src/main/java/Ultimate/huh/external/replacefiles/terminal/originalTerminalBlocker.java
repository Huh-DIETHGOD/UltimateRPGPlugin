package Ultimate.huh.external.replacefiles.terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.Desktop;
import java.awt.Window;

/**
 * 通过JFrame enableEvents和processWindowEvents实现原生窗口的关闭
 */
public class originalTerminalBlocker extends JFrame{
    public originalTerminalBlocker() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setSize(new Dimension(0, 0));
        this.setTitle(null);
    }

    protected static Window termTracker(){
        Window[] window = Desktop.getDesktop().getDesktopWindows();
        JFrame frame = new JFrame();

        Window tarWindow;

        return tarWindow;
    }

    protected void TerminalBlocker(WindowEvent event){
        super.processWindowEvent(event);
        if (event.getID() == WindowEvent.WINDOW_CLOSING){
            System.exit(0);
        }
    }
}
