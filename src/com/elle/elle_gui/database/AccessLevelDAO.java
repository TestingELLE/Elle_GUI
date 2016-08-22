
package com.elle.elle_gui.database;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.AccessLevel;
import com.elle.elle_gui.miscellaneous.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AccessLevelDAO
 * @author Carlos Igreja
 * @since  May 12, 2016
 */
    public final class AccessLevelDAO {

    // database table information
    public static final String DB_TABLE_NAME = "accessLevels";
    public static final String COL_USER = "user";
    public static final String COL_ACCESS_LEVEL = "accessLevel";

    public static String getAccessLevel(String user) {
        String sql = "SELECT * FROM " + DB_TABLE_NAME +
                      " WHERE " + COL_USER + " = '" + user +"';";

        ResultSet rs = null;
        AccessLevel accessLevel = new AccessLevel();
        
        try {

            DBConnection.close();
            DBConnection.open();
            rs = DBConnection.getStatement().executeQuery(sql);
            
            while(rs.next()){
                accessLevel.setUser(rs.getString(COL_USER));
                accessLevel.setAccessLevel(rs.getString(COL_ACCESS_LEVEL));
            }
            
            LoggingAspect.afterReturn("Loaded access level from " + DB_TABLE_NAME + " for " + accessLevel.getUser());
        } 
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        
        return accessLevel.getAccessLevel();
    }
}
