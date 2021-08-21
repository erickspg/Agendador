package com.example.agendador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    // 1 - nome do banco de dados
    private final static String NOME_BANCO ="DB01";
    // 2 - versão mais atual da minha APP
    private final static int VERSAO_APP = 6;


    // criar construtor
    public Banco(Context activityEmExecucao){
        super(activityEmExecucao, // 1 - contexto da Activity
                NOME_BANCO, // 2 - nome do banco de dados
                null,    // 3 - cursor customizado (usar null)
                VERSAO_APP);// 4 - versão atual do banco
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // função para criação da tabela no banco de dados

        db.execSQL("CREATE TABLE clientes (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT, " +
                " cpf TEXT, " +
                " email TEXT, " +
                " senha TEXT, " +
                " telefone TEXT " +
                " );");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // função executada nas mudanças de versão

        db.execSQL("DROP TABLE IF EXISTS clientes;");
        onCreate(db);
    }


}
