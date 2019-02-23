package org.byters.periodictablelauncher.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.presenter.IPresenterAdapterApps;
import org.byters.periodictablelauncher.view.presenter.callback.IPresenterAdapterAppsListener;
import org.byters.periodictablelauncher.view.ui.utils.TransitionsHelper;

import javax.inject.Inject;

public class AdapterApps extends RecyclerView.Adapter<AdapterApps.ViewHolder> {

    private final IPresenterAdapterAppsListener listenerPresenter;

    @Inject
    IPresenterAdapterApps presenterAdapterApps;

    @Inject
    TransitionsHelper transitionsHelper;

    public AdapterApps() {
        ApplicationLauncher.getComponent().inject(this);
        presenterAdapterApps.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return presenterAdapterApps.getSize();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
            , View.OnLongClickListener {

        TextView tvTitle;
        TextView tvSubtitle;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        }

        public void setData(int position) {
            setTransitionName(position);
            setShadow(position);
            setColor(position);
            setText(position);
        }

        private void setTransitionName(int position) {
            transitionsHelper.setTransitionView1(presenterAdapterApps.getItemFullname(position), tvTitle);
            transitionsHelper.setTransitionView2(presenterAdapterApps.getItemFullname(position), tvSubtitle);
        }

        private void setText(int position) {
            String title = presenterAdapterApps.getItemTitle(position);
            String label = presenterAdapterApps.getItemLabel(position);
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(label)) {
                tvTitle.setText(R.string.item_title_error);
                tvSubtitle.setText(R.string.item_subtitle_error);
            } else {
                tvTitle.setText(title);
                tvSubtitle.setText(label);
            }
        }

        private void setColor(int position) {
            if (!presenterAdapterApps.isItemColorSetted(position))
                resetColor();
            else {
                int color = presenterAdapterApps.getItemColor(position);
                tvTitle.setTextColor(color);
                tvSubtitle.setTextColor(color);
            }
        }

        private void setShadow(int position) {
            boolean isShadowVisible = presenterAdapterApps.isShadowVisible();
            tvTitle.setShadowLayer(isShadowVisible
                            ? itemView.getContext().getResources().getInteger(R.integer.text_shadow_radius)
                            : 0,
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dx),
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dy),
                    itemView.getContext().getResources().getColor(R.color.text_shadow));

            tvSubtitle.setShadowLayer(isShadowVisible
                            ? itemView.getContext().getResources().getInteger(R.integer.text_shadow_radius)
                            : 0,
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dx),
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dy),
                    itemView.getContext().getResources().getColor(R.color.text_shadow));
        }

        private void resetColor() {
            int color = presenterAdapterApps.getColorIconDefault();
            tvTitle.setTextColor(color);
            tvSubtitle.setTextColor(color);
        }

        @Override
        public void onClick(View v) {
            presenterAdapterApps.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            presenterAdapterApps.onClickLong(tvTitle, tvSubtitle, getAdapterPosition());
            return true;
        }
    }

    private class ListenerPresenter implements IPresenterAdapterAppsListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
