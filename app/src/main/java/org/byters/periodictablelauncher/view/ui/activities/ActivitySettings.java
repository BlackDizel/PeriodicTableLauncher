package org.byters.periodictablelauncher.view.ui.activities;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.jrummyapps.android.colorpicker.ColorPanelView;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.BuildConfig;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.controller.data.repository.IRepositoryApps;
import org.byters.periodictablelauncher.model.ModelPreference;
import org.byters.periodictablelauncher.view.INavigator;

import javax.inject.Inject;

public class ActivitySettings extends ActivityBase {

    private static final String TAG_COLOR_PICKER_DIALOG = "color_picker_dialog";

    @Inject
    ICachePreferences cachePreferences;

    @Inject
    INavigator navigator;

    @Inject
    ICacheApps cacheApps;

    @Inject
    IRepositoryApps repositoryApps;

    private ColorSelectListener listenerColorSelect;
    private ClickListener listenerClick;
    private SwitchListener listenerSwitch;
    private RadioGroupListener listenerRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLauncher.getComponent().inject(this);

        navigator.set(this, getSupportFragmentManager(), R.id.flContent,R.id.flBackground);

        setContentView(R.layout.activity_settings);

        listenerClick = new ClickListener();
        listenerSwitch = new SwitchListener();
        listenerRadioGroup = new RadioGroupListener();
        listenerColorSelect = new ColorSelectListener();

        setVersion();
        updateViewIconDefaultColor();
        setShadow();
        setScrollOrientation();
        setSortMethod();
        setSortOrientation();

        ((RadioGroup) findViewById(R.id.rgScrollOrientation)).setOnCheckedChangeListener(listenerRadioGroup);
        ((RadioGroup) findViewById(R.id.rgSortMethod)).setOnCheckedChangeListener(listenerRadioGroup);
        ((RadioGroup) findViewById(R.id.rgSortOrientation)).setOnCheckedChangeListener(listenerRadioGroup);

        ((Switch) findViewById(R.id.sShadow)).setOnCheckedChangeListener(listenerSwitch);

        findViewById(R.id.tvBackground).setOnClickListener(listenerClick);
        findViewById(R.id.llTextColorDefault).setOnClickListener(listenerClick);
        findViewById(R.id.tvResetIconsLabels).setOnClickListener(listenerClick);
        findViewById(R.id.tvResetIconsColor).setOnClickListener(listenerClick);
        findViewById(R.id.tvResetCache).setOnClickListener(listenerClick);
        findViewById(R.id.tvFeedback).setOnClickListener(listenerClick);
        findViewById(R.id.tvSupport).setOnClickListener(listenerClick);
    }

    private void setSortOrientation() {
        int sortOrientation = cachePreferences.getSortOrientation();

        if (sortOrientation != ModelPreference.SORT_ORIENT_ASC
                && sortOrientation != ModelPreference.SORT_ORIENT_DESC)
            return;

        ((RadioGroup) findViewById(R.id.rgSortOrientation))
                .check(sortOrientation == ModelPreference.SORT_ORIENT_ASC
                        ? R.id.rbSortOrientAsc
                        : R.id.rbSortOrientDesc);
    }

    private void setSortMethod() {
        int sortMethod = cachePreferences.getSortMethod();

        if (sortMethod != ModelPreference.SORT_FULLTITLE
                && sortMethod != ModelPreference.SORT_LABEL
                && sortMethod != ModelPreference.SORT_DATE)
            return;

        ((RadioGroup) findViewById(R.id.rgSortMethod))
                .check(sortMethod == ModelPreference.SORT_FULLTITLE
                        ? R.id.rbSortFullTitle
                        : sortMethod == ModelPreference.SORT_LABEL
                        ? R.id.rbSortLabel
                        : R.id.rbSortDate);
    }

    private void setScrollOrientation() {
        int scrollOrientation = cachePreferences.getAppListOrientation();

        if (scrollOrientation != ModelPreference.ORIENTATION_HORIZONTAL
                && scrollOrientation != ModelPreference.ORIENTATION_VERTICAL) return;

        ((RadioGroup) findViewById(R.id.rgScrollOrientation))
                .check(scrollOrientation == ModelPreference.ORIENTATION_HORIZONTAL
                        ? R.id.rbHorizontal
                        : R.id.rbVertical);
    }

    private void setShadow() {
        ((Switch) findViewById(R.id.sShadow)).setChecked(cachePreferences.isShadowEnabled());
    }

    private void setVersion() {
        ((TextView) findViewById(R.id.tvVersion)).setText(String.format(getString(R.string.settings_version)
                , String.valueOf(BuildConfig.VERSION_CODE)
                , BuildConfig.VERSION_NAME));
    }


    @Override
    protected void onStop() {
        super.onStop();
        cachePreferences.storeData();
        cacheApps.storeData();
    }

    private void updateViewIconDefaultColor() {
        ((ColorPanelView) findViewById(R.id.cpTextColorDefault)).setColor(cachePreferences.getColorIconDefault());
    }

    private void showDialogColorSelect() {
        ColorPickerDialog.Builder builder = ColorPickerDialog
                .newBuilder()
                .setShowAlphaSlider(true);

        builder.setColor(cachePreferences.getColorIconDefault());

        ColorPickerDialog dialog = builder.create();
        dialog.setColorPickerDialogListener(listenerColorSelect);
        dialog.show(getFragmentManager(), TAG_COLOR_PICKER_DIALOG);
    }

    private class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            cachePreferences.setAppShadowVisibility(isChecked);
        }
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvBackground:
                    navigator.startActivitySetWallpaper();
                    break;
                case R.id.llTextColorDefault:
                    showDialogColorSelect();
                    break;
                case R.id.tvResetCache:
                    cacheApps.removeCache();
                    repositoryApps.request();
                    Snackbar.make(findViewById(R.id.clRoot), R.string.message_cache_removed, Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.tvResetIconsColor:
                    cacheApps.resetAppIconsCustomColor();
                    Snackbar.make(findViewById(R.id.clRoot), R.string.message_color_reset, Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.tvResetIconsLabels:
                    cacheApps.resetAppIconsCustomLabels();
                    Snackbar.make(findViewById(R.id.clRoot), R.string.message_labels_reset, Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.tvFeedback:
                    navigator.openPlayMarketPage();
                    break;
                case R.id.tvSupport:
                    navigator.sendSupportRequest();
                    break;
            }
        }
    }

    private class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (group.getId()) {
                case R.id.rgScrollOrientation:
                    int orientation = checkedId == R.id.rbHorizontal
                            ? ModelPreference.ORIENTATION_HORIZONTAL
                            : checkedId == R.id.rbVertical
                            ? ModelPreference.ORIENTATION_VERTICAL
                            : ModelPreference.NO_VALUE;
                    if (orientation != ModelPreference.NO_VALUE)
                        cachePreferences.setAppListOrientation(orientation);
                    break;
                case R.id.rgSortMethod:
                    int sortMethod = checkedId == R.id.rbSortFullTitle
                            ? ModelPreference.SORT_FULLTITLE
                            : checkedId == R.id.rbSortLabel
                            ? ModelPreference.SORT_LABEL
                            : checkedId == R.id.rbSortDate
                            ? ModelPreference.SORT_DATE
                            : ModelPreference.NO_VALUE;
                    if (sortMethod != ModelPreference.NO_VALUE)
                        cachePreferences.setSortMethod(sortMethod);
                    break;
                case R.id.rgSortOrientation:
                    int sortOrientation = checkedId == R.id.rbSortOrientAsc
                            ? ModelPreference.SORT_ORIENT_ASC
                            : checkedId == R.id.rbSortOrientDesc
                            ? ModelPreference.SORT_ORIENT_DESC
                            : ModelPreference.NO_VALUE;
                    if (sortOrientation != ModelPreference.NO_VALUE)
                        cachePreferences.setSortOrientation(sortOrientation);
                    break;
            }
        }
    }

    private class ColorSelectListener implements ColorPickerDialogListener {

        @Override
        public void onColorSelected(int dialogId, @ColorInt int color) {
            cachePreferences.setColorIconDefault(color);
            updateViewIconDefaultColor();
        }

        @Override
        public void onDialogDismissed(int dialogId) {

        }
    }
}
