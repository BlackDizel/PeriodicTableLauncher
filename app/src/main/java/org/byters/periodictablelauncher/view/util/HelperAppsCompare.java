package org.byters.periodictablelauncher.view.util;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.model.AppDetail;
import org.byters.periodictablelauncher.model.ModelPreference;

import java.util.Comparator;

import javax.inject.Inject;

public class HelperAppsCompare {

    private final CustomComparator comparator;
    @Inject
    ICachePreferences cachePreferences;

    public HelperAppsCompare() {
        ApplicationLauncher.getComponent().inject(this);
        comparator = new CustomComparator();
    }


    public CustomComparator getComparator() {
        return comparator;
    }

    private class CustomComparator implements Comparator<AppDetail> {
        @Override
        public int compare(AppDetail o1, AppDetail other) {
            int sortOrientationFactor = cachePreferences.getSortOrientation() == ModelPreference.SORT_ORIENT_ASC ? 1 : -1;
            int compareResult = cachePreferences.getSortMethod() == ModelPreference.SORT_FULLTITLE
                    ? o1.getLabel().compareTo(other.getLabel())
                    : cachePreferences.getSortMethod() == ModelPreference.SORT_LABEL
                    ? o1.getTitle().compareTo(other.getTitle())
                    : compareDate(o1.getDate(), other.getDate());
            return sortOrientationFactor * compareResult;
        }

        private int compareDate(long date, long dateOther) {
            return date == dateOther
                    ? 0
                    : date < dateOther
                    ? -1
                    : 1;
        }

    }
}
