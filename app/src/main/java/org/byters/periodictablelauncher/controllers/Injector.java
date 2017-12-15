package org.byters.periodictablelauncher.controllers;


import org.byters.periodictablelauncher.view.presenter.IPresenterWallpaper;
import org.byters.periodictablelauncher.view.presenter.PresenterWallpaper;

public class Injector {
    private IPresenterWallpaper presenterWallpaper;

    public IPresenterWallpaper getPresenterWallpaper() {
        if (presenterWallpaper == null) presenterWallpaper = new PresenterWallpaper();
        return presenterWallpaper;
    }
}
