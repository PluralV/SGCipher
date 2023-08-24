package SGCipherMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class GUI{


    private MenuPanel menuPanel;
    private TFPanel tfPanel;
    private JFrame frame;

    //hi my name is ting
    public GUI(){
        frame = new JFrame("SGCipher");

        switchToMenuPanel();
    }

    void switchToMenuPanel(){
        menuPanel = new MenuPanel(this);
        setFrameDecorationAndDimensions(menuPanel);
    }

    void switchToTFPanel(){
        tfPanel = new TFPanel(this);
        setFrameDecorationAndDimensions(tfPanel);
    }

    void setFrameDecorationAndDimensions(JPanel panel){
        frame.setForeground(new Color(100,150,200));
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void close(){
        frame.dispose();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(false);
                new GUI();
            }
        });
    }
}
