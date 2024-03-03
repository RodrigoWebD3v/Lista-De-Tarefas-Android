package com.example.applistatarefa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applistatarefa.adapter.TarefaAdapter
import com.example.applistatarefa.database.TarefaDAO
import com.example.applistatarefa.databinding.ActivityMainBinding
import com.example.applistatarefa.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var tarefaAdapter: TarefaAdapter? = null

    private var listaTarefas   = emptyList<Tarefa>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.fabAdicionar.setOnClickListener{
            val intent = Intent(this, AdicionarTarefaActivity::class.java)

            startActivity(intent)
        }

        tarefaAdapter = TarefaAdapter(
            {id -> confirmarExclusao(id)},
            {tarefa -> editar(tarefa)})

        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(this)


    }

    private fun editar(tarefa: Tarefa) {

        val intent = Intent(this, AdicionarTarefaActivity::class.java)

        intent.putExtra("tarefa", tarefa)

        startActivity(intent)


    }

    private fun confirmarExclusao(id: Int) {

        val alertBuilder = AlertDialog.Builder(this)

        Log.i("info_db", "${println(id)}")

        alertBuilder.setTitle("Confirmar exclusao")
        alertBuilder.setMessage("Deseja realmente exluir a tarefa?")

        alertBuilder.setPositiveButton("Sim"){_,_ ->
        val tarefaDAO = TarefaDAO(this)



        if (tarefaDAO.remover(id)){
                Toast.makeText(this, "Sucesso ao remover tarefa", Toast.LENGTH_SHORT).show()
                atualizarListaTarefas()
        }else{
                Toast.makeText(this, "Erro ao remover tarefa", Toast.LENGTH_SHORT).show()
        }

        }

        alertBuilder.setNegativeButton("Nao"){_,_ -> }

        alertBuilder.create().show()

    }

    private fun atualizarListaTarefas(){
        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }
    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }
}