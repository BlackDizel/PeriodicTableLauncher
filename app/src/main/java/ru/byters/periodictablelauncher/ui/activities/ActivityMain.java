package ru.byters.periodictablelauncher.ui.activities;

import android.os.Bundle;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.controllers.ControllerItems;
import ru.byters.periodictablelauncher.controllers.ControllerPreference;
import ru.byters.periodictablelauncher.ui.NavigationHelper;
import ru.byters.periodictablelauncher.ui.fragments.FragmentIcons;
import ru.byters.periodictablelauncher.ui.fragments.FragmentItemEdit;
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
        ControllerItems.getInstance().updateData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NavigationHelper.getInstance().removeListener(this);
        ControllerItems.getInstance().storeData();
        ControllerPreference.getInstance().storeData();
    }

    @Override
    public void onNavigate(NavigationType type) {
        if (type == NavigationType.TYPE_ITEM_INFO)
            setFragmentItemInfo();
        else if (type == NavigationType.TYPE_ITEMS)
            setFragmentGrid();
        else if (type == NavigationType.TYPE_ITEM_EDIT)
            setFragmentItemEdit();
    }

    private void setFragmentItemEdit() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentItemEdit.getFragment())
                .commit();
    }
}
