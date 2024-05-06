package com.example.dataseaapp.dataSea

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dataseaapp.R
import com.example.dataseaapp.dataSea.Model.DbHelper
import com.example.dataseaapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: DbHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitle.setText(note.title)
        binding.updateContent.setText(note.content)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitle.text.toString()
            val newContent = binding.updateContent.text.toString()
            val updatedNote = Note(noteId, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show()
        }
    }
}