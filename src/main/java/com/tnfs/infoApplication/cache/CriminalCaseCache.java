package com.tnfs.infoApplication.cache;

import com.tnfs.infoApplication.model.CriminalCaseObj;

public class CriminalCaseCache {
    private CriminalCaseObj criminalCaseObj;
    private static CriminalCaseCache instance;
    private CriminalCaseCache() {}
    public static CriminalCaseCache getInstance() {
        if (instance == null) {
            synchronized (CriminalCaseCache.class) {
                if (instance == null) {
                    instance = new CriminalCaseCache();
                }
            }
        }
        return instance;
    }

    public CriminalCaseObj getCriminalCaseObj() {
        return criminalCaseObj;
    }

    public void setCriminalCaseObj(CriminalCaseObj obj) {
        criminalCaseObj = obj;
    }
}
