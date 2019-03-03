package org.byters.periodictablelauncher;

import android.app.Application;

import org.byters.periodictablelauncher.controller.data.memorycache.CacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.CachePreferences;
import org.byters.periodictablelauncher.controller.data.memorycache.CacheState;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheState;
import org.byters.periodictablelauncher.controller.data.repository.IRepositoryApps;
import org.byters.periodictablelauncher.controller.data.repository.RepositoryApps;
import org.byters.periodictablelauncher.view.INavigator;
import org.byters.periodictablelauncher.view.presenter.IPresenterActivityMain;
import org.byters.periodictablelauncher.view.presenter.IPresenterAdapterApps;
import org.byters.periodictablelauncher.view.presenter.IPresenterFragmentApps;
import org.byters.periodictablelauncher.view.presenter.IPresenterFragmentBackground;
import org.byters.periodictablelauncher.view.presenter.PresenterActivityMain;
import org.byters.periodictablelauncher.view.presenter.PresenterAdapterApps;
import org.byters.periodictablelauncher.view.presenter.PresenterFragmentApps;
import org.byters.periodictablelauncher.view.presenter.PresenterFragmentBackground;
import org.byters.periodictablelauncher.view.ui.Navigator;
import org.byters.periodictablelauncher.view.ui.utils.TransitionsHelper;
import org.byters.periodictablelauncher.view.util.HelperAppsCompare;
import org.byters.periodictablelauncher.view.util.HelperStorage;
import org.byters.periodictablelauncher.view.util.IHelperStorage;

import java.lang.ref.WeakReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private WeakReference<Application> refApplication;

    public AppModule(Application application) {
        this.refApplication = new WeakReference<>(application);
    }

    @Provides
    @Singleton
    HelperAppsCompare helperAppsCompare(){
        return new HelperAppsCompare();
    }

    @Provides
    @Singleton
    WeakReference<Application> applicationWeakReference() {
        return refApplication;
    }

    @Provides
    @Singleton
    INavigator navigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    IHelperStorage helperStorage() {
        return new HelperStorage();
    }

    @Provides
    @Singleton
    TransitionsHelper transitionsHelper() {
        return new TransitionsHelper();
    }

    @Provides
    @Singleton
    IRepositoryApps repositoryApps() {
        return new RepositoryApps();
    }

    //region cache
    @Provides
    @Singleton
    ICacheApps cacheApps() {
        return new CacheApps();
    }

    @Provides
    @Singleton
    ICacheState cacheState() {
        return new CacheState();
    }


    @Provides
    @Singleton
    ICachePreferences cachePreferences() {
        return new CachePreferences();
    }
    //endregion

    //region presenters
    @Provides
    @Singleton
    IPresenterActivityMain getPresenterActivityMain() {
        return new PresenterActivityMain();
    }

    @Provides
    @Singleton
    IPresenterAdapterApps presenterAdapterApps() {
        return new PresenterAdapterApps();
    }

    @Provides
    @Singleton
    IPresenterFragmentBackground presenterFragmentBackground(){
        return new PresenterFragmentBackground();
    }
    @Provides
    @Singleton
    IPresenterFragmentApps presenterFragmentApps() {
        return new PresenterFragmentApps();
    }
    //endregion

}
