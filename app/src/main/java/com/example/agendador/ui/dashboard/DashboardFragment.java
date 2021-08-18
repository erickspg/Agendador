package com.example.agendador.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendador.AgendaRecyclerView;
import com.example.agendador.AgendadamentosModel;
import com.example.agendador.ClienteHome;
import com.example.agendador.MainActivity;
import com.example.agendador.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<AgendadamentosModel> dados = new ArrayList<>();
    private AgendaRecyclerView adaptador;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.listaAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        adaptador = new AgendaRecyclerView(dados);
        recyclerView.setAdapter(adaptador);

        String response = ((ClienteHome)getActivity()).historico;
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
