package atividade.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import atividade.todolist.databinding.TeladecadastroBinding
import atividade.todolist.room.DatabaseItem
import atividade.todolist.room.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroActivity : AppCompatActivity() {
    private lateinit var databaseItem: DatabaseItem
    private lateinit var binding: TeladecadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TeladecadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseItem = DatabaseItem.getInstance(this)


        binding.btnSaveTask.setOnClickListener {
            val description = binding.etTaskDescription.text.toString()

            val newItem = ToDoItem(description = description)
            Log.d("CadastroItemActivity", "Item a ser salvo: $newItem")

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    databaseItem.toDoItemDao().insertAll(newItem)
                    Log.d("CadastroItemActivity", "Item salvo com sucesso")

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@CadastroActivity, "Item salvo com sucesso: $description", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Log.e("CadastroItemActivity", "Erro ao salvar item: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@CadastroActivity, "Erro ao salvar item: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            setResult(RESULT_OK)
            finish()
        }




        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initDatabase() {
        var db = Room.databaseBuilder(
            applicationContext,
            DatabaseItem::class.java, "database-todolist"
        )
            .allowMainThreadQueries()
            .build()
    }
}
