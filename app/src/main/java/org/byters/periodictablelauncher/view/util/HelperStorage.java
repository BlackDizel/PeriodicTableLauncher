package org.byters.periodictablelauncher.view.util;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.byters.periodictablelauncher.model.FileEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class HelperStorage implements IHelperStorage {

    private final Gson gson;

    @Inject
    WeakReference<Application> refContext;

    public HelperStorage() {
        gson = new Gson();
    }

    @Override
    public synchronized <T> T readFile(FileEnum fileEnum, Class<T> tClass) {
        if (refContext == null || refContext.get() == null) return null;
        File file = new File(refContext.get().getFilesDir(), fileEnum.getFilename());

        if (!file.exists()) return null;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }

        T result;
        try {
            result = gson.fromJson(bufferedReader, tClass);
        } catch (JsonSyntaxException e) {
            file.delete();
            return null;
        }
        return result;
    }

    @Override
    public synchronized void writeData(Object data, FileEnum fileEnum) {
        if (refContext == null || refContext.get() == null) return;

        File folder = refContext.get().getFilesDir();

        if (!folder.exists() && !folder.mkdirs()) return;
        File file = new File(folder, fileEnum.getFilename());

        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(gson.toJson(data).getBytes());
        } catch (IOException e) {
            return;
        }
    }
}
