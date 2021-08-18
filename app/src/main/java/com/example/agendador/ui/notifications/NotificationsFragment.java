package com.example.agendador.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.agendador.Cliente;
import com.example.agendador.ClientesDAO;
import com.example.agendador.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private EditText editTextNome, editTextCPF, editTextSenha, editTextEmail;
    private Button btnAlterar;
    private Cliente cliente = null;

    // objeto para manipular dados
    private ClientesDAO conexaoBanco;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        conexaoBanco = new ClientesDAO(getActivity().getBaseContext());
        editTextNome = root.findViewById(R.id.nomeAlt);
        editTextEmail = root.findViewById(R.id.emailAlt);
        editTextCPF = root.findViewById(R.id.cpfAlt);
        editTextSenha = root.findViewById(R.id.senha2Alt);
        btnAlterar = root.findViewById(R.id.alterar);

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarDados(view);
            }
        });

        SharedPreferences preferencias = getActivity().getSharedPreferences("user_preferences", getActivity().MODE_PRIVATE);
        String idUser = preferencias.getString( "login", "idUser");
        Integer id = Integer.parseInt(idUser);

        Cliente clientealterado = cliente;
        cliente = conexaoBanco.retornaCliente(id);

        editTextNome.setText(cliente.getNome());
        editTextEmail.setText(cliente.getEmail());
        editTextCPF.setText(cliente.getCPF());
        editTextSenha.setText(cliente.getSenha());

//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //textView.setText(s);
//            }
//
//        });

        return root;
    }


    public void alterarDados( View v){

        //valida dados
        if(!validaForm()){
            return; // para a execução da função
        }
        // capta os dados da tela
        String nome = editTextNome.getText().toString();
        String CPF = editTextCPF.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        // altera dados na tabela
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setCPF(CPF);
        cliente.setSenha(senha);

        // executa operação do banco
        int linhas = conexaoBanco.atualizar(cliente);

        // mensagem para o usuário informando sucesso
        if(linhas > 0){

            Toast.makeText(getActivity().getApplicationContext(), "Dados alterados com sucesso",
                    Toast.LENGTH_SHORT).show();

             //getActivity().finish();

        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Erro ao alterar dados",
                    Toast.LENGTH_SHORT).show();
            cliente = null; // ajuda na hora de debug
        }

        //}
    }


    public boolean validaForm() {
        String nome = editTextNome.getText().toString();
        String cpf = editTextCPF.getText().toString();
        String senha = editTextSenha.getText().toString();
        String email = editTextEmail.getText().toString();

        if (nome.isEmpty()) {
            editTextNome.setError("O campo nome é obrigatório!");
            editTextNome.requestFocus();
            return false;
        }

        if (cpf.isEmpty()) {
            editTextCPF.setError("O campo CPF é obrigatório!");
            editTextCPF.requestFocus();
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

        return true;
    }
}
