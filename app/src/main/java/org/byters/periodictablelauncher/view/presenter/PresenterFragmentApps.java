package org.byters.periodictablelauncher.view.presenter;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheState;
import org.byters.periodictablelauncher.controller.data.repository.IRepositoryApps;

import javax.inject.Inject;

public class PresenterFragmentApps implements IPresenterFragmentApps {

    @Inject
    ICacheApps cacheApps;

    @Inject
    ICacheState cacheState;

    @Inject
    IRepositoryApps repositoryApps;

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
}
