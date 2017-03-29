package ru.byters.periodictablelauncher.ui.activities;

import android.os.Bundle;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.ui.NavigationHelper;
import ru.byters.periodictablelauncher.ui.fragments.FragmentIcons;
import ru.byters.periodictablelauncher.ui.fragments.FragmentItemInfo;

public class ActivityMain extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragmentGrid();
    }

    private void setFragmentGrid() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentIcons.getFragment())
                .commit();
    }

    private void setFragmentItemInfo() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentItemInfo.getFragment())
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationHelper.getInstance().addListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NavigationHelper.getInstance().removeListener(this);
    }

    @Override
    public void onNavigate(NavigationType type) {
        if (type == NavigationType.TYPE_ITEM_INFO)
            setFragmentItemInfo();
        else if (type == NavigationType.TYPE_ITEMS)
            setFragmentGrid();
    }
}
