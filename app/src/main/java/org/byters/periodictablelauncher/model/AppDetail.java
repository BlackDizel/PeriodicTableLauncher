package org.byters.periodictablelauncher.model;

import android.content.Context;
import android.text.TextUtils;

import org.byters.periodictablelauncher.R;

import java.io.Serializable;

public class AppDetail implements Serializable {

    public static final int NO_VALUE = -1;
    public static final int MAX_ITEM_TITLE_LENGTH = 2;

    private String label;
    private String name;
    private String title;
    private int color;
    private long date;
    private String packageName;
    private String activityName;

    public AppDetail() {
        resetColor();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFullName() {
        return name;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    public boolean isColorSetted() {
        return color != NO_VALUE;
    }

    public void resetColor() {
        color = NO_VALUE;
    }

    public void resetCustomLabel(Context context) {
        setTitle(getTitle(context, getLabel()));
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActviityName() {
        return activityName;
    }

    private String getTitle(Context context, String label) {
        if (TextUtils.isEmpty(label)) return "?";

        String result;

        String buffer = label.replaceAll("[0123456789]", "");

        if (TextUtils.isEmpty(buffer) || buffer.length() < 1) return "?";

        result = String.valueOf(Character.toUpperCase(buffer.charAt(0)));

        buffer = buffer
                .substring(1)
                .toLowerCase()
                .replaceAll(" ", "")
                .replaceAll(context.getString(R.string.vowels_eng), "")
                .replaceAll(context.getString(R.string.vowels_rus), "");

        if (TextUtils.isEmpty(buffer) || buffer.length() < 1) return result;

        return result + buffer.substring(0, 1);
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
