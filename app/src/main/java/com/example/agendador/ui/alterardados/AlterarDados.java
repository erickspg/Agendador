package com.example.agendador.ui.alterardados;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.agendador.Cliente;
import com.example.agendador.ClientesDAO;
import com.example.agendador.R;

public class AlterarDados extends Fragment {

    private EditText editTextNome, editTextCPF, editTextSenha, editTextEmail, editTextTelefone;
    private Button btnAlterar;
    private Cliente cliente = null;
    private static final int PERMISSION_SEND_SMS = 123;

    // objeto para manipular dados
    private ClientesDAO conexaoBanco;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_alterardados, container, false);

        conexaoBanco = new ClientesDAO(getActivity().getBaseContext());
        editTextNome = root.findViewById(R.id.nomeAlt);
        editTextEmail = root.findViewById(R.id.emailAlt);
        editTextCPF = root.findViewById(R.id.cpfAlt);
        editTextSenha = root.findViewById(R.id.senha2Alt);
        editTextTelefone = root.findViewById(R.id.telefoneAlt);
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
        editTextTelefone.setText(cliente.getTelefone());

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
        String telefone = editTextTelefone.getText().toString();

        // altera dados na tabela
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setCPF(CPF);
        cliente.setSenha(senha);
        cliente.setTelefone(telefone);

        // executa operação do banco
        int linhas = conexaoBanco.atualizar(cliente);

        // mensagem para o usuário informando sucesso
        if(linhas > 0){
            Log.e("update", "update realizado");
            Toast.makeText(getActivity(), "Dados alterados com sucesso",
                    Toast.LENGTH_SHORT).show();

             permissaoSMS(); //função pra o user aceitar permissao de envio de SMS

        } else {
            Toast.makeText(getActivity(),
                    "Erro ao alterar dados",
                    Toast.LENGTH_SHORT).show();
            cliente = null; // ajuda na hora de debug
        }



    }
    private void permissaoSMS() {

        String numero = editTextTelefone.getText().toString();
        String telefone;

        telefone = "+55"+numero;
        // check permission is given
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);

            enviaSMS(telefone, "Organeasy: Os seus dados cadastrais foram alterados");
        }else{
            Toast.makeText(getActivity().getApplicationContext(),
                    "Necessário permissão para o envio de SMS",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void enviaSMS(String phoneNumber, String message) {

        //Log.e("SMSmethod", "entrei no metodo de envio SMS");

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);

            Toast.makeText(getActivity(), "SMS enviada",
                    Toast.LENGTH_SHORT).show();

             //Log.e("SMS", "sms enviada com sucesso");
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Falha no envio da SMS",
                    Toast.LENGTH_LONG).show();
             Log.e("SMSError", e.getMessage());
            e.printStackTrace();
        }
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
