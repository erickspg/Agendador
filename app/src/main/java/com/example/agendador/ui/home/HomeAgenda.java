package com.example.agendador.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class HomeAgenda extends Fragment {

    private RecyclerView recyclerView;
    private List<AgendadamentosModel> dados = new ArrayList<>();
    private AgendaRecyclerView adaptador;
    private TextView nomeCliente;
    private Button btnSair;
    private ProgressBar loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.listaAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        int resId = R.anim.layout_desliza_direita;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        adaptador = new AgendaRecyclerView(dados);
        recyclerView.setAdapter(adaptador);
        //loading = root.findViewById(R.id.loadingPanel);

        String agenda = ClienteHome.getAgenda();
        adaptador.getList().addAll(transformaJson(agenda));
        //Log.e("response", agenda);

        btnSair = root.findViewById(R.id.sair);
        nomeCliente = root.findViewById(R.id.nomeUser);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoff(view);
            }
        });

        //FloatingActionButton fab = root.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent newAg = new Intent(getActivity().getBaseContext(),  NovoAgendamento.class);
//                startActivity(newAg);
//            }
//        });

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

                //loading.setVisibility(View.GONE);
                AgendadamentosModel hist = new AgendadamentosModel(data, hora, titulo, status);
                dados.add(hist);

            }
            return dados;
        }catch (JSONException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        String nomeUser = ((ClienteHome)getActivity()).nome;
        nomeCliente.setText(nomeUser);
    }


    public void logoff(View view){

        // destruir sessao
        SharedPreferences preferencias = getActivity().getSharedPreferences(
                "user_preferences", // 1 - chave das preferencias buscadas
                getActivity().MODE_PRIVATE);      // 2 - modo de acesso
        SharedPreferences.Editor editor = preferencias.edit();

        editor.remove("login").apply();

        getActivity().finish();


    }

}
