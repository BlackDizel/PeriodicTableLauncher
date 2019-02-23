package org.byters.periodictablelauncher.view.ui.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.repository.IRepositoryApps;

import javax.inject.Inject;

public class AppInstallReceiver extends BroadcastReceiver {

    @Inject
    IRepositoryApps repositoryApps;

    @Override
    public void onReceive(Context context, Intent intent) {
        ApplicationLauncher.getComponent().inject(this);
        repositoryApps.request();
    }
}
