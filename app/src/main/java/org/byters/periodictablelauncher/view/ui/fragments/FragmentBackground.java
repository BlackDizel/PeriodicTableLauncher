package org.byters.periodictablelauncher.view.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.presenter.IPresenterFragmentBackground;
import org.byters.periodictablelauncher.view.presenter.callback.IPresenterFragmentBackgroundListener;

import javax.inject.Inject;

public class FragmentBackground extends FragmentBase {

    @Inject
    IPresenterFragmentBackground presenterFragmentBackground;
    private ImageView imageView;
    private ListenerPresenter listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLauncher.getComponent().inject(this);
        presenterFragmentBackground.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        initViews(view);
        presenterFragmentBackground.onCreateView(imageView);
        return view;
    }

    void initViews(View view) {
        imageView = view.findViewById(R.id.imageView);
        imageView.addOnLayoutChangeListener(new BackgroundLayoutChangeListener());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private class BackgroundLayoutChangeListener implements View.OnLayoutChangeListener {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            presenterFragmentBackground.onImageLayoutChange();
        }
    }

    private class ListenerPresenter implements IPresenterFragmentBackgroundListener {
        @Override
        public void setBackground(Drawable wallpaperDrawable) {
            imageView.setImageDrawable(wallpaperDrawable);
        }
    }
}
