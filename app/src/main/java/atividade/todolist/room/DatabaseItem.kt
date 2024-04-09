package atividade.todolist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItem::class], version = 1)
abstract class DatabaseItem : RoomDatabase() {
    abstract fun toDoItemDao(): ToDoItemDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseItem? = null

        fun getInstance(context: Context): DatabaseItem {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseItem::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
