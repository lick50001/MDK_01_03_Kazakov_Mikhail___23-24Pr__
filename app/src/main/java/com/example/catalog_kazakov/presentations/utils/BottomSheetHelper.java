package com.example.catalog_kazakov.presentations.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catalog_kazakov.MainActivity;
import com.example.catalog_kazakov.R;
import com.example.network.domains.common.Settings;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthBig;
import com.example.uicomponents.button.BthCustom;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

public class BottomSheetHelper {

    private BottomSheetDialog dialog;

    public BottomSheetHelper(Context context) {
        this.dialog = new BottomSheetDialog(context);
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.dismiss();
    }

    public void setContentView(View view) {
        dialog.setContentView(view);
    }

    public static void Create(
            Context context,
            Activity activity,
            Product product,
            BthCustom bthCardAdd,
            ProgressDialogHelper progressDialogHelper) {

        progressDialogHelper.progressDialog.show();
        BottomSheetHelper helper = new BottomSheetHelper(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_description, null);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvExpenditure = view.findViewById(R.id.tvExpenditure);
        View btnClose = view.findViewById(R.id.btnClose);
        BthBig btnAdd = view.findViewById(R.id.btnAdd);
        ImageView image = view.findViewById(R.id.imageView);

        if (product.img != null) {
            Picasso.get()
                    .load(Settings.URL + "/img/" + product.img)
                    .into(image);
        }

        tvName.setText(product.name);
        tvDescription.setText(product.description);
        tvExpenditure.setText(product.expenditure);

        btnAdd.init("Добавить за " + product.price + "₽", BthCustom.TypeButton.PRIMARY);

        btnClose.setOnClickListener(v -> {
            helper.hide();
        });

        btnAdd.Bth.setOnClickListener(v -> {
            ((MainActivity) activity).BasketCreate(product, bthCardAdd);
            helper.hide();
        });

        helper.setContentView(view);
        progressDialogHelper.progressDialog.hide();
        helper.show();
    }
}