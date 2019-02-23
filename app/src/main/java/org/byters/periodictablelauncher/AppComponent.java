package org.byters.periodictablelauncher;

import org.byters.periodictablelauncher.controller.data.memorycache.CacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.CachePreferences;
import org.byters.periodictablelauncher.controller.data.repository.RepositoryApps;
import org.byters.periodictablelauncher.view.presenter.PresenterActivityMain;
import org.byters.periodictablelauncher.view.presenter.PresenterAdapterApps;
import org.byters.periodictablelauncher.view.presenter.PresenterFragmentApps;
import org.byters.periodictablelauncher.view.presenter.PresenterFragmentBackground;
import org.byters.periodictablelauncher.view.ui.activities.ActivityMain;
import org.byters.periodictablelauncher.view.ui.activities.ActivitySettings;
import org.byters.periodictablelauncher.view.ui.adapters.AdapterApps;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentApps;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentBackground;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentItemEdit;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentItemInfo;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentItemInfoBase;
import org.byters.periodictablelauncher.view.ui.utils.AppInstallReceiver;
import org.byters.periodictablelauncher.view.ui.utils.TransitionsHelper;
import org.byters.periodictablelauncher.view.ui.utils.WallpaperChangedReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(ActivityMain activityMain);

    void inject(CacheApps cacheApps);

    void inject(PresenterFragmentApps presenterFragmentApps);

    void inject(RepositoryApps repositoryApps);

    void inject(AdapterApps adapterApps);

    void inject(PresenterAdapterApps presenterAdapterApps);

    void inject(TransitionsHelper transitionsHelper);

    void inject(FragmentItemEdit fragmentItemEdit);

    void inject(FragmentItemInfo fragmentItemInfo);

    void inject(ApplicationLauncher applicationLauncher);

    void inject(CachePreferences cachePreferences);

    void inject(ActivitySettings activitySettings);

    void inject(WallpaperChangedReceiver wallpaperChangedReceiver);

    void inject(AppInstallReceiver appInstallReceiver);

    void inject(PresenterActivityMain presenterActivityMain);

    void inject(FragmentApps fragmentApps);

    void inject(FragmentBackground fragmentBackground);

    void inject(PresenterFragmentBackground presenterFragmentBackground);

    void inject(FragmentItemInfoBase fragmentItemInfoBase);
}
