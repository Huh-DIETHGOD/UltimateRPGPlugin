package Ultimate.huh.external.replacefiles.terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Terminal extends JFrame{

    public Terminal(){
        setTitle("Ult-Terminal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JButton openListener = new JButton("打开新窗口");
        add(openListener);
        openListener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void showTerm(){
        SwingUtilities.invokeLater(() -> {
            Terminal frame = new Terminal();
            frame.setVisible(true);
        });
    }
}
