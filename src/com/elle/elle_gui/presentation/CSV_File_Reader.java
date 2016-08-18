package com.elle.elle_gui.presentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import com.elle.elle_gui.miscellaneous.LoggingAspect;

/**
 *Reads a CSV file into a tableModel and displays the table in a TableDisplayWindow
 * @author corinne
 */
public class CSV_File_Reader {
    
    public final static CSV_File_Reader INSTANCE = new CSV_File_Reader();
    private String fileName;
    
    private CSV_File_Reader() {
    }
    
    public static CSV_File_Reader getInstance(){
        return INSTANCE;
    } 
    
    //@author Wei 
    //loads and displays a table of the CSV file's data
    public void readCSV(File file){
         fileName = file.getName();
        
        String[] columnNames = new String[0];
        Object[][] data = new Object[0][0];

        String line = "";
        String splitSign = ",";
        int i = 0;
        try {
            //initialize the data array
            BufferedReader br
                    = new BufferedReader(
                            new FileReader(file));
            while (br.readLine() != null) {
                i++;
            }
            br.close();
            data = new Object[i - 1][];

            i = 0;
            br = new BufferedReader(new FileReader(file));
            line = br.readLine();
            columnNames = line.split(splitSign);
            String[] rowNumber = {"#"};
            System.arraycopy(rowNumber, 0, columnNames, 0, 1);
            line = br.readLine();
            while (line != null) {
                data[i] = new Object[line.split(splitSign).length + 1];
                data[i][0] = i + 1;
                for (int j = 1; j < data[i].length; j++) {
                    data[i][j] = line.split(splitSign)[j - 1];
                }
                i++;
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            LoggingAspect.afterThrown(ex);
        } catch (IOException ex) {
            LoggingAspect.afterThrown(ex);
        }

        //display the data in a table
        displayCSV(data, columnNames);
        
    }

    public void displayCSV(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        TableDisplayWindow tableDisplayWindow = new TableDisplayWindow(model,null);
       tableDisplayWindow.setTitle(fileName);
    }

    public void displayCSV(Vector data, Vector columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        TableDisplayWindow tableDisplayWindow = new TableDisplayWindow(model,null);
       tableDisplayWindow.setTitle(fileName);
        
    }
}
