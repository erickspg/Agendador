package com.example.agendador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClientesDAO {

    // costante com nome das classes
    public static String NOME_TABELA_CLIENTES = "clientes";

    private SQLiteDatabase banco;

    public ClientesDAO(Context context){
        Banco newBanco = new Banco(context);
        banco = newBanco.getWritableDatabase();
    }

    public void inserir(Cliente cliente){
        // configura os parâmetros a serem enviados - coluna e valor
        ContentValues cv = new ContentValues();
        cv.put("nome", cliente.getNome());
        cv.put("cpf", cliente.getCPF());
        cv.put("email", cliente.getEmail());
        cv.put("senha", String.valueOf(cliente.getSenha()));
        cv.put("telefone",cliente.getTelefone());

        Long idGerado = banco.insert(
                NOME_TABELA_CLIENTES, null, cv);
        cliente.setId(idGerado.intValue());
    }

    public int atualizar(Cliente cliente){
        // configura os parâmetros a serem atualizados - coluna e valor
        ContentValues cv = new ContentValues();
        cv.put("nome", cliente.getNome());
        cv.put("cpf", cliente.getCPF());
        cv.put("email", cliente.getEmail());
        cv.put("senha", String.valueOf(cliente.getSenha()));
        cv.put("telefone", cliente.getTelefone());

        // parâmetros WHERE da função de alteração
        final String WHERE          = "id = ?";
        final String [] WHERE_PARAM = {String.valueOf(cliente.getId())};

        // modifica a tabela montando um SQL equivalente a:
        /* UPDATE INTO socios SET nome = ?, cpf = ?, email = ?,
                              senha = ?, dataPagamento = ?
           WHERE _id = ? ; */
        int linhasAfetadas = banco.update(
                NOME_TABELA_CLIENTES, // 1 - tabela
                cv,                  // 2 - valores a serem atualizados
                WHERE,               // 3 - parte WHERE do SQL
                WHERE_PARAM);        // 4 - valores da parte WHERE do SQL
        // retorna a quantidade de linhas afetadas pelo update
        return linhasAfetadas;
    }

    public String existe(String cpf, String senha){

        String retorno ="";
        // parâmetros da função query
        final String TABELA = NOME_TABELA_CLIENTES;
        final String[] COLUNAS = {"id"};
        final String ORDER_BY = "nome ASC";
        final String WHERE = "cpf = ? and senha = ?";
        final String[] WHERE_PARAM = {String.valueOf(cpf), String.valueOf(senha)};
        final String GROUP_BY = null;
        final String HAVING = null;

        Cursor cursor = banco.query(
                TABELA,     // 1 - nome da tabela
                COLUNAS,    // 2 - vetor com nome das colunas
                WHERE,      // 3 - parte WHERE do SQL
                WHERE_PARAM,// 4 - valores da parte WHERE do SELECT
                GROUP_BY,   // 5 - parte GROUP BY do SQL
                HAVING,     // 6 - parte HAVING do SQL
                ORDER_BY);  // 7 - parte ORDER BY do SQL

        while (cursor.moveToNext()) {
            // Obtem o valor das colunas
            retorno = cursor.getString(0);  // id
        }

        return retorno;
    }

    public String retornaNome(int id){

        String retorno ="";
        // parâmetros da função query
        final String TABELA = NOME_TABELA_CLIENTES;
        final String[] COLUNAS = {"nome"};
        final String ORDER_BY = "nome ASC";
        final String WHERE = "id = ?";
        final String[] WHERE_PARAM = {String.valueOf(id)};
        final String GROUP_BY = null;
        final String HAVING = null;

        Cursor cursor = banco.query(
                TABELA,     // 1 - nome da tabela
                COLUNAS,    // 2 - vetor com nome das colunas
                WHERE,      // 3 - parte WHERE do SQL
                WHERE_PARAM,// 4 - valores da parte WHERE do SELECT
                GROUP_BY,   // 5 - parte GROUP BY do SQL
                HAVING,     // 6 - parte HAVING do SQL
                ORDER_BY);  // 7 - parte ORDER BY do SQL

        while (cursor.moveToNext()) {
            // Obtem o valor das colunas
            retorno = cursor.getString(0);  // nome
        }

        return retorno;
    }

    public Cliente retornaCliente(int id) {

        Cliente cliente = null;

        // parâmetros da função query
        final String TABELA = NOME_TABELA_CLIENTES;
        final String[] COLUNAS = {"id", "nome",  "cpf", "email", "senha", "telefone"};
        final String WHERE = "id = ? ";
        final String[] WHERE_PARAM = {String.valueOf(id)};
        final String GROUP_BY = null;
        final String HAVING = null;
        final String ORDER_BY = "nome ASC";
        final String LIMIT = null;


        Cursor cursor = banco.query(
                TABELA,     // 1 - nome da tabela
                COLUNAS,    // 2 - vetor com nome das colunas
                WHERE,      // 3 - parte WHERE do SQL
                WHERE_PARAM,// 4 - valores da parte WHERE do SELECT
                GROUP_BY,   // 5 - parte GROUP BY do SQL
                HAVING,     // 6 - parte HAVING do SQL
                ORDER_BY,   // 7 - parte ORDER BY do SQL
                LIMIT);     // 8 - parte LIMIT do SQL

        while (cursor.moveToNext()) {
            // Obtem o valor das colunas
            int idCliente = cursor.getInt(0);
            String nome = cursor.getString(1);
            String cpf = cursor.getString(2);
            String email = cursor.getString(3);
            String senha = cursor.getString(4);
            String telefone = cursor.getString(5);


            cliente = new Cliente();

            cliente.setId(idCliente);
            cliente.setNome(nome);
            cliente.setCPF(cpf);
            cliente.setEmail(email);
            cliente.setSenha(senha);
            cliente.setTelefone(telefone);

        }

        return cliente;
    }

    public void excluir(int id){
        // parâmetros WHERE da consulta DELETE
        final String WHERE          = "id = ?";
        final String [] WHERE_PARAM = {String.valueOf(id)};

        // exclui um cliente pelo id.
        banco.delete(
                NOME_TABELA_CLIENTES,    // 1 - tabela
                WHERE,                  // 2 - parte WHERE do SQL
                WHERE_PARAM);           // 3 - valores da parte WHERE do SQL
    }

    public void fechaConexaoBanco(){
        // libera o banco
        banco.close();
    }
}
