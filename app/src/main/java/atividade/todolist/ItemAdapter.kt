import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import atividade.todolist.R
import atividade.todolist.room.ToDoItem

class ItemAdapter(var itemList: MutableList<ToDoItem>, private val onDeleteClickListener: (ToDoItem) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.teladelista, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.btnDeleteTask)

        fun bind(item: ToDoItem) {
            descriptionTextView.text = item.description
            deleteButton.setOnClickListener { onDeleteClickListener(item) }
        }
    }
}