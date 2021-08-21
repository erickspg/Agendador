package com.example.agendador;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ClienteHome extends AppCompatActivity {


    private ClientesDAO conexaoBanco;
    private BottomNavigationView navView;
    public String nome;
    public static String agenda, historico = "[{'titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído'}]";
    private Cliente cliente = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_home);

        navView = findViewById(R.id.nav_view);
        conexaoBanco = new ClientesDAO(getBaseContext());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //obtendo agenda da thred na main
        agenda = MainActivity.getAgendamentos();

        new Thread(new Runnable() {
            @Override
            public void run() {
                historico();
                retornaNome();
            }
        }).start();


    }

    public static String getAgenda(){
        agenda = MainActivity.getAgendamentos();
        return agenda;
    }

    public static String getHistorico(){
        return historico;
    }

    public void historico(){

        RequestQueue fila = Volley.newRequestQueue(this);
        String urlServidor = "http://10.0.2.2:8000/api/historico/";    //Campos do JSON: state / positive / death


        // cria a requisição de mensagem e tratamento de resposta
        StringRequest requisicao = new StringRequest (Request.Method.GET,urlServidor, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                historico = result;
                Log.e("historico", historico);

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

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void logoff(View view){

        // destruir sessao
        SharedPreferences preferencias = getSharedPreferences(
                "user_preferences", // 1 - chave das preferencias buscadas
                MODE_PRIVATE);      // 2 - modo de acesso
        SharedPreferences.Editor editor = preferencias.edit();

        editor.remove("login").apply();

        finish();


    }

    public void retornaNome(){
        SharedPreferences preferencias = getSharedPreferences(

                "user_preferences",
                MODE_PRIVATE);

        String idUser = preferencias.getString( // Pego ID do usuario na sessao
                "login",
                "idUser");

        Integer id = Integer.parseInt(idUser);
        nome = conexaoBanco.retornaNome(id);
    }

}
