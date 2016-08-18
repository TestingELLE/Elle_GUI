/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elle.elle_gui.database;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.TableObjects;
import com.elle.elle_gui.miscellaneous.LoggingAspect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ren
 */
public class TableObjectsDAO {
    
    // database table information
    public static final String DB_TABLE_NAME = "tableObjects";
    public static final String DB_TO_ID = "id";
    public static final String DB_TO_NAME = "name";
    public static final String DB_TO_TYPE = "type";
    public static final String DB_TO_USAGE = "usage";
    public static final String DB_TO_DES = "description";
    public static final String DB_TO_AUTHOR = "author";

    public ArrayList<TableObjects> getAll(){
        ArrayList<TableObjects> tablobjects = new ArrayList<>();
        ResultSet rs = null;
        String sql = "";
        
        
        sql = "SELECT * FROM " + DB_TABLE_NAME ;
        
        try {

            DBConnection.close();
            DBConnection.open();
            rs = DBConnection.getStatement().executeQuery(sql);
            while(rs.next()){
                TableObjects acct = new TableObjects();
                acct.setId(rs.getInt(DB_TO_ID));
                acct.setName(rs.getString(DB_TO_NAME));
                acct.setType(rs.getString(DB_TO_TYPE));
                acct.setUsage(rs.getString(DB_TO_USAGE));
                acct.setDescription(rs.getString(DB_TO_DES));
                acct.setAuthor(rs.getString(DB_TO_AUTHOR));
                
                tablobjects.add(acct);
            }
        } 
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }

        return tablobjects;
    }
    
    /*
    public boolean insert(TableObjects account) {
        
        boolean successful = false;
        DBConnection.close();
        if(DBConnection.open()){
            
            int id = account.getId();
            if (id < 0) {
                String sql = "SELECT MAX(" + DB_TO_ID + ") "
                       + "FROM " + DB_TABLE_NAME + ";";

                ResultSet result = null;
                int maxId = -1;

                try {
                    Connection con = DBConnection.getConnection();
                    PreparedStatement statement = con.prepareStatement(sql);
                    result = statement.executeQuery();
                    if(result.next()){
                        maxId = result.getInt(1);
                    }
                    id = maxId + 1;
                    
                }
                catch (SQLException ex) {
                    LoggingAspect.afterThrown(ex);
                    return false;
                }
                
                
            }
            String name = account.getName();
            String type = account.getType();
            
                
            try {
                
                
            String sql = "INSERT INTO " + DB_TABLE_NAME + " (" + COL_PK_ID + ", " 
                    + COL_USER + ", " +  COL_ACCESS_LEVEL + ") " 
                    + "VALUES (" + id + ", '" + user + "', '" +  accessLevel +  "') ";
            
                Connection con = DBConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.execute();
                LoggingAspect.afterReturn("Upload Successful!");
                successful = true;
                //update the id after successful uploading
                if (account.getId() < 0)
                    account.setId(id);
                     
            }
            catch (SQLException ex) {
                LoggingAspect.afterThrown(ex);
                successful = false;
            }
        }
        DBConnection.close();
        return successful;
    }
    */
    public boolean update(TableObjects account) {
        
        boolean successful = false;
        DBConnection.close();
        if(DBConnection.open()){
            
            // set issue values
            int id = account.getId();
            String name = account.getName();
            String type = account.getType();
            String usage = account.getUsage();
            String description = account.getDescription();
            String author = account.getAuthor();
            
            try {
                String sql = "UPDATE " + DB_TABLE_NAME + " SET " 
                    + DB_TO_NAME + " = '" + name + "', "
                    + DB_TO_TYPE + " = '" + type + "', "
                    + "`" + DB_TO_USAGE + "`" + " = '" + usage + "', "
                    + DB_TO_DES + " = '" + description + "', "
                    + DB_TO_AUTHOR + " = '" + author +  "' "
                    + "WHERE " + DB_TO_ID + " = " + id + ";";
                
                System.out.println("update : " + sql );
                Connection con = DBConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.execute();
                LoggingAspect.afterReturn("Upload Successful!");
                successful = true;
            }
            catch (SQLException ex) {
                LoggingAspect.afterThrown(ex);
                successful = false;
            }
        }
        DBConnection.close();
        return successful;
    }
    
    
}
