package org.byters.periodictablelauncher.view.ui.activities;

import android.os.Bundle;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.presenter.IPresenterActivityMain;

import javax.inject.Inject;

public class ActivityMain extends ActivityBase {

    @Inject
    IPresenterActivityMain presenterActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationLauncher.getComponent().inject(this);
        setContentView(R.layout.activity_main);

        presenterActivityMain.onCreate(this, getSupportFragmentManager(), R.id.flContent, R.id.flBackground);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenterActivityMain.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenterActivityMain.onStart(this, getSupportFragmentManager(), R.id.flContent, R.id.flBackground);
    }
}
