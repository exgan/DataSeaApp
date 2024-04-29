package com.example.dataseaapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dataseaapp.R
import com.example.dataseaapp.dataSea.Model.DbHelper
import com.example.dataseaapp.dataSea.Note
import com.example.dataseaapp.databinding.ActivityCreateBinding


class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private lateinit var db: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        binding.button.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val content = binding.edContent.text.toString()
            val note = Note(0, title, content)
            db.insertToDb(note)
            finish()
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
        }
    }
}