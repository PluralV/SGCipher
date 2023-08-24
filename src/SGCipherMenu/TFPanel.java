package SGCipherMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TFPanel extends JPanel {

    private GUI gui;
    public TFPanel(GUI gui){
        super();
        this.gui = gui;
        this.setLayout(new BoxLayout(this, 1));

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout());

        JLabel title_and_help = new JLabel("Welcome to the Task Force Editor!");
        title_and_help.setFont(new Font(Font.DIALOG_INPUT,Font.ROMAN_BASELINE,24));
        title_and_help.setVisible(true);
        title_and_help.setBounds(20,0,580,50);
        this.add(title_and_help);

        JLabel help1 = new JLabel("To add a task force, enter its name, map code, and type into " +
                "the text box and press \"add.\""
        );
        this.add(help1);
        help1.setVisible(true);

        JLabel help2 = new JLabel("To remove a task force, type its name into the text box and press \"remove.\""

        );

        JLabel help3 = new JLabel("Task force names are written in the comma separated form \"<NAME>,<MAPCODE>,<TYPE>\":"
        );

        JLabel help4 = new JLabel(
                "a) Name is the name of the task force.");

        JLabel help5 = new JLabel(
                "b) Map code refers to what would be written on its game counter when placed on the map - ");
        JLabel help512 = new JLabel("usually a shorter version of the name.");
        JLabel help6 = new JLabel("c) Type is a number from 1 to 6 designating the type of squadron: " +
                "generic groups (1), strike groups (2),"
               );
        JLabel help7 = new JLabel( "convoys (3), reserve groups (4), Kzinti groups (5), and mercenary groups (6).");
        help1.setBounds(20,40,580,25);
        help2.setBounds(20,55,580,25);
        help3.setBounds(20,75,580,25);
        help4.setBounds(20,90,580,25);
        help5.setBounds(20,105,580,25);
        help512.setBounds(20,120,580,25);
        help6.setBounds(20,135,580,25);
        help7.setBounds(20,150,580,25);

        this.add(help2);
        this.add(help3);
        this.add(help4);
        this.add(help5);
        this.add(help512);
        this.add(help6);
        this.add(help7);
        help2.setVisible(true);
        help3.setVisible(true);
        help4.setVisible(true);
        help5.setVisible(true);
        help512.setVisible(true);
        help6.setVisible(true);
        help7.setVisible(true);

        String[] categories =
                {
                        "Task Force Name",
                        "Map Code",
                        "Task Force Type"
                };

        File cslog = new File(System.getProperty("user.dir")+"\\src\\savedata\\callsign_log.txt");
        Scanner sc;
        ArrayList<String[]> tfList = new ArrayList<>();

        try{
            sc = new Scanner(cslog);

            while (sc.hasNextLine()){
                String tfLine = sc.nextLine();
                if (!tfLine.contentEquals("")){
                    String[] TFLineArr = tfLine.split(",");
                    tfList.add(TFLineArr);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }

        ArrayList<String[]> finaltfs = new ArrayList<>();

        for (String[] s : tfList){
            if (s.length==3){
                s[2] = typeToType(s[2]);
                finaltfs.add(s);
            }
        }

        String[][] finalList = new String[finaltfs.size()][3];
        for (int i = 0; i<finaltfs.size();i++){
            if (finaltfs.get(i).length==3){
                finalList[i] = tfList.get(i);
            }
        }


        JTable tfsTable = new JTable(finalList,categories);
        tfsTable.setPreferredScrollableViewportSize(new Dimension(600,380));
        tfsTable.setFillsViewportHeight(false);
        this.add(tfsTable);
        tfsTable.setVisible(true);

        for (int c = 0; c < tfsTable.getColumnCount(); c++)
        {
            Class<?> col_class = tfsTable.getColumnClass(c);
            tfsTable.setDefaultEditor(col_class, null);        // remove editor
        }

        JScrollPane scroller = new JScrollPane(tfsTable);
        scroller.setBounds(5,200,600,380);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scroller);
        scroller.setVisible(true);

        JLabel taskForceAddLabel = new JLabel("Add or remove a task force here:");
        taskForceAddLabel.setHorizontalAlignment(0);
        taskForceAddLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
        taskForceAddLabel.setBounds(600,185,400,50);
        this.add(taskForceAddLabel);
        taskForceAddLabel.setVisible(true);

        JTextField taskForceAdd = new JTextField();
        taskForceAdd.setBounds(620,235,360,25);
        this.add(taskForceAdd);
        taskForceAdd.setVisible(true);

        JButton tfAdd = new JButton("ADD");
        tfAdd.setBounds(620,270,100,25);
        tfAdd.setBackground(new Color(0,120,0));
        tfAdd.setForeground(Color.WHITE);
        tfAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addedForce = taskForceAdd.getText();
                String[] addedTest = addedForce.split(",");
                if (addedTest.length!=3){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Enter a valid name, map code, and type number!");
                }
                else{
                    try{
                        Integer.parseInt(addedTest[2]);
                        File callsignlog = new File(System.getProperty("user.dir")+
                                "\\src\\savedata\\callsign_log.txt");
                        Scanner scanner = new Scanner(callsignlog);
                        StringBuilder sbr = new StringBuilder();
                        while (scanner.hasNextLine()){
                            String nextLine = scanner.nextLine();
                            if (nextLine.contentEquals(addedForce)){
                                JOptionPane.showMessageDialog(new JFrame(), "Use a task force name that hasn't been used yet!");
                            }
                            sbr.append(nextLine).append("\n");
                        }
                        sbr.append(addedForce);

                        FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+
                                "\\src\\savedata\\callsign_log.txt");
                        fos.write(sbr.toString().getBytes(StandardCharsets.UTF_8));
                    }catch(Exception exception1){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Enter a valid name, map code, and type number!");
                    }
                    gui.switchToTFPanel();
                    JOptionPane.showMessageDialog(new JFrame(),"Task force added successfully.");
                }
            }
        });
        pane.add(tfAdd);
        tfAdd.setVisible(true);

        JButton tfRem = new JButton("REMOVE");
        tfRem.setBounds(880,270,100,25);
        tfRem.setBackground(new Color(120,0,0));
        tfRem.setForeground(Color.WHITE);
        pane.add(tfRem);
        tfRem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String removedForce = taskForceAdd.getText();
                try{
                    File callsignlog = new File(System.getProperty("user.dir")+
                            "\\src\\savedata\\callsign_log.txt");
                    Scanner sc = new Scanner(callsignlog);
                    StringBuilder sbr = new StringBuilder();
                    while (sc.hasNextLine()){
                        String nextLine = sc.nextLine();
                        if (nextLine.length()<removedForce.length()){
                            sbr.append(nextLine).append("\n");
                        }
                        else if (!nextLine.substring(0,removedForce.length()).contentEquals(removedForce)){
                            sbr.append(nextLine).append("\n");
                        }
                    }
                    String finalString;
                    try{finalString = sbr.substring(0,sbr.length()-1);}catch(StringIndexOutOfBoundsException et){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Please enter the name of the TF you wish to remove.");
                        return;
                    }

                    try {
                        FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") +
                                "\\src\\savedata\\callsign_log.txt");
                        fos.write(finalString.getBytes(StandardCharsets.UTF_8));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(),"File write error detected!");
                    }

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(new JFrame(),"Missing callsign_log.txt file!");
                }
                gui.switchToTFPanel();
                JOptionPane.showMessageDialog(new JFrame(),"Task force removed successfully.");

            }
        });
        tfRem.setVisible(true);

        JButton returnToMenu = new JButton("Return to the menu");
        returnToMenu.setBounds(830,505,150,50);
        returnToMenu.setBackground(new Color(50,120,180));
        returnToMenu.setForeground(Color.WHITE);
        returnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.switchToMenuPanel();
            }
        });
        pane.add(returnToMenu);
        returnToMenu.setVisible(true);

        this.add(pane);
        pane.setVisible(true);
    }

    private String typeToType(String num){
        int i = Integer.parseInt(num);

        if (i==2){
            return "Strike Fleet";
        }
        else if (i==5){
            return "Kzinti Squadron";
        }
        else if (i==3){
            return "Convoy";
        }
        else if (i==4){
            return "Reserve Squadron";
        }
        else if (i==6){
            return "Mercenary Squadron";
        }
        else{
            return "Naval Squadron";
        }
    }

    private boolean log_contains(String taskForce){
        File log = new File(System.getProperty("user.dir") +
                "\\src\\savedata\\callsign_log.txt");
        try{
            Scanner sc = new Scanner(log);
            while (sc.hasNextLine()){
               if (sc.nextLine().contentEquals(taskForce)) return true;
            }
        }catch(FileNotFoundException fnfex){
            JOptionPane.showMessageDialog(new JFrame(),"Warning: missing callsign_log.txt");
        }
        return false;
    }

    public void repaint(){
        super.repaint();
    }

}
