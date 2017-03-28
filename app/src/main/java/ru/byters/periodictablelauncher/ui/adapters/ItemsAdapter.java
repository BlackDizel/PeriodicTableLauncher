package ru.byters.periodictablelauncher.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
        String name;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            name = "";
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tvSubtitle);
        }

        public void setData(int position) {
            AppDetail item = ControllerItems.getInstance().getItem(position);
            if (item == null) {
                name = "";
                tvTitle.setText(R.string.item_title_error);
                tvSubtitle.setText(R.string.item_subtitle_error);
            } else {
                name = item.getName();
                tvTitle.setText(item.getTitle());
                tvSubtitle.setText(item.getLabel());
            }
        }

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(name)) return;
            Core.getInstance().startActivity(name);
        }

        @Override
        public boolean onLongClick(View v) {
            if (TextUtils.isEmpty(name)) return true;
            new ItemOptionsDialog(v.getContext(), name).show();
            return true;
        }
    }
}
