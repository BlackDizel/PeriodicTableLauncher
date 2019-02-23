package org.byters.periodictablelauncher.view.ui.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheState;

import javax.inject.Inject;


public class WallpaperChangedReceiver extends BroadcastReceiver {

    @Inject
    ICacheState cacheState;

    @Override
    public void onReceive(Context context, Intent intent) {
        ApplicationLauncher.getComponent().inject(this);
        cacheState.notifyWallpaperChange();
    }
}