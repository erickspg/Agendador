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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        conexaoBanco = new ClientesDAO(getBaseContext());
    }

    public void cadastrar(View v){

        //String teste = "testando";

        Intent cadastro = new Intent(getBaseContext(), CadastrarCliente.class);
        startActivity(cadastro);


        //Log.e("Teste", teste);

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
