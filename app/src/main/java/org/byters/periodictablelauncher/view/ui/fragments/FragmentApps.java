package org.byters.periodictablelauncher.view.ui.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.presenter.IPresenterFragmentApps;
import org.byters.periodictablelauncher.view.presenter.callback.IPresenterFragmentAppsListener;
import org.byters.periodictablelauncher.view.ui.adapters.AdapterApps;

import javax.inject.Inject;

public class FragmentApps extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterFragmentApps presenterFragmentApps;

    private RecyclerView rvView;

    private RecyclerViewScrollListener scrollListener;
    private View vSettings;
    private ListenerPresenter listenerPresenter;
    private ImageView ivSettingsButton;
    private ImageView ivSettingsSortOrientation;

    public static int getColumns(SpannedGridLayoutManager.Orientation gridOrientation, int screenW, int screenH, int cellW, int cellH) {

        if (cellH == 0 || cellW == 0) return 1;

        int maxSize = Math.max(cellH, cellW);

        int spanNum = gridOrientation == SpannedGridLayoutManager.Orientation.HORIZONTAL
                ? screenH / maxSize
                : screenW / maxSize;

        return Math.max(spanNum, 1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLauncher.getComponent().inject(this);
        scrollListener = new RecyclerViewScrollListener();
        presenterFragmentApps.setListener(listenerPresenter = new ListenerPresenter());
        presenterFragmentApps.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_icons, container, false);
        initViews(v);
        presenterFragmentApps.onCreateView();
        return v;
    }

    private void initViews(View v) {
        rvView = v.findViewById(R.id.rvItems);
        rvView.setAdapter(new AdapterApps());
        setLayoutManager();
        rvView.addOnScrollListener(scrollListener);

        vSettings = v.findViewById(R.id.svSettings);
        ivSettingsButton = v.findViewById(R.id.ivSettingsButton);
        ivSettingsSortOrientation = v.findViewById(R.id.ivSortOrientation);

        v.findViewById(R.id.flSettingsButton).setOnClickListener(this);
        v.findViewById(R.id.ivSettings).setOnClickListener(this);
        v.findViewById(R.id.ivAppShadow).setOnClickListener(this);
        v.findViewById(R.id.ivAppColor).setOnClickListener(this);
        v.findViewById(R.id.ivGridSize).setOnClickListener(this);
        v.findViewById(R.id.ivSortMethod).setOnClickListener(this);
        v.findViewById(R.id.ivSortOrientation).setOnClickListener(this);
        v.findViewById(R.id.ivBackground).setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutManager();
    }

    private void setLayoutManager() {
        //TODO implement
        /*int orientation = ControllerPreference.getInstance().getAppListOrientation() == ModelPreference.ORIENTATION_HORIZONTAL
                ? GridLayoutManager.HORIZONTAL
                : GridLayoutManager.VERTICAL;*/

        SpannedGridLayoutManager.Orientation orientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                ? SpannedGridLayoutManager.Orientation.HORIZONTAL
                : SpannedGridLayoutManager.Orientation.VERTICAL;

        rvView.setLayoutManager(new SpannedGridLayoutManager(orientation, getColumns(orientation)));
    }

    private int getColumns(SpannedGridLayoutManager.Orientation gridOrientation) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.view_item, null);
        ((TextView) view.findViewById(R.id.tvTitle)).setText("Mm");
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        return getColumns(gridOrientation,
                getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels,
                view.getMeasuredWidth(),
                view.getMeasuredHeight());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.flSettingsButton)
            presenterFragmentApps.onClickSettings();
        if (v.getId() == R.id.ivSettings)
            presenterFragmentApps.onClickSettingsApp();
        if (v.getId() == R.id.ivAppShadow)
            presenterFragmentApps.onClickSettingsAppShadow();
        if (v.getId() == R.id.ivAppColor)
            presenterFragmentApps.onClickSettingsAppColor();
        if (v.getId() == R.id.ivGridSize)
            presenterFragmentApps.onClickSettingsGridSize();
        if (v.getId() == R.id.ivSortMethod)
            presenterFragmentApps.onClickSettingsSortMethod();
        if (v.getId() == R.id.ivSortOrientation)
            presenterFragmentApps.onClickSettingsSortOrientation();
        if (v.getId() == R.id.ivBackground)
            presenterFragmentApps.onClickSettingsBackground();
    }

    private class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            presenterFragmentApps.onScroll(rvView.computeHorizontalScrollOffset(), rvView.computeHorizontalScrollRange());
        }
    }

    private class ListenerPresenter implements IPresenterFragmentAppsListener {

        @Override
        public void setStateSettingsOpened(boolean isOpenedNewState) {
            if (!isAdded()) return;
            vSettings.setVisibility(isOpenedNewState ? View.VISIBLE : View.GONE);
            ivSettingsButton.setImageDrawable(getResources().getDrawable(isOpenedNewState ? R.drawable.ic_arrow_left_24dp : R.drawable.ic_arrow_right_24dp));
        }

        @Override
        public void setSortOrientation(int drawable) {
            ivSettingsSortOrientation.setImageResource(drawable);
        }
    }
}
