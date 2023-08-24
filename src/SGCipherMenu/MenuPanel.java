package SGCipherMenu;
import SGCipher.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MenuPanel extends JPanel {
    private boolean first_impulse = true;
    private GUI gui;

    public MenuPanel(GUI gui){
        super();
        this.gui = gui;
        this.setLayout(null);

        JButton firstImpulse = new JButton("MAKE NEW CODENAMES");
        firstImpulse.setBounds(375,70,250,30);
        firstImpulse.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,14));
        firstImpulse.setForeground(new Color(255,255,255));
        firstImpulse.setBackground(new Color(75,100,50));
        firstImpulse.addActionListener(e -> {
            first_impulse = !first_impulse;
            if (first_impulse){
                firstImpulse.setText("MAKE NEW CODENAMES");
                firstImpulse.setBackground(new Color(75,100,50));
            }
            else{
                firstImpulse.setText("USE OLD CODENAMES");
                firstImpulse.setBackground(new Color(150,0,0));
            }
        });
        this.add(firstImpulse);
        firstImpulse.setVisible(true);

        JLabel fileNameInputLabel = new JLabel("Enter move order file name here:");
        fileNameInputLabel.setHorizontalAlignment(JLabel.CENTER);
        fileNameInputLabel.setBounds(100,150,350,50);
        this.add(fileNameInputLabel);
        fileNameInputLabel.setVisible(true);

        String defaultDir = System.getProperty("user.dir")+"\\src\\savedata";
        String defaultFilename = "test";
        File filename_and_dir = new File(System.getProperty("user.dir")+"\\src\\savedata\\directory_and_filename.txt");

        try {
            Scanner sc = new Scanner(filename_and_dir);
            defaultDir = sc.nextLine();
            if (defaultDir.contentEquals("%")) defaultDir = System.getProperty("user.dir")+"\\src\\savedata\\";
            defaultFilename = sc.nextLine();
            if (defaultFilename.contentEquals("%")) defaultFilename = "test";
        }catch(Exception e){
            JOptionPane.showMessageDialog(new JFrame(),"Warning: missing filename/directory file from source");
        }


        JTextField fileNameInput = new JTextField();
        fileNameInput.setText(defaultFilename);
        fileNameInput.setBounds(100,200,350,25);
        this.add(fileNameInput);
        fileNameInput.setVisible(true);



        JLabel directoryInputLabel = new JLabel("Enter move order directory name here:");
        directoryInputLabel.setHorizontalAlignment(JLabel.CENTER);
        directoryInputLabel.setBounds(550,150,350,50);
        this.add(directoryInputLabel);
        directoryInputLabel.setVisible(true);

        JTextField directoryInput = new JTextField();
        directoryInput.setBounds(550,200,350,25);
        directoryInput.setText(defaultDir);
        this.add(directoryInput);
        directoryInput.setVisible(true);


        JLabel title = new JLabel();
        title.setSize(1000,100);
        title.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,24));
        title.setText("STRIKE GROUP CIPHER");
        title.setVisible(true);
        title.setBackground(new Color(0,0,0,0));
        title.setHorizontalAlignment(JLabel.CENTER);
        this.add(title);

        JButton help = new JButton("help");
        help.setBounds(470,110,60,25);
        help.setBackground(new Color(100,100,100));
        help.setForeground(Color.WHITE);
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(),"Hello and welcome to SGCipher!\n" +
                        "This program is made for the game SFB PQ-17, and allows you to randomly assign callsigns to " +
                        "in-game naval task forces by \nediting their movement files.\nSimply enter the name of your movement file " +
                        "and the directory it can be found in, and press encode. \nPress the" +
                        " \"MAKE NEW CODENAMES\" button to determine whether the program will generate new codenames or use " +
                        "the \nprevious ones (does not work on the first time, but they do save after closing).\nIf you want to change what task forces the program" +
                        " looks for in your file, press \"edit TFs\".\nThis menu will allow you to edit the log of task forces" +
                        " which determines what names the encoder looks for when assigning \nand replacing callsigns.");
            }
        });
        this.add(help);
        help.setVisible(true);

        JButton addTaskForces = new JButton("Edit TFs");
        addTaskForces.setBounds(425,270,150,35);
        addTaskForces.setBackground(new Color(60,180,255));
        addTaskForces.setForeground(Color.WHITE);
        addTaskForces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.switchToTFPanel();
            }
        });
        this.add(addTaskForces);
        addTaskForces.setVisible(true);

        JButton start = new JButton("ENCODE!");
        start.setBackground(new Color(0,100,150));
        start.setForeground(Color.WHITE);
        start.setSize(new Dimension(100,50));
        start.setBounds(450,450,100,50);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newFileName = fileNameInput.getText();
                    String newDir = directoryInput.getText();

                    String interior_path = System.getProperty("user.dir") + "\\src\\savedata\\";

                    NotesDocReader.readSGTextFile("callsign_log", interior_path);
                    NotesDocReader.editMoveOrderDoc(fileNameInput.getText(),first_impulse,
                            directoryInput.getText(),
                            interior_path);

                    FileOutputStream fos = new FileOutputStream(interior_path+"directory_and_filename.txt");
                    String newdirandnm = newDir+"\n"+newFileName;
                    fos.write(newdirandnm.getBytes(StandardCharsets.UTF_8));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(),"Choose an existing file to read.");
                }
            }
        });
        this.add(start);
        start.setVisible(true);

    }

}
