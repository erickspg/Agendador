package com.example.agendador;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinhaExibida extends RecyclerView.ViewHolder{

    public TextView data, hora, titulo, status;

    public LinhaExibida(@NonNull View itemView) {
        super(itemView);
        data = itemView.findViewById(R.id.data);
        hora = itemView.findViewById(R.id.hora);
        titulo = itemView.findViewById(R.id.titulo);
        status = itemView.findViewById(R.id.status);
    }
}
