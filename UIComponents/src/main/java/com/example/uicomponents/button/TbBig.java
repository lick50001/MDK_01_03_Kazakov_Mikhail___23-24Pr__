package com.example.uicomponents.button;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uicomponents.R;

public class TbBig extends TbCustom {

    public TbBig(@NonNull Context context) {
        super(context);
        inflateLayout();
    }

    public TbBig(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public TbBig(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        if (editText == null) {
            super.init(R.layout.textbox);
        }
    }
}