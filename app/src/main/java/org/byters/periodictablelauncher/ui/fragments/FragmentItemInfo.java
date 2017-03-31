package org.byters.periodictablelauncher.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.Core;
import org.byters.periodictablelauncher.ui.NavigationHelper;

public class FragmentItemInfo extends FragmentItemInfoBase
        implements View.OnClickListener {

    public static Fragment getFragment() {
        return new FragmentItemInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_dialog, container, false);

        initLabels(v);

        v.findViewById(R.id.tvInfo).setOnClickListener(this);
        v.findViewById(R.id.tvRemove).setOnClickListener(this);
        v.findViewById(R.id.ivBack).setOnClickListener(this);
        v.findViewById(R.id.ivSettings).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        Uri uri = ControllerItems.getInstance().getSelectedItemUri();
        if (uri == null) return;
        switch (v.getId()) {
            case R.id.tvInfo:
                Core.getInstance().startActivityAppDetails(v.getContext(), uri);

                NavigationHelper.getInstance().navigateItems();
                break;
            case R.id.tvRemove:
                Core.getInstance().startActivityAppRemove(v.getContext(), uri);

                NavigationHelper.getInstance().navigateItems();
                break;
            case R.id.ivSettings:
                NavigationHelper.getInstance().navigateItemEdit();
                break;
            case R.id.ivBack:
                getActivity().onBackPressed();
                break;
        }
    }
}