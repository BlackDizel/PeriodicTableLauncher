package org.byters.periodictablelauncher;

import org.byters.periodictablelauncher.view.ui.fragments.FragmentApps;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class UnitTest {

    private static final int GRID_ORIENTATION_HORIZONTAL = 0;
    private static final int GRID_ORIENTATION_VERTICAL = 1;

    @Test
    public void gridSpanNumsIsCorrect() throws Exception {
        assertEquals(7, FragmentApps.getColumns(GRID_ORIENTATION_HORIZONTAL, 640, 480, 120, 64));
        assertEquals(5, FragmentApps.getColumns(GRID_ORIENTATION_VERTICAL, 640, 480, 120, 64));
    }
}