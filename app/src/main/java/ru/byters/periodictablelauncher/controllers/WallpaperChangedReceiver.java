package ru.byters.periodictablelauncher.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WallpaperChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ControllerWallpaper.getInstance().notifyListeners();
    }
}