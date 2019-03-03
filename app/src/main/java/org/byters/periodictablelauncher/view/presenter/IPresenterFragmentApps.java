package org.byters.periodictablelauncher.view.presenter;

import org.byters.periodictablelauncher.view.presenter.callback.IPresenterFragmentAppsListener;

public interface IPresenterFragmentApps {

    void onScroll(int scrollOffset, int scrollRange);

    void onCreate();

    void onClickSettings();

    void setListener(IPresenterFragmentAppsListener listener);

    void onCreateView();

    void onClickSettingsAppShadow();

    void onClickSettingsAppColor();

    void onClickSettingsGridSize();

    void onClickSettingsSortMethod();

    void onClickSettingsSortOrientation();

    void onClickSettingsBackground();

    void onClickSettingsApp();
}
