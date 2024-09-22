package com.huawei.cust;

import java.lang.System;
import java.io.File;
import android.util.Log;

public class HwCfgFilePolicy {
    private static final String TAG = "HwCfgFilePolicy";
    private static final String[] TYPE_NAMES = {"GLOBAL", "EMUI", "PC", "BASE", "CUST", "CLOUD_MCC", "CLOUD_DPLMN", "CLOUD_APN"};
    private static final String[] CFG_DIRS;

    static {
        String policy = System.getenv("CUST_POLICY_DIRS");
        if (policy == null || policy.isEmpty()) {
            Log.e(TAG, "CUST_POLICY_DIRS not set, using default.");
            policy = "/system/emui:/system/global:/system/etc:/oem:/data/cust:/cust_spec";
        }
        CFG_DIRS = policy.split(":");
    }

    public static File getCfgFile(String path, int type) {
        if (type < 0 || type >= TYPE_NAMES.length) {
            Log.e(TAG, "Invalid file type requested: " + type);
            return null;
        }

        String typeName = TYPE_NAMES[type];
        Log.v(TAG, "Requested config file: " + path + " for type: " + typeName);

        for (String dir : CFG_DIRS) {
            File file = new File(dir, path);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}
