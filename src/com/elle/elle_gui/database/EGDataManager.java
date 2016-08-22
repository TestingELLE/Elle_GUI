package com.elle.elle_gui.database;

import com.elle.elle_gui.entities.BackupDBTableRecord;
import com.elle.elle_gui.entities.RecordSet;
import com.elle.elle_gui.entities.TableObjects;
import java.util.ArrayList;

/**
 * Manages all communication with the data layer. 
 * @author corinne
 */
public class EGDataManager implements DataManager{
    private final static DataManager INSTANCE = new EGDataManager();
    private final TableObjectsDAO tableObjectDAO;
    private final BackupDBTableDAO backupDBTableDAO;
    private final AccessLevelDAO accessLevelDAO;
    private final RecordDAO recordDAO;
    
    private EGDataManager(){
        tableObjectDAO = new TableObjectsDAO();
        backupDBTableDAO = new BackupDBTableDAO();
        recordDAO = RecordDAO.getInstance(); 
        accessLevelDAO = new AccessLevelDAO();
    }
    
    public static DataManager getInstance(){
        return INSTANCE;
    }

    public RecordSet getRecordSet(String tableName, String accountName) {
       return recordDAO.getRecordSet(tableName, accountName);
    }
       
    public RecordSet getRecordSet(String tableName) {
       return recordDAO.getRecordSet(tableName);
    } 
    
    public RecordSet getRecordSetFromSQL(String sql) {
       return recordDAO.getRecordSetFromSQL(sql);
    } 

    public String getAccessLevel(String user) {
      return accessLevelDAO.getAccessLevel(user);
    }

    public ArrayList<TableObjects> getAllTableObjects() {
        return tableObjectDAO.getAll();
    }

    
    public boolean updateTableObjects(TableObjects account) {
       return tableObjectDAO.update(account);
    }

    public void createDBTableToStoreBackupsInfo() {
      backupDBTableDAO.createDBTableToStoreBackupsInfo();
    }

    public ArrayList<BackupDBTableRecord> getBackupTableRecords(){
         return backupDBTableDAO.getRecords();
    }

 
    public boolean deleteBackupTableRecord(BackupDBTableRecord record) {
        return backupDBTableDAO.deleteRecord(record);
    }

   
    public boolean deleteBackupTable(String backupTableName) {
        return backupDBTableDAO.deleteRecord(backupTableName);
    }

    
    public boolean addBackupTableRecord(BackupDBTableRecord record) {
     return backupDBTableDAO.addRecord(record);
    }

    public boolean updateBackupTableRecord(BackupDBTableRecord record, String oldTableName) {
       return backupDBTableDAO.updateRecord(record, oldTableName); 
    } 
}
