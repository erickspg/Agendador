package com.example.agendador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AgendaRecyclerView extends RecyclerView.Adapter <LinhaExibida>{
    private final List<AgendadamentosModel> list;
    private Context context;

    public AgendaRecyclerView(List<AgendadamentosModel> valores) {
        list = new ArrayList<>();
        list.addAll(valores);
    }

    public List<AgendadamentosModel> getList() {
        return list;
    }

    @NonNull
    @Override
    public LinhaExibida onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final LinhaExibida holder = new LinhaExibida(LayoutInflater.from(context)
                .inflate(R.layout.linha_simples_adaptador, parent, false));

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull LinhaExibida holder, int position) {
        // dado selecionado
        AgendadamentosModel conteudoLinha = list.get(position);
        // exibe dados
        holder.data.setText(String.valueOf(conteudoLinha.getData()));
        holder.hora.setText(String.valueOf(conteudoLinha.getHora()));
        holder.titulo.setText(conteudoLinha.getTitulo());
        holder.status.setText(conteudoLinha.getStatus());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}
