package ru.byters.periodictablelauncher.ui.fragments;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.controllers.ControllerItems;
import ru.byters.periodictablelauncher.models.AppDetail;

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

        setText(etTitle, ControllerItems.getInstance().getSelectedItemTitle());
        setText(tvLabel, ControllerItems.getInstance().getSelectedItemLabel());
        setTextColor();
    }

    protected void setTextColor() {
        int color = ControllerItems.getInstance().getSelectedItemColor();
        if (color == AppDetail.NO_VALUE) return;
        etTitle.setTextColor(color);
        tvLabel.setTextColor(color);
    }

}
