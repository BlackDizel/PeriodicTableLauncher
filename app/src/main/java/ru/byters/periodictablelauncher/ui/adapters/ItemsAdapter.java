package ru.byters.periodictablelauncher.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.controllers.ControllerItems;
import ru.byters.periodictablelauncher.controllers.Core;
import ru.byters.periodictablelauncher.models.AppDetail;
import ru.byters.periodictablelauncher.ui.dialogs.ItemOptionsDialog;

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
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tvSubtitle);
        }

        public void setData(int position) {
            item = ControllerItems.getInstance().getItem(position);
            if (item == null) {
                tvTitle.setText(R.string.item_title_error);
                tvSubtitle.setText(R.string.item_subtitle_error);
            } else {
                tvTitle.setText(item.getTitle());
                tvSubtitle.setText(item.getLabel());
            }
        }

        @Override
        public void onClick(View v) {
            if (item == null) return;
            Core.getInstance().startActivity(item.getName());
        }

        @Override
        public boolean onLongClick(View v) {
            if (item == null) return true;
            ControllerItems.getInstance().setSelectedItem(item);
            new ItemOptionsDialog(v.getContext()).show();
            return true;
        }
    }
}
