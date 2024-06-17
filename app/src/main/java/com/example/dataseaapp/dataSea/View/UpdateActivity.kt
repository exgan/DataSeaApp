package com.example.dataseaapp.dataSea.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dataseaapp.R
import com.example.dataseaapp.dataSea.Model.DbHelper
import com.example.dataseaapp.dataSea.Note
import com.example.dataseaapp.databinding.ActivityUpdateBinding
import java.util.Calendar

class UpdateActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0
    private val numbersForMinutes: Array<Int> = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val numbersForHours: Array<Int> = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    private var priority = "1"

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
        binding.tvUpdateTextTime.setText(note.time)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitle.text.toString()
            val newContent = binding.updateContent.text.toString()
            val newTime = binding.tvUpdateTextTime.text.toString()
            val updatedNote = Note(noteId, newTitle, newContent, newTime, priority)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show()
        }

        binding.ButtonCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.redPriority.setOnClickListener {
            binding.redPriority.setImageResource(R.drawable.baseline_done_24)
            binding.greenPriority.setImageResource(0)
            binding.yellowPriority.setImageResource(0)
            binding.grayPriority.setImageResource(0)
            priority = "1"
        }

        binding.greenPriority.setOnClickListener {
            binding.redPriority.setImageResource(0)
            binding.greenPriority.setImageResource(R.drawable.baseline_done_24)
            binding.yellowPriority.setImageResource(0)
            binding.grayPriority.setImageResource(0)
            priority = "2"
        }

        binding.yellowPriority.setOnClickListener {
            binding.redPriority.setImageResource(0)
            binding.greenPriority.setImageResource(0)
            binding.yellowPriority.setImageResource(R.drawable.baseline_done_24)
            binding.grayPriority.setImageResource(0)
            priority = "3"
        }

        binding.grayPriority.setOnClickListener {
            binding.redPriority.setImageResource(0)
            binding.greenPriority.setImageResource(0)
            binding.yellowPriority.setImageResource(0)
            binding.grayPriority.setImageResource(R.drawable.baseline_done_24)
            priority = "4"
        }

        pickDate()
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        binding.updateTimeButton.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, dayOfMonth: Int, month: Int, year: Int) {
        savedDay = dayOfMonth
        savedMonth = month+1
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        if ((minute in numbersForMinutes) and (hourOfDay in numbersForHours)) {
            binding.tvUpdateTextTime.text = "$savedDay.$savedMonth.$savedYear , 0$savedHour:0$savedMinute"
        } else if (minute in numbersForMinutes) {
            binding.tvUpdateTextTime.text = "$savedDay.$savedMonth.$savedYear , $savedHour:0$savedMinute"
        } else if (hourOfDay in numbersForHours) {
            binding.tvUpdateTextTime.text = "$savedDay.$savedMonth.$savedYear , 0$savedHour:$savedMinute"
        } else {
            binding.tvUpdateTextTime.text = "$savedDay.$savedMonth.$savedYear , $savedHour:$savedMinute"
        }
    }
}