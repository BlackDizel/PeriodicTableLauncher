package org.byters.periodictablelauncher.ui.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;

import com.jrummyapps.android.colorpicker.ColorPickerView;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.Core;
import org.byters.periodictablelauncher.controllers.TransitionsHelper;
import org.byters.periodictablelauncher.models.AppDetail;

public class FragmentItemEdit extends FragmentItemInfoBase
        implements View.OnClickListener, TextWatcher, ColorPickerView.OnColorChangedListener {

    public static Fragment getFragment() {
        return new FragmentItemEdit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_edit_container, container, false);
        initView(inflater, (ViewGroup) v);
        return v;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        initView(LayoutInflater.from(getContext()), (ViewGroup) getView());
    }

    private void initView(LayoutInflater inflater, ViewGroup v) {
        v.removeAllViews();

        inflater.inflate(R.layout.fragment_item_edit, v, true);

        initLabels(v);

        v.findViewById(R.id.ivBack).setOnClickListener(this);
        ((EditText) v.findViewById(R.id.etTitle)).addTextChangedListener(this);
        ((ColorPickerView) v.findViewById(R.id.cpv)).setOnColorChangedListener(this);

        ((ColorPickerView) v.findViewById(R.id.cpv)).setColor(getColor());

        ViewCompat.setTransitionName(v.findViewById(R.id.etTitle), TransitionsHelper.getViewName1());
        ViewCompat.setTransitionName(v.findViewById(R.id.tvLabel), TransitionsHelper.getViewName2());

        animateEntrance(v);
    }

    private void animateEntrance(ViewGroup v) {
        AlphaAnimation animationAlpha = new AlphaAnimation(0, 1);
        animationAlpha.setStartOffset(250);
        animationAlpha.setDuration(250);
        animationAlpha.setFillAfter(true);
        v.findViewById(R.id.cpv).setAnimation(animationAlpha);
        animationAlpha.start();
    }

    @Override
    public void onClick(View v) {
        Core.hideKeyboard(getContext(), getView().getWindowToken());
        getActivity().onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) return;
        ControllerItems.getInstance().setSelectedItemTitle(s.subSequence(0, Math.min(AppDetail.MAX_ITEM_TITLE_LENGTH, s.length())).toString());
    }

    @Override
    public void onColorChanged(int color) {
        ControllerItems.getInstance().setSelectedItemColor(color);
        setTextColor();
    }
}