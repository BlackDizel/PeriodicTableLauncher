package org.byters.periodictablelauncher.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ControllerItems.getInstance().updateData();
    }
}
