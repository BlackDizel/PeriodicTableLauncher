package org.byters.periodictablelauncher.view.presenter;

import android.view.View;

import org.byters.periodictablelauncher.view.presenter.callback.IPresenterAdapterAppsListener;

public interface IPresenterAdapterApps {
    int getSize();

    int getColorIconDefault();

    String getItemFullname(int position);

    String getItemTitle(int position);

    String getItemLabel(int position);

    boolean isItemColorSetted(int position);

    int getItemColor(int position);

    boolean isShadowVisible();

    void onClick(int position);

    void onClickLong(View view1, View view2, int position);

    void setListener(IPresenterAdapterAppsListener listener);
}
