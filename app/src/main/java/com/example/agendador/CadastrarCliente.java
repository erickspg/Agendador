package com.example.agendador;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class CadastrarCliente extends AppCompatActivity {

    private EditText editTextNome, editTextCPF, editTextSenha, editTextEmail, editTextTelefone;
    private Cliente cliente = null;
    private ClientesDAO conexaoBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_cliente);

        conexaoBanco = new ClientesDAO(getBaseContext());

        editTextNome = findViewById(R.id.nome);
        editTextEmail = findViewById(R.id.email);
        editTextCPF = findViewById(R.id.cpf);
        editTextSenha = findViewById(R.id.senha2);
        editTextTelefone = findViewById(R.id.telefone);

    }

    public void cadastrarCliente(View v){
        // valida dados
        if(!validaForm()){
            return; // para a execução da função
        }
        // capta os dados da tela
        String nome = editTextNome.getText().toString();
        String CPF = editTextCPF.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();
        String telefone = editTextTelefone.getText().toString();

        // condicional para inclusão e edição
        if(cliente == null){ // inclusão
            // instancia o socio
            cliente = new Cliente();
            // insere dados na tabela de socio
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setCPF(CPF);
            cliente.setSenha(senha);
            cliente.setTelefone(telefone);

            // executa operação do banco
            conexaoBanco.inserir(cliente);

            // mensagem para o usuário informando sucesso
            if(cliente.getId() > 0){

                Toast.makeText(getApplicationContext(),
                        "Cliente cadastrado com sucesso",
                        Toast.LENGTH_SHORT).show();

                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Erro ao realizar cadastro",
                        Toast.LENGTH_SHORT).show();
                cliente = null; // ajuda na hora de debug
            }

        }
    }

    public boolean validaForm() {
        String nome = editTextNome.getText().toString();
        String cpf = editTextCPF.getText().toString();
        String senha = editTextSenha.getText().toString();
        String email = editTextEmail.getText().toString();
        String telefone = editTextTelefone.getText().toString();

        if (cpf.isEmpty()) {
            editTextCPF.setError("O campo CPF é obrigatório!");
            editTextCPF.requestFocus();
            return false;
        }

        if (nome.isEmpty()) {
            editTextNome.setError("O campo nome é obrigatório!");
            editTextNome.requestFocus();
            return false;
        }

        if (senha.isEmpty()) {
            editTextSenha.setError("O campo senha é obrigatório!");
            editTextSenha.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("O campo de email é obrigatório!");
            editTextEmail.requestFocus();
            return false;
        }

        if (telefone.isEmpty()) {
            editTextTelefone.setError("O campo de telefone é obrigatório!");
            editTextTelefone.requestFocus();
            return false;
        }

        return true;
    }

    public void voltarIndex(View view) {
        finish();
    }

}
