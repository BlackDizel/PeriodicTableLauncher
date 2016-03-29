package ru.byters.periodictablelauncher.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import ru.byters.periodictablelauncher.R;

public class ItemOptionsDialog
        implements View.OnClickListener {

    private static final String PACKAGE = "package";
    @NonNull
    private BottomSheetDialog dialog;
    @NonNull
    private String name;

    public ItemOptionsDialog(@NonNull Context context, @NonNull String name) {
        this.name = name;

        dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.view_item_dialog);
        dialog.findViewById(R.id.tvInfo).setOnClickListener(this);
        dialog.findViewById(R.id.tvRemove).setOnClickListener(this);
        dialog.findViewById(R.id.tvEdit).setOnClickListener(this);
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Uri uri = Uri.fromParts(PACKAGE, name, null);
        Intent intent;
        switch (v.getId()) {
            case R.id.tvInfo:
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                v.getContext().startActivity(intent);
                break;
            case R.id.tvRemove:
                intent = new Intent(Intent.ACTION_DELETE, uri);
                v.getContext().startActivity(intent);
                break;
            case R.id.tvEdit:
                //todo implement
                break;
        }
        dialog.dismiss();
    }
}
