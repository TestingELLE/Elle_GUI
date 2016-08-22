package com.elle.elle_gui.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author corinne 7/27/2016
 * Creates an immutable class that stores the data from one row in the database
 */
public class Record {
    private final List<Object> recordData;
    
    //constructor
    public Record(List<Object> recordData){
        this.recordData = Collections.unmodifiableList(recordData);
    }
    
    public Object getRecordValue(int i){
       return recordData.get(i);
    }
}
