package org.byters.periodictablelauncher.ui.fragments;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.ControllerPreference;
import org.byters.periodictablelauncher.models.AppDetail;

public class FragmentItemInfoBase extends FragmentBase {


    private TextView etTitle, tvLabel;

    private void setText(View v, @IdRes int textViewId, @Nullable String text) {
        setText(((TextView) v.findViewById(textViewId)), text);
    }

    private void setText(TextView textView, @Nullable String text) {
        if (TextUtils.isEmpty(text)) return;
        textView.setText(text);
    }

    protected void initLabels(View v) {
        etTitle = (TextView) v.findViewById(R.id.etTitle);
        tvLabel = (TextView) v.findViewById(R.id.tvLabel);

        setText();
        setTextColor();
        setShadow();
    }

    private void setText() {
        setText(etTitle, ControllerItems.getInstance().getSelectedItemTitle());
        setText(tvLabel, ControllerItems.getInstance().getSelectedItemLabel());
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
        view.setShadowLayer(ControllerPreference.getInstance().isShadowVisible()
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
        int color = ControllerItems.getInstance().getSelectedItemColor();
        if (color != AppDetail.NO_VALUE) return color;
        return ControllerPreference.getInstance().getColorIconDefault();
    }
}
