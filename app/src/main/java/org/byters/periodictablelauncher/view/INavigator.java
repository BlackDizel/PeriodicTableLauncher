package org.byters.periodictablelauncher.view;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.byters.periodictablelauncher.view.ui.activities.ActivityBase;

public interface INavigator {
    void navigateApps();

    void set(ActivityBase activity, FragmentManager fragmentManager, int viewId, int viewIdBackground);

    void startActivity(String itemPackageName, String itemActivityName);

    void navigateItemInfo(View view1, View view2);

    void navigateBackground();

    void startActivitySetWallpaper();

    void openPlayMarketPage();

    void sendSupportRequest();

    void startActivityAppDetails(Uri uri);

    void startActivityAppRemove(Uri uri);

    void navigateItemEdit(View view1, View view2);
}
