package com.bbstudios.hks;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class sHolder extends RecyclerView.ViewHolder {
    TextView zagtext,duzumtext;
    ImageView gorkez;
    Button button;

    LinearLayout expandableLayout;
    public sHolder(@NonNull View itemView) {
        super(itemView);
        expandableLayout=itemView.findViewById(R.id.expandableLayout);
        gorkez=itemView.findViewById(R.id.expandButton);
        button=itemView.findViewById(R.id.barlamak);
        zagtext=itemView.findViewById(R.id.titleTextView);
        duzumtext=itemView.findViewById(R.id.detailsTextView);

    }
}
