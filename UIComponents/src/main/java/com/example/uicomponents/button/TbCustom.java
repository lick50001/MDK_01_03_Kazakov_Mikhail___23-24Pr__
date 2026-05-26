package com.example.uicomponents.button;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uicomponents.R;

public class TbCustom extends ConstraintLayout {

    public EditText editText;
    private TextView label;
    private TextView errorHint;

    private boolean hasError = false;
    private boolean hasFocus = false;

    public enum TypeText {
        PRIMARY,
        ACTIVITY,
        ERROR
    }

    public TbCustom(@NonNull Context context) { super(context); }
    public TbCustom(@NonNull Context context, @Nullable AttributeSet attrs) { super(context, attrs); }
    public TbCustom(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }

    public void init(Integer idLayout) {
        if (idLayout == null || editText != null) return;
        LayoutInflater.from(getContext()).inflate(idLayout, this, true);

        editText = findViewById(R.id.tb);
        label = findViewById(R.id.tv);
        errorHint = findViewById(R.id.errorhint);

        if (editText != null) {
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                this.hasFocus = hasFocus;
                updateBackground();
            });
        }
    }

    /**
     * Полный init со всеми параметрами
     * @param labelValue Текст лейбла (сверху)
     * @param hintValue Текст подсказки в поле
     * @param value Начальное значение в поле
     * @param type Стиль (PRIMARY, ACTIVITY, ERROR)
     * @param errorMessage Текст ошибки (показывается при type=ERROR или валидации)
     */
    public void init(String labelValue, String hintValue, String value, TypeText type, String errorMessage) {
        // Сначала инициализируем лейаут, если ещё не сделано
        if (editText == null) {
            init(R.layout.textbox);
        }

        setLabel(labelValue);

        setHint(hintValue);

        setValue(value);

        if (type == TypeText.ERROR || errorMessage != null) {
            setErrorState(errorMessage != null ? errorMessage : "Ошибка ввода");
        } else {
            clearErrorState();
            if (editText != null) {
                editText.setBackgroundResource(R.drawable.tb_text_states);
                editText.setTextColor(Color.parseColor("#7E7E9A"));
            }
        }
    }

    public void setLabel(String text) {
        if (label != null && text != null) {
            label.setText(text);
        }
    }

    public void setHint(String text) {
        if (editText != null && text != null) {
            editText.setHint(text);
        }
    }

    public void setValue(String text) {
        if (editText != null && text != null) {
            editText.setText(text);
        }
    }

    public String getValue() {
        return editText != null ? editText.getText().toString() : "";
    }

    public void setErrorMessage(String message) {
        if (errorHint != null && message != null) {
            errorHint.setText(message);
            if (hasError) {
                errorHint.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setErrorState(String message) {
        if (editText == null) return;
        hasError = true;

        if (errorHint != null) {
            errorHint.setText(message != null ? message : "Ошибка");
            errorHint.setVisibility(View.VISIBLE);
        }

        updateBackground();
    }

    public void clearErrorState() {
        if (editText == null) return;
        hasError = false;

        if (errorHint != null) {
            errorHint.setVisibility(View.GONE);
        }

        updateBackground();
    }

    private void updateBackground() {
        if (editText == null) return;

        if (hasFocus) {
            editText.setBackgroundResource(R.drawable.tb_text_states);
            editText.setTextColor(Color.parseColor("#2196F3"));
        } else if (hasError) {
            editText.setBackgroundResource(R.drawable.tb_error);
            editText.setTextColor(Color.parseColor("#F44336"));
        } else {
            editText.setBackgroundResource(R.drawable.tb_text_states);
            editText.setTextColor(Color.parseColor("#7E7E9A"));
        }
    }


    public boolean validateLettersOnly(String error) {
        if (editText == null) return false;
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) { clearErrorState(); return false; }

        boolean isValid = text.matches("^[a-zA-Zа-яА-ЯёЁ\\s]+$");
        if (!isValid) setErrorState(error);
        else clearErrorState();
        return isValid;
    }

    public boolean validateNumbersOnly(String error) {
        if (editText == null) return false;
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) { clearErrorState(); return false; }

        boolean isValid = text.matches("^\\d+$");
        if (!isValid) setErrorState(error);
        else clearErrorState();
        return isValid;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (editText != null) {
            editText.setEnabled(enabled);
            editText.setAlpha(enabled ? 1f : 0.5f);
        }
        if (label != null) {
            label.setAlpha(enabled ? 1f : 0.5f);
        }
    }
}