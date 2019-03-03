package org.byters.periodictablelauncher.controller.data.memorycache;

import android.net.Uri;

import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICacheAppsListener;
import org.byters.periodictablelauncher.model.AppDetail;

import java.util.ArrayList;

public interface ICacheApps {
    int getItemsNum();

    String getItemFullname(int position);

    String getItemTitle(int position);

    String getItemLabel(int position);

    boolean isItemColorSetted(int position);

    int getItemColor(int position);

    String getItemPackageName(int adapterPosition);

    String getItemActivityName(int adapterPosition);

    void setSelectedItem(int position);

    void storeData();

    String getSelectedItemTitle();

    void setSelectedItemTitle(String s);

    void addListener(ICacheAppsListener listener);

    void removeListener(ICacheAppsListener listener);

    void removeCache();

    void resetAppIconsCustomColor();

    void resetAppIconsCustomLabels();

    int getSelectedItemColor();

    void setSelectedItemColor(int color);

    String getSelectedItemLabel();

    Uri getSelectedItemUri();

    String getSelectedItemFullname();

    boolean isDataExist();

    ArrayList<AppDetail> getData();

    void setData(ArrayList<AppDetail> result);

    void reloadDataCurrent();
}
