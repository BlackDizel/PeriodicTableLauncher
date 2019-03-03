package org.byters.periodictablelauncher.view.presenter;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheState;
import org.byters.periodictablelauncher.controller.data.repository.IRepositoryApps;
import org.byters.periodictablelauncher.model.ModelPreference;
import org.byters.periodictablelauncher.view.INavigator;
import org.byters.periodictablelauncher.view.presenter.callback.IPresenterFragmentAppsListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterFragmentApps implements IPresenterFragmentApps {

    @Inject
    ICacheApps cacheApps;

    @Inject
    ICacheState cacheState;

    @Inject
    IRepositoryApps repositoryApps;

    @Inject
    INavigator navigator;

    @Inject
    ICachePreferences cachePreferences;

    private WeakReference<IPresenterFragmentAppsListener> refListener;

    public PresenterFragmentApps() {
        ApplicationLauncher.getComponent().inject(this);
    }

    @Override
    public void onScroll(int scrollOffset, int scrollRange) {
        cacheState.setScroll(scrollOffset / (float) scrollRange);
    }

    @Override
    public void onCreate() {
        repositoryApps.request();
    }

    @Override
    public void onClickSettings() {
        boolean isOpenedNewState = !cacheState.isSettingsOpened();
        cacheState.setSettingsOpened(isOpenedNewState);
        notifyListenerSettingsOpened(isOpenedNewState);
    }

    @Override
    public void setListener(IPresenterFragmentAppsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        notifyListenerSettingsOpened(cacheState.isSettingsOpened());
        notifySetSortOrientation(cachePreferences.getSortOrientation());
    }

    @Override
    public void onClickSettingsAppShadow() {
        //todo implememt
    }

    @Override
    public void onClickSettingsAppColor() {
        //todo implememt
    }

    @Override
    public void onClickSettingsGridSize() {
        //todo implememt
    }

    @Override
    public void onClickSettingsSortMethod() {
        //todo implememt
    }

    @Override
    public void onClickSettingsSortOrientation() {
        int sortOrientationNew = cachePreferences.getSortOrientation() == ModelPreference.SORT_ORIENT_ASC
                ? ModelPreference.SORT_ORIENT_DESC
                : ModelPreference.SORT_ORIENT_ASC;

        cachePreferences.setSortOrientation(sortOrientationNew);
        notifySetSortOrientation(sortOrientationNew);

    }

    private void notifySetSortOrientation(int sortOrientationNew) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setSortOrientation(sortOrientationNew == ModelPreference.SORT_ORIENT_ASC ? R.drawable.ic_sort_ascend_white_24dp : R.drawable.ic_sort_descend_white_24dp);
    }

    @Override
    public void onClickSettingsBackground() {
        navigator.startActivitySetWallpaper();
    }

    @Override
    public void onClickSettingsApp() {
        //todo implement
    }

    private void notifyListenerSettingsOpened(boolean isOpenedNewState) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setStateSettingsOpened(isOpenedNewState);
    }
}
