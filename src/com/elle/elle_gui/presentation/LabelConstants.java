package com.elle.elle_gui.presentation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *Noninstantiable utility class that stores the label constants
 * @author corinne
 */
public class LabelConstants {
    // view label constants
    public static final String VIEW_LABEL_TXT_ALL_FIELDS = "All Fields";
    public static final String VIEW_LABEL_TXT_DEFAULT_VIEW = "Default View";
    
// Suppress default constructor for noninstantiability
    private LabelConstants(){
        throw new AssertionError();
    }
}
