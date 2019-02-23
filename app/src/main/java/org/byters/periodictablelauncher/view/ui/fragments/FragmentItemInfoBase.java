package org.byters.periodictablelauncher.view.ui.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.model.AppDetail;

import javax.inject.Inject;

public class FragmentItemInfoBase extends FragmentBase {

    @Inject
    ICacheApps cacheApps;

    @Inject
    ICachePreferences cachePreferences;

    private TextView etTitle, tvLabel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLauncher.getComponent().inject(this);
    }

    private void setText(View v, @IdRes int textViewId, @Nullable String text) {
        setText(((TextView) v.findViewById(textViewId)), text);
    }

    private void setText(TextView textView, @Nullable String text) {
        if (TextUtils.isEmpty(text)) return;
        textView.setText(text);
    }

    protected void initLabels(View v) {
        etTitle = v.findViewById(R.id.etTitle);
        tvLabel = v.findViewById(R.id.tvLabel);

        setText();
        setTextColor();
        setShadow();
    }

    private void setText() {
        setText(etTitle, cacheApps.getSelectedItemTitle());
        setText(tvLabel, cacheApps.getSelectedItemLabel());
    }

    private void setShadow() {
        setShadow(etTitle);
        setShadow(tvLabel);
    }

    protected void setTextColor() {
        setTextColor(etTitle);
        setTextColor(tvLabel);
    }

    private void setShadow(TextView view) {
        view.setShadowLayer(cachePreferences.isShadowEnabled()
                        ? getContext().getResources().getInteger(R.integer.text_shadow_radius)
                        : 0,
                getContext().getResources().getInteger(R.integer.text_shadow_dx),
                getContext().getResources().getInteger(R.integer.text_shadow_dy),
                getContext().getResources().getColor(R.color.text_shadow));
    }

    private void setTextColor(TextView view) {
        int color = getColor();
        view.setTextColor(color);
    }

    protected int getColor() {
        int color = cacheApps.getSelectedItemColor();
        if (color != AppDetail.NO_VALUE) return color;
        return cachePreferences.getColorIconDefault();
    }
}
