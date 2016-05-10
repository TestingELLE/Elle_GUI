package com.elle.elle_gui;

import static com.elle.elle_gui.presentation.ELLE_GUI_Frame.creationDate;
import static com.elle.elle_gui.presentation.ELLE_GUI_Frame.version;
import com.elle.elle_gui.presentation.ELLE_GUI_Frame;
import com.elle.elle_gui.presentation.LoginWindow;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author cigreja
 */
public class ELLE_GUI {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }
        UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30, 30));

        // get the creation date and version from the manifest
        Manifest mf = new Manifest();
        Attributes atts;
        String s = "MANIFEST.MF";
        InputStream inputStream = ELLE_GUI.class.getResourceAsStream(s);
        try {
            mf.read(inputStream);
            atts = mf.getMainAttributes();
            creationDate = atts.getValue("creation-date");
            version = atts.getValue("version");
            JOptionPane.showMessageDialog(null, "All good = ");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }
}
