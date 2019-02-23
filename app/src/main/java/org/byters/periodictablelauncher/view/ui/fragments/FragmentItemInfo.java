package org.byters.periodictablelauncher.view.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.INavigator;
import org.byters.periodictablelauncher.view.ui.utils.TransitionsHelper;

import javax.inject.Inject;

public class FragmentItemInfo extends FragmentItemInfoBase
        implements View.OnClickListener {

    @Inject
    TransitionsHelper transitionsHelper;

    @Inject
    INavigator navigator;

    public static Fragment getFragment() {
        return new FragmentItemInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLauncher.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_dialog, container, false);

        initLabels(v);

        v.findViewById(R.id.tvInfo).setOnClickListener(this);
        v.findViewById(R.id.tvRemove).setOnClickListener(this);
        v.findViewById(R.id.ivBack).setOnClickListener(this);
        v.findViewById(R.id.ivSettings).setOnClickListener(this);

        ViewCompat.setTransitionName(v.findViewById(R.id.etTitle), transitionsHelper.getViewName1());
        ViewCompat.setTransitionName(v.findViewById(R.id.tvLabel), transitionsHelper.getViewName2());

        return v;
    }

    @Override
    public void onClick(View v) {
        Uri uri = cacheApps.getSelectedItemUri();
        if (uri == null) return;
        switch (v.getId()) {
            case R.id.tvInfo:
                getActivity().onBackPressed();
                navigator.startActivityAppDetails(uri);
                break;
            case R.id.tvRemove:
                getActivity().onBackPressed();
                navigator.startActivityAppRemove(uri);
                break;
            case R.id.ivSettings:
                navigator.navigateItemEdit(getView().findViewById(R.id.etTitle), getView().findViewById(R.id.tvLabel));
                break;
            case R.id.ivBack:
                getActivity().onBackPressed();
                break;
        }
    }
}