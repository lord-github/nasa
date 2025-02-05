package com.bbstudios.hks;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomInfoDialog extends Dialog {
    public CustomInfoDialog(@NonNull Context context, String title, String description) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_info);

        TextView titleTextView = findViewById(R.id.dialog_title);
        TextView descriptionTextView = findViewById(R.id.dialog_description);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
    }

}
