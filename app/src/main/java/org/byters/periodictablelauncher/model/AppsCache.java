package org.byters.periodictablelauncher.model;

import java.io.Serializable;
import java.util.HashMap;

public class AppsCache implements Serializable {

    private HashMap<String, AppDetail> data;

    public HashMap<String, AppDetail> getData() {
        return data;
    }
}
