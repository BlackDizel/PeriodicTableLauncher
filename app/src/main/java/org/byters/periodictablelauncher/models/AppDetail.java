package org.byters.periodictablelauncher.models;

import android.support.annotation.NonNull;

import org.byters.periodictablelauncher.controllers.ControllerPreference;
import org.byters.periodictablelauncher.controllers.Core;

import java.io.Serializable;

public class AppDetail implements Comparable, Serializable {
    public static final int NO_VALUE = -1;
    public static final int MAX_ITEM_TITLE_LENGTH = 2;
    private String label;
    private String name;
    private String title;
    private int color;
    private long date;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        if (!(another instanceof AppDetail))
            return 0;

        AppDetail other = (AppDetail) another;
        int sortOrientationFactor = ControllerPreference.getInstance().getSortOrientation() == ModelPreference.SORT_ORIENT_ASC ? 1 : -1;
        int compareResult = ControllerPreference.getInstance().getSortMethod() == ModelPreference.SORT_FULLTITLE
                ? getLabel().compareTo(other.getLabel())
                : ControllerPreference.getInstance().getSortMethod() == ModelPreference.SORT_LABEL
                ? getTitle().compareTo(other.getTitle())
                : compareDate(other.date);
        return sortOrientationFactor * compareResult;
    }

    private int compareDate(long dateOther) {
        return date == dateOther
                ? 0
                : date < dateOther
                ? -1
                : 1;
    }

    public boolean isColorSetted() {
        return color != NO_VALUE;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void resetColor() {
        color = NO_VALUE;
    }

    public void resetCustomLabel() {
        setTitle(Core.getInstance().getTitle(getLabel()));
    }
}
