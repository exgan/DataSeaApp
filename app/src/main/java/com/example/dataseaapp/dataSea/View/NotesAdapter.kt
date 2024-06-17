package com.example.dataseaapp.dataSea.View

import android.widget.TextView
import com.example.dataseaapp.R
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dataseaapp.dataSea.Model.DbHelper
import com.example.dataseaapp.dataSea.Note

class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView : TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton : Button = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
        val timeTextView : TextView = itemView.findViewById(R.id.timeTextView)
        val notesPriorityView : View = itemView.findViewById(R.id.notesPriorityView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
        holder.timeTextView.text = note.time
        if (note.priority == "1") {
            holder.notesPriorityView.setBackgroundResource(R.drawable.red_shape)
        } else if (note.priority == "2") {
            holder.notesPriorityView.setBackgroundResource(R.drawable.green_shape)
        } else if (note.priority == "3") {
            holder.notesPriorityView.setBackgroundResource(R.drawable.yellow_shape)
        } else if (note.priority == "4") {
            holder.notesPriorityView.setBackgroundResource(R.drawable.gray_shape)
        }

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Заметка выполнена", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes:List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }

    private val db: DbHelper = DbHelper(context)
}