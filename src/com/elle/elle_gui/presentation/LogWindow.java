
package com.elle.elle_gui.presentation;

import com.elle.elle_gui.logic.LogMessage;
import com.elle.elle_gui.miscellaneous.LoggingAspect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * LogWindow
 * @author Carlos Igreja
 * @since June 10, 2015
 * @version 0.6.3
 */
public class LogWindow extends JFrame{
    
    // variables
    public static final String HYPHENS = "-------------------------"; // delimiter
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    public static String fileName;
    private ArrayList<LogMessage> logMessages = new ArrayList<>();
    
    private JScrollPane scrollPane;
    private static JTextArea logText;
    private JPanel panelLogWindowButtons;
    private JButton btnClearAllButToday;
    private JButton btnDeleteAllButToday;
    private JButton showAll;
    private static Component parent;
    //private final JCheckBox jCheckBoxOrder;  // check box for order of dates
    //private final JLabel jLabelOrder; // label for checkbox order
    
    // manages a listener and dispatches a PropertyChangeEvent for windowOpen
    PropertyChangeSupport pcs; 
    
    private boolean windowOpen;

    // constructor
    public LogWindow() {

        this.setTitle("Log Window");
        ImageIcon imag = new ImageIcon(
                        "Images/elle gui image.jpg");
        this.setIconImage(imag.getImage());

        logText = new JTextArea(5, 30);
        logText.setEditable(false);
        logText.setLineWrap(true); 
        ShortCutSetting.copyAndPasteShortCut(logText.getInputMap());

        //was testing the JList
        // I need a no horizontal scroll and a wrap
        // going to come back to this
//                JList list = new JList();
//                list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//                list.add(logText);
//                list.setVisibleRowCount(0);
        scrollPane = new JScrollPane(logText);
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // change layout of frame
        this.setLayout(new GridBagLayout());

        // set constraints for the scroll panel
        GridBagConstraints scrollPanelConstraints = new GridBagConstraints();
        scrollPanelConstraints.fill = GridBagConstraints.BOTH;
        scrollPanelConstraints.weightx = 1; // takes up whole x axis
        scrollPanelConstraints.weighty = 1; // takes up most y axis with room for buttons
        scrollPanelConstraints.gridx = 0; // first col cell
        scrollPanelConstraints.gridy = 0; // first row cell

        // add scroll panel to frame
        this.add(scrollPane, scrollPanelConstraints);

        // create a panel for buttons
        panelLogWindowButtons = new JPanel();

        // create buttons 
        btnClearAllButToday = new JButton("Clear All But Today");
        btnClearAllButToday.addActionListener((ActionEvent evt) -> {
            btnClearAllButTodayActionPerformed(evt);
        });

        btnDeleteAllButToday = new JButton("Delete All But Today");
        btnDeleteAllButToday.addActionListener((ActionEvent evt) -> {
            btnDeleteAllButTodayActionPerformed(evt);
        });

        /********* THIS IS THE CHECKBOX ORDER FEATURE *****************/
//                jCheckBoxOrder = new JCheckBox();
//                jCheckBoxOrder.addActionListener(new java.awt.event.ActionListener() {
//                    public void actionPerformed(java.awt.event.ActionEvent evt) {
//                        jCheckBoxOrderActionPerformed(evt);
//                    }
//                });
////                jLabelOrder = new JLabel("Order");

        showAll = new JButton("Show All");
        showAll.addActionListener((ActionEvent evt) -> {
            showAllActionPerformed(evt);
        });


        // add buttons to panel
//                panelLogWindowButtons.add(btnClearAll);
        panelLogWindowButtons.add(btnClearAllButToday);
        panelLogWindowButtons.add(btnDeleteAllButToday);
        //jPanelLogWindowButtons.add(jCheckBoxOrder);
        //jPanelLogWindowButtons.add(jLabelOrder);
        panelLogWindowButtons.add(showAll);

        // set constraints for the buttons panel
        GridBagConstraints buttonsPanelConstraints = new GridBagConstraints();
        buttonsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanelConstraints.weightx = 1; // takes up whole x axis
        buttonsPanelConstraints.weighty = 0; // takes up enough y axis just for buttons
        buttonsPanelConstraints.gridx = 0; // first col cell
        buttonsPanelConstraints.gridy = 1; // second row cell

        //intialize a propertyChangeSupport to manage a listener and 
        //dispatch a PropertyChangeEvent for windowOpen
        pcs= new PropertyChangeSupport(windowOpen);
        
        // add panel to the frame
        this.add(panelLogWindowButtons,buttonsPanelConstraints);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(860, 540));
        
        //sets listeners for when this window is set visible and closed
        setListener();
        windowOpen = false;

        this.pack();
        this.setVisible(false);
        
    }
    
    /**
     * fillSQLCommand
     * This is legacy code for ELLE_GUI
     * @param str
     * @return 
     */
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

    /**
     * readCurrentMessages
     * @param str 
     */
    public static void readCurrentMessages(String str) {
        logText.append("\n");
        logText.append(str);
    }

    /**
     * readMessages
     */
    public void readMessages() {
        String line = "";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            while (line != null) {
                logText.append("\n");
                logText.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException ex) {
            LoggingAspect.afterThrown(ex);
        } catch (Exception ex) {
            LoggingAspect.afterThrown(ex);
        }
    }

    /**
     * addMessage
     * @param str 
     */
    public static void addMessage(String str) {
        
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                    file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if (str.startsWith(HYPHENS))
                    bufferedWriter.newLine();
            bufferedWriter.write(str );
            bufferedWriter.newLine();
            bufferedWriter.close();
            readCurrentMessages(str);
        } catch (IOException ex) {
            LoggingAspect.afterThrown(ex);
        } catch (Exception ex) {
            LoggingAspect.afterThrown(ex);
        }
    }
    
    /**
     * addMessageWithDate
     * @param str 
     */
    public static void addMessageWithDate(String str) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss a");
            addMessage(dateFormat.format(date) + ": " + str);
    }

    /**
     * Clear all but today button action performed: 
     * When the Clear all but today button is clicked, 
     * all the messages are removed from the scroll panel text box,
     * except todays.
     */
    private void btnClearAllButTodayActionPerformed(ActionEvent evt) {

        // store log messages in an array of log messages
        storeLogMessages(); // get most current messages to array

            /****************** CHECK BOX ORDER FEATURE ********************/
//            // get the order of messages
//            if(jCheckBoxOrder.isSelected()){
//                // sorts by most recent date first
//                Collections.sort(logMessages, new LogMessage.SortByMostRecentDateFirst());
//            }else if(!jCheckBoxOrder.isSelected()){
//                // sorts by most recent date last
//                Collections.sort(logMessages, new LogMessage.SortByMostRecentDateLast());
//            }

        // sorts by most recent date last
        Collections.sort(logMessages, new LogMessage.SortByMostRecentDateLast());

        // compare date with todays date and print to screen
        Date date = new Date(); // get todays date
        logText.setText(""); // clear text box
        for(LogMessage logMessage : logMessages){

            // if date is today then print to screen
            if(logMessage.getDate().getYear() == date.getYear()
                    && logMessage.getDate().getMonth()== date.getMonth()
                    && logMessage.getDate().getDate()== date.getDate()){
                logText.append(HYPHENS + dateFormat.format(logMessage.getDate()) + HYPHENS);
                logText.append(logMessage.getMessage());
            }
        }  
    }

    /**
     * Clear all but today button action performed: 
     * When the Clear all but today button is clicked, 
     * all the messages are removed from the scroll panel text box,
     * except todays.
     */
    private void btnDeleteAllButTodayActionPerformed(ActionEvent evt) {

        // store log messages in an array of log messages
        storeLogMessages(); // get most current messages to array

       /****************** CHECK BOX ORDER FEATURE ********************/
//            // get the order of messages
//            if(jCheckBoxOrder.isSelected()){
//                // sorts by most recent date first
//                Collections.sort(logMessages, new LogMessage.SortByMostRecentDateFirst());
//            }else if(!jCheckBoxOrder.isSelected()){
//                // sorts by most recent date last
//                Collections.sort(logMessages, new LogMessage.SortByMostRecentDateLast());
//            }

        // clear the text file
        clearTextFile();

        // sorts by most recent date last
        Collections.sort(logMessages, new LogMessage.SortByMostRecentDateLast());

        // compare date with todays date and print to screen
        Date date = new Date(); // get todays date
        logText.setText(""); // clear text box
        for(LogMessage logMessage : logMessages){

            // if date is today then print to screen
            if(logMessage.getDate().getYear() == date.getYear()
                    && logMessage.getDate().getMonth()== date.getMonth()
                    && logMessage.getDate().getDate()== date.getDate()){
                addMessage(HYPHENS + dateFormat.format(logMessage.getDate()) + HYPHENS);
                addMessage(logMessage.getMessage());
            }
        }  

        // reload the messages from the file
        readMessages();
        storeLogMessages();
    }


    /**
     * Order check box: When the order check box is checked, 
     * all the messages are reversed in order in the scroll pane text box.
     */
//        private void jCheckBoxOrderActionPerformed(ActionEvent evt) {
//            
//            // store log messages in an array of log messages
//            storeLogMessages(); // get most current messages to array
//            
//            // sort log messages
//            if(jCheckBoxOrder.isSelected()){
//                // sorts by most recent date first
//                Collections.sort(logMessages, new LogMessage.SortByMostRecentDateFirst());
//            }else if(!jCheckBoxOrder.isSelected()){
//                // sorts by least recent date first
//                Collections.sort(logMessages, new LogMessage.SortByLeastRecentDateFirst());
//            }
//            
//            logText.setText("");// clear text box
//            // print log messages to log window text box
//            for (LogMessage logMessage : logMessages) {
//                logText.append("-------------------------" + dateFormat.format(logMessage.getDate()) + "-------------------------");
//                logText.append(logMessage.getMessage());
//            }
//        }

    /**
     * Show all message with most recent appearing at the bottom
     * @param evt 
     */
    private void showAllActionPerformed(ActionEvent evt){

        // store log messages in an array of log messages
        storeLogMessages(); // get most current messages to array

        // sorts by least recent date first
        Collections.sort(logMessages, new LogMessage.SortByMostRecentDateLast());

        logText.setText(""); // clear text box

        // print log messages to log window text box
        for (LogMessage logMessage : logMessages) {
            logText.append("\n");
            logText.append(HYPHENS + dateFormat.format(logMessage.getDate()) + HYPHENS);
            logText.append(logMessage.getMessage());
        }
    }

    /**
     * storeLogMessages: Method
     * Stores each LogMessage object in an array. 
     * This is to be able to easily retrieve specific data according to specific times or dates.
     */
    private void storeLogMessages(){

        File file = new File(fileName);
        logMessages.clear(); // clear array of any elements
        Date date = new Date();
        String message = "";

        if (file.exists())  // prevent the FileNotFoundException
        {
            try
            {

                BufferedReader in = 
                     new BufferedReader(
                     new FileReader(fileName));

                // read all log messages stored in the log file
                // and store them into the array list
                String line = in.readLine(); 
                while(line != null)
                {
                    // first get date between hyphens
                    if(line.startsWith(HYPHENS)){
                        String[] columns = line.split(HYPHENS);
                        date = dateFormat.parse(columns[1]);
                        message = ""; // reset message string

                        line = in.readLine();
                    }
                    
                    // get message until next date
                    else{
                        message = message + "\n" + line;
                        line = in.readLine();
                        
                        // if next line is null or next date 
                        // then store this logmessage
                        if(line == null || line.startsWith(HYPHENS)){
                            logMessages.add(new LogMessage(date, message));  
                        }
                    }                  
                }

                in.close(); // close the input stream
            }
            catch(IOException e){
                LoggingAspect.afterThrown(e);
            } 
            catch (ParseException ex) {
                LoggingAspect.afterThrown(ex);
            }
        }  
    }

    /**
     * clearTextFile
     * clear the text file
     */
    public void clearTextFile(){

        // clear the log.text file
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.close();
        } 
        catch (FileNotFoundException ex) {
            LoggingAspect.afterThrown(ex);
        }
    }

       public void setUserLogFileDir(String userName) {
        
        String OS = System.getProperty("os.name").toLowerCase();
        
        if (OS.startsWith("mac")) {
            String ELLE_GUI = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/ELLE_GUI/";
            File dir = new File(ELLE_GUI);
            dir.mkdir();
            fileName = ELLE_GUI + "ELLE_GUI_" + userName + "_log.txt";
            
        } else {

            String ELLE_GUI =  "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\ELLE_GUI\\";
            File dir = new File(ELLE_GUI);
            dir.mkdir();
            fileName = ELLE_GUI + "ELLE_GUI_" + userName + "_log.txt";
        }
    }
    
    
    
    public static void setParent(Component parent) {
        LogWindow.parent = parent;
    }
    
    //registers listeners for changes to windowOpen
    //Elle_GUI frame calls this method
    public void addLogWindowClosedListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    private void setListener(){
        
        this.addComponentListener(new ComponentAdapter() {
            // fire propertychange event when this window is set invisible 
            @Override
            public void componentHidden(ComponentEvent e) 
            {
                boolean oldValue = windowOpen;
                windowOpen = false;
               pcs.firePropertyChange("LogWindow closed",
                                 oldValue  , windowOpen);
            }
            // fire propertychange event when this window is set visible 
            @Override
            public void componentShown(ComponentEvent e) {
                boolean oldValue = windowOpen;
                windowOpen = true;
                pcs.firePropertyChange("LogWindow opened",
                                 oldValue  , windowOpen);
            }
        });
        // fire propertychange event when this window is closed 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean oldValue = windowOpen;
                windowOpen = false;
                pcs.firePropertyChange("LogWindow closed",
                           oldValue, windowOpen);
            }
        });
   }
    
}



