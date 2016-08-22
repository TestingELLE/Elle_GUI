package com.elle.elle_gui.entities;

/**
 *
 * @author corinne
 */
public class ColumnMetaData {
    private int index;
    private String dbName;
    private String label;
    private boolean isUnexpected;
    private boolean isMissing;
    private String classType;
    
    public ColumnMetaData(){
        index = -1;
        dbName = null;
        label = null;
        isUnexpected = false;
        isMissing = false;
        classType = null;
    }
    
      public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isUnexpected() {
        return isUnexpected;
    }

    public void setIsUnexpected(boolean isUnexpected) {
        this.isUnexpected = isUnexpected;
    }

    public boolean isMissing() {
        return isMissing;
    }

    public void setIsMissing(boolean isMissing) {
        this.isMissing = isMissing;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}

