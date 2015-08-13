/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ELLE_GUI.ellegui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Louis W.
 */
public class LogWindow extends javax.swing.JFrame {

    /**
     * Creates new form LogWindow3
     */
    public LogWindow() {
        initComponents();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        FILENAME = "log.txt";

        jLogText.setEditable(false);
        writeToTextFile("-------------------------" + dateFormat.format(date)
                + "-------------------------");
        readMessages();

        this.setLocationRelativeTo(null);
        this.setTitle("Log Window");
        this.setVisible(false);
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        jLogText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLogText.setColumns(20);
        jLogText.setRows(5);
        jScrollPane.setViewportView(jLogText);

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String fillSQLCommand(String str) {

        String output;
        if (str.startsWith("#")) {
            output = "SELECT *\nFROM trades\nWHERE Symbol = " + "'"
                    + str.substring(1) + "'";
        } else {
            String[] lst = str.split(" ");
            output = "SELECT *\nFROM trades\nWHERE Trade_Time BETWEEN " + "'"
                    + lst[0].substring(1) + "' AND '" + lst[1] + "'";
        }
        return output;
    }

    public void sendMessages(String str) {
//        JOptionPane.showMessageDialog(null, "sendmessages entered");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss a");
//        JOptionPane.showMessageDialog(null, "data ok");
        writeToTextFile(dateFormat.format(date) + ": " + str);
//        JOptionPane.showMessageDialog(null, "write ok");
        readCurrentMessages(dateFormat.format(date) + ": " + str);
//        JOptionPane.showMessageDialog(null, "read ok");
    }

    public void readCurrentMessages(String str) {
        jLogText.append(str);
        jLogText.append("\n");
    }

    public void readMessages() {
        String line = "";
        try {
            FileReader fileReader = new FileReader(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            while (line != null) {
                jLogText.append(line);
                jLogText.append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: Fail to read the log file");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unknown error");
        }
    }

    public void writeToTextFile(String str) {
        File file = new File(FILENAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(FILENAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            if (str.endsWith("\n")) {
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: Fail to write the log file");
        } catch (Exception ex) {
            JOptionPane.showConfirmDialog(this, "Unknow error");
        }
    }

    public void showLogWindow() {
        this.setVisible(true);
    }

    private final String FILENAME;
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jLogText;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane;
    // End of variables declaration//GEN-END:variables
}