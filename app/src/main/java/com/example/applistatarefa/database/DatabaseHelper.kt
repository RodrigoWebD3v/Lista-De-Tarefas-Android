package com.example.applistatarefa.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, NOME_BANCO_DADOS,null, VERSAO ) {

    companion object{
        const val NOME_BANCO_DADOS = "ListaTarefas.db"
        const val VERSAO = 3
        const val TABELA_TAREFAS = "tarefas"
        const val CAMPO_DESCRICAO_TAREFAS = "descricao"
        const val CAMPO_IDTAREFA_TAREFAS = "id_tarefa"
        const val CAMPO_DATACADASTRO_TAREFAS = "data_cadastro"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql : String = "CREATE TABLE IF NOT EXISTS $TABELA_TAREFAS(" +
                "$CAMPO_IDTAREFA_TAREFAS  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$CAMPO_DESCRICAO_TAREFAS VARCHAR(70)," +
                "$CAMPO_DATACADASTRO_TAREFAS DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "sucesso ao criar o banco de dados")
        }catch (e:Exception){
            Log.i("info_db", "erro ao criar banco de dados - ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("info_db", "onUpgrade executado")
    }
}