package com.example.agendador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private EditText user, pass;
    private ClientesDAO conexaoBanco;
    //private static final int REQUEST_CODE = 100;
    public static String agendamentos = "[{'titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído','titulo':'Quadra de futebol','dataAgendamento':'01/08/2021','horaInicio':'19:00:00','status':'Concluído'}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        conexaoBanco = new ClientesDAO(getBaseContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                agendamentos();
            }
        }).start();
    }

    public void agendamentos(){

        RequestQueue fila = Volley.newRequestQueue(this);
        String urlServidor = "http://10.0.2.2:8000/api/agendamentos/";    //Campos do JSON: state / positive / death


        // cria a requisição de mensagem e tratamento de resposta
        StringRequest requisicao = new StringRequest (Request.Method.GET,urlServidor, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                agendamentos = result;
                Log.e("agendamentos", agendamentos);

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
    public static String getAgendamentos(){
        return agendamentos;
    }

    public void cadastrar(View v){

        Intent cadastro = new Intent(getBaseContext(), CadastrarCliente.class);
        startActivity(cadastro);

    }

    public void acessar(View v){

        if(!validaForm()){
            return;
        }

        Intent home = new Intent(getBaseContext(),  ClienteHome.class);
        startActivity(home);
    }



    @Override
    protected void onResume() {
        super.onResume();
        pass.setText("");
        user.setText("");

    }

    public boolean validaForm() {

        String etUser = user.getText().toString();
        String etPass = pass.getText().toString();

        if (etUser.isEmpty()) {
            user.setError("O campo CPF é obrigatório!");
            user.requestFocus();
            return false;
        }

        if (etPass.isEmpty()) {
            pass.setError("O campo senha é obrigatório!");
            pass.requestFocus();
            return false;
        }

        String existe = conexaoBanco.existe(etUser, etPass);

        if(existe.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Cliente inexistente, realize seu cadastro!",
                    Toast.LENGTH_SHORT).show();
            pass.setText("");
            user.setText("");
            return false;
        }else{

            SharedPreferences preferencias = getSharedPreferences(
                    "user_preferences", // 1 - chave das preferencias buscadas
                    MODE_PRIVATE);      // 2 - modo de acesso
            SharedPreferences.Editor editor = preferencias.edit();

            editor.putString(
                    "login", // 1 - Chave
                    existe); // 2 - Valor
            editor.apply();

            return true;
        }

    }

}
