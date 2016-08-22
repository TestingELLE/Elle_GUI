package com.elle.elle_gui.database;

/**
 * Factory class which returns an instance of a DataManager
 * This is necessary since the DataManager interface is implemented 
 * by a singleton class--EGDataManager
 * @author corinne
 * 8/16/2016
 */
public class DataManagerFactory {
   
    private static final DataManager DATA_MANAGER = EGDataManager.getInstance();
    
    public static DataManager getDataManager(){
        return DATA_MANAGER;
    }
}
