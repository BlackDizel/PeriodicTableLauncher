package ru.byters.periodictablelauncher.models;

public class AppDetail implements Comparable {
    public static final int NO_VALUE = -1;
    public static final int MAX_ITEM_TITLE_LENGTH = 2;
    private String label;
    private String name;
    private String title;
    private int color;

    public AppDetail() {
        color = NO_VALUE;
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
    public int compareTo(Object another) {
        if (!(another instanceof AppDetail))
            return 0;
        return getLabel().compareTo(((AppDetail) another).getLabel());
    }

    public boolean isColorSetted() {
        return color != NO_VALUE;
    }
}
