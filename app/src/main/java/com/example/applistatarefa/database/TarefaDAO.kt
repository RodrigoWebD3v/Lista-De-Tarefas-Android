package com.example.applistatarefa.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract.Data
import android.util.Log
import com.example.applistatarefa.model.Tarefa
import java.util.Date

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase
    override fun salvar(tarefa: Tarefa): Boolean {

        val valores : ContentValues = ContentValues()

        valores.put(DatabaseHelper.CAMPO_DESCRICAO_TAREFAS, tarefa.descricao)


        try {
           escrita.insert(DatabaseHelper.TABELA_TAREFAS, null, valores )
            Log.i("info_db", "sucesso ao salvar registro")


        }catch (e:Exception){
            Log.i("info_db", "erro ao salvar registro")
            return false
        }

        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {

        val valores : ContentValues = ContentValues()

        valores.put("${DatabaseHelper.CAMPO_DESCRICAO_TAREFAS}", tarefa.descricao)

        val args = arrayOf(tarefa.idTarefa.toString())

        Log.i("info_db", "$valores")
        try {
            escrita.update(DatabaseHelper.TABELA_TAREFAS,valores, "${DatabaseHelper.CAMPO_IDTAREFA_TAREFAS} = ?", args )
            Log.i("info_db", "sucesso ao atualizar registro")


        }catch (e:Exception){
            Log.i("info_db", "erro ao atualizar registro - ${e.message}")
            return false
        }

        return true
    }

    override fun remover(idTarefa: Int): Boolean {

        val args = arrayOf(idTarefa.toString())

        try {
            escrita.delete(DatabaseHelper.TABELA_TAREFAS, "${DatabaseHelper.CAMPO_IDTAREFA_TAREFAS} = ?", args )
            Log.i("info_db", "sucesso ao deletar registro")


        }catch (e:Exception){
            Log.i("info_db", "erro ao deletar registro - ${e.message}")
            return false
        }

        return true
    }

    override fun listar(): List<Tarefa> {

        val listaTarefas = mutableListOf<Tarefa>()

        val sql = "select *  from ${DatabaseHelper.TABELA_TAREFAS}";

        try{
            val cursor : Cursor = leitura.rawQuery(sql, null)

            val indiceId : Int = cursor.getColumnIndex(DatabaseHelper.CAMPO_IDTAREFA_TAREFAS)
            val indiceDescricao : Int = cursor.getColumnIndex(DatabaseHelper.CAMPO_DESCRICAO_TAREFAS)
            val indiceDataHora : Int = cursor.getColumnIndex(DatabaseHelper.CAMPO_DATACADASTRO_TAREFAS)

            while(cursor.moveToNext()){

                val idTarefa : Int = cursor.getInt(indiceId )

                val descricao : String = cursor.getString(indiceDescricao)

                val datahora : String = cursor.getString(indiceDataHora)


                listaTarefas.add(
                    Tarefa(idTarefa, descricao, datahora)
                )
            }
        }catch (e:Exception){
            Log.i("info_db", "${e.message}")
        }


        return listaTarefas

    }
}