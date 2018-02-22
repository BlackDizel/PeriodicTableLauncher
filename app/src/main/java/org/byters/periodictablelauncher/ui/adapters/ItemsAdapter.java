package org.byters.periodictablelauncher.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.ControllerPreference;
import org.byters.periodictablelauncher.controllers.Core;
import org.byters.periodictablelauncher.controllers.TransitionsHelper;
import org.byters.periodictablelauncher.models.AppDetail;
import org.byters.periodictablelauncher.ui.NavigationHelper;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return ControllerItems.getInstance().getSize();
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
        AppDetail item;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            item = null;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        }

        public void setData(int position) {
            item = ControllerItems.getInstance().getItem(position);
            setTransitionName();
            setShadow();
            setColor();
            setText();
        }

        private void setTransitionName() {
            TransitionsHelper.setTransitionView1(item, tvTitle);
            TransitionsHelper.setTransitionView2(item, tvSubtitle);
        }

        private void setText() {
            if (item == null) {
                tvTitle.setText(R.string.item_title_error);
                tvSubtitle.setText(R.string.item_subtitle_error);
            } else {
                tvTitle.setText(item.getTitle());
                tvSubtitle.setText(item.getLabel());
            }
        }

        private void setColor() {
            if (item == null || !item.isColorSetted())
                resetColor();
            else {
                tvTitle.setTextColor(item.getColor());
                tvSubtitle.setTextColor(item.getColor());
            }
        }

        private void setShadow() {
            tvTitle.setShadowLayer(ControllerPreference.getInstance().isShadowVisible()
                            ? itemView.getContext().getResources().getInteger(R.integer.text_shadow_radius)
                            : 0,
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dx),
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dy),
                    itemView.getContext().getResources().getColor(R.color.text_shadow));

            tvSubtitle.setShadowLayer(ControllerPreference.getInstance().isShadowVisible()
                            ? itemView.getContext().getResources().getInteger(R.integer.text_shadow_radius)
                            : 0,
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dx),
                    itemView.getContext().getResources().getInteger(R.integer.text_shadow_dy),
                    itemView.getContext().getResources().getColor(R.color.text_shadow));
        }


        private void resetColor() {
            int color = ControllerPreference.getInstance().getColorIconDefault();
            tvTitle.setTextColor(color);
            tvSubtitle.setTextColor(color);
        }

        @Override
        public void onClick(View v) {
            if (item == null) return;
            Core.getInstance().startActivity(item.getPackageName(), item.getActviityName());
        }

        @Override
        public boolean onLongClick(View v) {
            if (item == null) return true;
            ControllerItems.getInstance().setSelectedItem(item);

            NavigationHelper.getInstance().navigateItemInfo(tvTitle, tvSubtitle);

            return true;
        }
    }
}
