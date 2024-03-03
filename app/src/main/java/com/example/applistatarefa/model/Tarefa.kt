package com.example.applistatarefa.model

import java.io.Serializable

class Tarefa(
    val idTarefa:Int,
    val descricao: String,
    val dataCadastro: String?
):Serializable