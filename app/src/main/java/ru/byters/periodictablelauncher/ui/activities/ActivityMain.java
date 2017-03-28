package ru.byters.periodictablelauncher.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.ui.fragments.FragmentIcons;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment();
    }

    private void setFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentIcons.getFragment())
                .commit();
    }
}
