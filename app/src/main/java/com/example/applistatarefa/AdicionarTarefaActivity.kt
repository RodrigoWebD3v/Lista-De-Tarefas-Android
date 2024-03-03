package com.example.applistatarefa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.applistatarefa.database.TarefaDAO
import com.example.applistatarefa.databinding.ActivityAdicionarTarefaBinding
import com.example.applistatarefa.databinding.ActivityMainBinding
import com.example.applistatarefa.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        var tarefa : Tarefa? = null
        val bundle = intent.extras
        if(bundle != null){

            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }

        binding.btnSalvar.setOnClickListener{
            if(tarefa != null){
                editar(tarefa)
            }else{
                salvar()
            }
        }


    }

    private fun editar(tarefa: Tarefa) {
        val tarefaDAO : TarefaDAO = TarefaDAO(this)
        val novoTitulo : String = binding.editTarefa.text.toString()
        val novaTarefa : Tarefa = Tarefa(tarefa.idTarefa, novoTitulo,null)
        Log.i("info_db", "${novaTarefa.idTarefa} - ${novaTarefa.descricao} - ${novaTarefa.dataCadastro}")

        if(tarefaDAO.atualizar(novaTarefa)){
            Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Erro ao atualizar tarefa", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listar() {
        TODO("Not yet implemented")
    }

    private fun deletar() {
        TODO("Not yet implemented")
    }

    private fun atualizar() {
        TODO("Not yet implemented")
    }

    private fun salvar() {
        val descricaoTarefa : String = binding.editTarefa.text.toString()

        if(descricaoTarefa.isNotEmpty()){
            val tarefaDAO : TarefaDAO = TarefaDAO(this)
            val tarefa : Tarefa = Tarefa(-1, descricaoTarefa, null)

            if(tarefaDAO.salvar(tarefa)){
                Toast.makeText(this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Erro ao adicionar tarefa", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Preencha o campo descricao", Toast.LENGTH_SHORT).show()
        }


    }
}