package ru.byters.periodictablelauncher.ui.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.byters.periodictablelauncher.R;

public class AppItemDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public AppItemDecoration(Context context) {
        margin = (int) context.getResources().getDimension(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = margin;
        outRect.left = margin;
        outRect.right = margin;
        outRect.bottom = margin;
    }
}