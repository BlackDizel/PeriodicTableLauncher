package org.byters.periodictablelauncher.controllers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.byters.periodictablelauncher.models.AppDetail;
import org.byters.periodictablelauncher.models.ModelPreference;

class ControllerStorage {
    private static final String FILE_CACHE = "cache";
    private static final String TAG = "storage";
    private static final String CACHE_PREFERENCES = "preferences";

    static HashMap<String, AppDetail> getAppsCache(Context context) {
        return (HashMap<String, AppDetail>) readObjectFromFile(context, FILE_CACHE);
    }

    static void storeCache(Context context, ArrayList<AppDetail> data) {
        HashMap<String, AppDetail> cache = null;
        if (data != null && data.size() > 0)
            for (AppDetail item : data) {
                if (cache == null) cache = new HashMap<>();
                cache.put(item.getName(), item);
            }

        writeObjectToFile(context, cache, FILE_CACHE);
    }

    private synchronized static void writeObjectToFile(Context context, Object object, String filename) {
        if (context == null) return;
        ObjectOutputStream objectOut = null;
        if (object == null)
            context.deleteFile(filename);
        else {
            try {
                FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(object);
                fileOut.getFD().sync();

            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            } finally {
                if (objectOut != null) {
                    try {
                        objectOut.close();
                    } catch (IOException e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }
                }
            }
        }
    }

    @Nullable
    private synchronized static Object readObjectFromFile(Context context, String filename) {
        if (context == null) return null;

        ObjectInputStream objectIn = null;
        Object object = null;
        boolean needRemove = false;
        try {

            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            needRemove = true;
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }

        if (needRemove) RemoveFile(context, filename);

        return object;
    }

    private synchronized static void RemoveFile(Context context, String filename) {
        if (context == null) return;
        try {
            context.getApplicationContext().deleteFile(filename);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    static ModelPreference readPreferenceCache(Context context) {
        return (ModelPreference) readObjectFromFile(context, CACHE_PREFERENCES);
    }

    static void storePreferenceCache(Context context, ModelPreference model) {
        writeObjectToFile(context, model, CACHE_PREFERENCES);
    }
}
