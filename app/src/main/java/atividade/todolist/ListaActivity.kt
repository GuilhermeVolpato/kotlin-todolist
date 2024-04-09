package atividade.todolist

import ItemAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import atividade.todolist.databinding.TeladelistaBinding
import atividade.todolist.room.DatabaseItem
import atividade.todolist.room.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListaActivity : AppCompatActivity() {

    private lateinit var adapter: ItemAdapter
    private lateinit var binding: TeladelistaBinding
    private lateinit var databaseItem: DatabaseItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TeladelistaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseItem = DatabaseItem.getInstance(this)

        setupRecyclerView()
        refreshList()

        binding.fabAddTask.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK)
        }

        binding.btnDeleteTask.setOnClickListener {
            refreshList()
        }
    }

    private fun setupRecyclerView() {
        GlobalScope.launch(Dispatchers.IO) {
            val itemList = databaseItem.toDoItemDao().getAll()
            launch(Dispatchers.Main) {
                adapter = ItemAdapter(itemList.toMutableList()) { item ->
                    onDeleteItem(item)
                }
                binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this@ListaActivity)
                binding.recyclerViewTasks.adapter = adapter

                refreshList()
            }
        }
    }

    private fun onDeleteItem(item: ToDoItem) {
        GlobalScope.launch(Dispatchers.IO) {
            databaseItem.toDoItemDao().delete(item)
            refreshList()
        }
    }

    private fun refreshList() {
        GlobalScope.launch(Dispatchers.IO) {
            val itemList = databaseItem.toDoItemDao().getAll()
            launch(Dispatchers.Main) {
                adapter.itemList = itemList.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_ADD_TASK = 1001
    }
}
