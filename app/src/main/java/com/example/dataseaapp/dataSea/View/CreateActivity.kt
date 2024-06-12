package com.example.dataseaapp.dataSea.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dataseaapp.R
import com.example.dataseaapp.dataSea.Model.DbHelper
import com.example.dataseaapp.dataSea.Note
import com.example.dataseaapp.dataSea.NotesAdapter
import com.example.dataseaapp.databinding.ActivityCreateBinding
import java.util.Calendar


class CreateActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    private lateinit var binding: ActivityCreateBinding
    private lateinit var db: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)


        binding.buttonSave.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val content = binding.edContent.text.toString()
            val time = binding.tvTextTime.text.toString()
            val note = Note(0, title, content, time)
            db.insertToDb(note)
            finish()
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
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
        binding.buttonTime.setOnClickListener {
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
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear , 0$savedHour:0$savedMinute"
        } else if (minute in numbersForMinutes) {
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear , $savedHour:0$savedMinute"
        } else if (hourOfDay in numbersForHours) {
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear , 0$savedHour:$savedMinute"
        } else {
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear , $savedHour:$savedMinute"
        }


    }
}