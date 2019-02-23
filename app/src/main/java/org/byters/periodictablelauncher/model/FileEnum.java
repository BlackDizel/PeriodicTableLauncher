package org.byters.periodictablelauncher.model;

public enum FileEnum {
    CACHE_APPS("cache"), CACHE_PREFERENCES("preferences");

    private final String filename;

    public String getFilename() {
        return filename;
    }

    FileEnum(String filename) {
        this.filename = filename;
    }
}
