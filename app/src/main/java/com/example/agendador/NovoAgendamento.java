package com.example.agendador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NovoAgendamento extends AppCompatActivity {

    public String categorias, prestadores, servicos;
    public Spinner spCategorias, spPrestadores, spServicos;
    public ArrayAdapter<String> arrayCategorias, arrayPrestadores, arrayServicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_agendamento);

        categorias();
        prestadores();
        servicos();

        spCategorias = findViewById(R.id.spinnerCategorias);
        spPrestadores = findViewById(R.id.spinnerPrestadores);
        spServicos = findViewById(R.id.spinnerServicos);

        // instanciar adapter
        arrayCategorias = new ArrayAdapter<>(
                this,       // Activity que vai executar
                android.R.layout.simple_list_item_1 // modelo de linha
        );

        arrayPrestadores = new ArrayAdapter<>(
                this,       // Activity que vai executar
                android.R.layout.simple_list_item_1 // modelo de linha
        );

        arrayServicos = new ArrayAdapter<>(
                this,       // Activity que vai executar
                android.R.layout.simple_list_item_1 // modelo de linha
        );

        // vincula adaptador e spinner
        spCategorias.setAdapter(arrayCategorias);
        spPrestadores.setAdapter(arrayPrestadores);
        spServicos.setAdapter(arrayServicos);

        //fazer loop de categorias
        arrayCategorias.add("Selecione uma categoria");
        arrayPrestadores.add("Selecione um prestador");
        arrayServicos.add("Selecione um serviço");

//        try {
//            JSONArray jsonArray = new JSONArray(categorias);
//            Log.e("Categ", categorias);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String data = jsonObject.getString("titulo");
//                arrayCategorias.add(data);
//            }
//
//        }catch (JSONException e){
//            throw new RuntimeException(e.getMessage());
//        }

    }


    public void categorias(){

        Log.e("cat", "entrei no categorias");
        RequestQueue fila = Volley.newRequestQueue(this);
        String urlServidor = "http://10.0.2.2:8000/api/categorias/";    //Campos do JSON: state / positive / death


        // cria a requisição de mensagem e tratamento de resposta
        StringRequest  requisicao = new StringRequest (Request.Method.GET,urlServidor, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                categorias = result;
//                Log.e("Categ", categorias);

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("acesso", "Erro: " + error.getMessage());
                    }
                });

        // envia a mensagem ao servidor
        fila.add(requisicao);

    }

    public void prestadores(){

        RequestQueue fila = Volley.newRequestQueue(this);
        String urlServidor = "http://10.0.2.2:8000/api/prestadores/";    //Campos do JSON: state / positive / death


        // cria a requisição de mensagem e tratamento de resposta
        StringRequest  requisicao = new StringRequest (Request.Method.GET,urlServidor, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                prestadores = result;

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // envia a mensagem ao servidor
        fila.add(requisicao);

    }

    public void servicos(){

        RequestQueue fila = Volley.newRequestQueue(this);
        String urlServidor = "http://10.0.2.2:8000/api/servicos/";    //Campos do JSON: state / positive / death


        // cria a requisição de mensagem e tratamento de resposta
        StringRequest  requisicao = new StringRequest (Request.Method.GET,urlServidor, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                servicos = result;

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // envia a mensagem ao servidor
        fila.add(requisicao);

    }

    public void VoltarAgenda(View view) {
        finish();
    }
}
