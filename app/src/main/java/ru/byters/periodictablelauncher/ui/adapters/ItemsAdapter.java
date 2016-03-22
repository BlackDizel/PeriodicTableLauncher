package ru.byters.periodictablelauncher.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.controllers.ControllerItems;
import ru.byters.periodictablelauncher.models.AppDetail;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private Context context;

    public ItemsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return ControllerItems.getSize(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvSubtitle;
        String name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = "";
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tvSubtitle);
        }

        public void setData(int position) {
            AppDetail item = ControllerItems.getItem(context, position);
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
            Intent intent = ControllerItems.getLauncherIntent(context, name);
            context.startActivity(intent);
        }
    }
}
