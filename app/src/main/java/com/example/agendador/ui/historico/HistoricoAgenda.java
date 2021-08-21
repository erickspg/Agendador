package com.example.agendador.ui.historico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendador.AgendaRecyclerView;
import com.example.agendador.AgendadamentosModel;
import com.example.agendador.ClienteHome;
import com.example.agendador.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoricoAgenda extends Fragment {

    private RecyclerView recyclerView;
    private List<AgendadamentosModel> dados = new ArrayList<>();
    private AgendaRecyclerView adaptador;
    private String response;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_historico, container, false);

        recyclerView = root.findViewById(R.id.listaAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        int resId = R.anim.layout_baixo_p_cima;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        adaptador = new AgendaRecyclerView(dados);
        recyclerView.setAdapter(adaptador);



        response = ClienteHome.getHistorico();
        adaptador.getList().addAll(transformaJson(response));

        return root;
    }

    private List<AgendadamentosModel> transformaJson(String response) {
        try {
            List<AgendadamentosModel> dados = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String data = jsonObject.getString("dataAgendamento");
                String hora = jsonObject.getString("horaInicio");
                String titulo = jsonObject.getString("titulo");
                String status = jsonObject.getString("status");
                AgendadamentosModel hist = new AgendadamentosModel(data, hora, titulo, status);
                dados.add(hist);
            }
            return dados;
        }catch (JSONException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
