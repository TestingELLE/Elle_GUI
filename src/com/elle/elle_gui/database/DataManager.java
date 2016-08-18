package com.elle.elle_gui.database;

import com.elle.elle_gui.entities.BackupDBTableRecord;
import com.elle.elle_gui.entities.RecordSet;
import com.elle.elle_gui.entities.TableObjects;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface manages all communication with the data layer. 
 * Other layers talk to the data layer through this interface:contains the data layer API
 * 
 * @author corinne 7/28/16
 */
public interface DataManager {
    public RecordSet getRecordSet(String tableName, String accountName);
    public RecordSet getRecordSet(String tableName);
    public RecordSet getRecordSetFromSQL(String sql);
    public String getAccessLevel(String user);
    public ArrayList<TableObjects> getAllTableObjects();
    public boolean updateTableObjects(TableObjects account);
    public void createDBTableToStoreBackupsInfo();
    public ArrayList<BackupDBTableRecord> getBackupTableRecords();
    public boolean deleteBackupTableRecord(BackupDBTableRecord record);
    public boolean deleteBackupTable(String backupTableName);
    public boolean addBackupTableRecord(BackupDBTableRecord record);
    public boolean updateBackupTableRecord(BackupDBTableRecord record, String oldTableName) ;
}
