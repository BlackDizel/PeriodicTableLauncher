package org.byters.periodictablelauncher.view.presenter;

import android.widget.ImageView;

import org.byters.periodictablelauncher.view.presenter.callback.IPresenterFragmentBackgroundListener;

public interface IPresenterFragmentBackground {
    void setListener(IPresenterFragmentBackgroundListener listener);

    void onCreateView(ImageView imageView);

    void onImageLayoutChange();
}
