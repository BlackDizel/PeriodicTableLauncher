package ru.byters.periodictablelauncher.models;

public class AppDetail implements Comparable {
    private String label;
    private String name;
    private String title;

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
}
