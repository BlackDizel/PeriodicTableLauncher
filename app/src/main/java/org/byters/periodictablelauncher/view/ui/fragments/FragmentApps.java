package org.byters.periodictablelauncher.view.ui.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.presenter.IPresenterFragmentApps;
import org.byters.periodictablelauncher.view.ui.adapters.AdapterApps;

import javax.inject.Inject;

public class FragmentApps extends FragmentBase {

    @Inject
    IPresenterFragmentApps presenterFragmentApps;

    private RecyclerView rvView;

    private RecyclerViewScrollListener scrollListener;

    public static int getColumns(int gridOrientation, int screenW, int screenH, int cellW, int cellH) {

        if (cellH == 0 || cellW == 0) return 1;

        int spanNum = gridOrientation == GridLayoutManager.HORIZONTAL
                ? screenH / cellH
                : screenW / cellW;

        return Math.max(spanNum, 1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLauncher.getComponent().inject(this);
        scrollListener = new RecyclerViewScrollListener();
        presenterFragmentApps.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_icons, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        rvView = v.findViewById(R.id.rvItems);
        rvView.setAdapter(new AdapterApps());
        setLayoutManager();
        rvView.addOnScrollListener(scrollListener);
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

        int orientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                ? GridLayoutManager.HORIZONTAL
                : GridLayoutManager.VERTICAL;

        rvView.setLayoutManager(new GridLayoutManager(getContext()
                , getColumns(orientation)
                , orientation
                , false));
    }

    private int getColumns(int gridOrientation) {

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

    private class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            presenterFragmentApps.onScroll(rvView.computeHorizontalScrollOffset(), rvView.computeHorizontalScrollRange());
        }
    }
}
