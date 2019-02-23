package org.byters.periodictablelauncher.view.presenter;

import android.view.View;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICacheAppsListener;
import org.byters.periodictablelauncher.view.INavigator;
import org.byters.periodictablelauncher.view.presenter.callback.IPresenterAdapterAppsListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterApps implements IPresenterAdapterApps {

    private final ListenerCacheApps listenerCacheApps;

    @Inject
    ICachePreferences cachePreferences;

    @Inject
    ICacheApps cacheApps;

    @Inject
    INavigator navigator;

    private WeakReference<IPresenterAdapterAppsListener> refListener;

    public PresenterAdapterApps() {
        ApplicationLauncher.getComponent().inject(this);
        cacheApps.addListener(listenerCacheApps = new ListenerCacheApps());
    }

    @Override
    public int getSize() {
        return cacheApps.getItemsNum();
    }

    @Override
    public int getColorIconDefault() {
        return cachePreferences.getColorIconDefault();
    }

    @Override
    public String getItemFullname(int position) {
        return cacheApps.getItemFullname(position);
    }

    @Override
    public String getItemTitle(int position) {
        return cacheApps.getItemTitle(position);
    }

    @Override
    public String getItemLabel(int position) {
        return cacheApps.getItemLabel(position);
    }

    @Override
    public boolean isItemColorSetted(int position) {
        return cacheApps.isItemColorSetted(position);
    }

    @Override
    public int getItemColor(int position) {
        return cacheApps.getItemColor(position);
    }

    @Override
    public boolean isShadowVisible() {
        return cachePreferences.isShadowEnabled();
    }

    @Override
    public void onClick(int adapterPosition) {
        navigator.startActivity(cacheApps.getItemPackageName(adapterPosition), cacheApps.getItemActivityName(adapterPosition));
    }

    @Override
    public void onClickLong(View view1, View view2, int position) {
        cacheApps.setSelectedItem(position);
        navigator.navigateItemInfo(view1,view2);
    }

    @Override
    public void setListener(IPresenterAdapterAppsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private class ListenerCacheApps implements ICacheAppsListener {
        @Override
        public void onUpdate() {
            if (refListener == null || refListener.get() == null)
                return;
            refListener.get().onUpdate();
        }
    }
}
