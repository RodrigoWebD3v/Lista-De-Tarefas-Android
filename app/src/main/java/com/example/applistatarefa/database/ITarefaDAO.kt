package com.example.applistatarefa.database

import com.example.applistatarefa.model.Tarefa

interface ITarefaDAO {
    fun salvar(tarefa:Tarefa):Boolean
    fun atualizar(tarefa:Tarefa):Boolean
    fun remover(idTarefa: Int):Boolean
    fun listar(): List<Tarefa>
}