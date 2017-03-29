package ru.byters.periodictablelauncher.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.controllers.ControllerItems;
import ru.byters.periodictablelauncher.controllers.Core;
import ru.byters.periodictablelauncher.ui.NavigationHelper;

public class FragmentItemInfo extends FragmentBase
        implements View.OnClickListener {

    public static Fragment getFragment() {
        return new FragmentItemInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_dialog, container, false);

        v.findViewById(R.id.tvInfo).setOnClickListener(this);
        v.findViewById(R.id.tvRemove).setOnClickListener(this);
        v.findViewById(R.id.ivBack).setOnClickListener(this);
        v.findViewById(R.id.ivSettings).setOnClickListener(this);

        setText(v, R.id.tvTitle, ControllerItems.getInstance().getSelectedItemLabel());
        setText(v, R.id.tvLabel, ControllerItems.getInstance().getSelectedItemTitle());

        return v;
    }

    private void setText(View v, @IdRes int textViewId, @Nullable String text) {
        if (TextUtils.isEmpty(text)) return;
        ((TextView) v.findViewById(textViewId)).setText(text);
    }

    @Override
    public void onClick(View v) {
        Uri uri = ControllerItems.getInstance().getSelectedItemUri();
        if (uri == null) return;
        switch (v.getId()) {
            case R.id.tvInfo:
                Core.getInstance().startActivityAppDetails(v.getContext(), uri);
                break;
            case R.id.tvRemove:
                Core.getInstance().startActivityAppRemove(v.getContext(), uri);
                break;
        }

        NavigationHelper.getInstance().navigateItems();
    }
}