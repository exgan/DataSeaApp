package com.example.dataseaapp.dataSea.View

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.example.dataseaapp.R
import com.example.dataseaapp.dataSea.AlarmReceiver
import com.example.dataseaapp.dataSea.Model.DbHelper
import com.example.dataseaapp.dataSea.Note
import com.example.dataseaapp.databinding.ActivityCreateBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

    private var priority = "1"

    private lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var calendar: Calendar

    private lateinit var binding: ActivityCreateBinding
    private lateinit var db: DbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        db = DbHelper(this)


        binding.buttonSave.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val content = binding.edContent.text.toString()
            val time = binding.tvTextTime.text.toString()
            val note = Note(0, title, content, time, priority)
            db.insertToDb(note)
            finish()
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
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

        binding.alarmButtonSelectTime.setOnClickListener {
            showTimePicker()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "TaskMaster"
            val description = "Канал для уведомлений"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("TaskMaster", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Выберете время для уведомления")
            .build()

        picker.show(supportFragmentManager, "TaskMaster")

        picker.addOnPositiveButtonClickListener {
            if (hour > 12){
                binding.tvAlarmTime.text = "${picker.hour - 12} :  ${picker.minute}"
            } else {
                binding.tvAlarmTime.text = "${picker.hour} : ${picker.minute}"
            }

            val cal = Calendar.getInstance()
            cal[Calendar.HOUR_OF_DAY] = picker.hour
            cal[Calendar.MINUTE] = picker.minute
            cal[Calendar.SECOND] = 0
            cal[Calendar.MILLISECOND] = 0
        }
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
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear, 0$savedHour:0$savedMinute"
        } else if (minute in numbersForMinutes) {
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear, $savedHour:0$savedMinute"
        } else if (hourOfDay in numbersForHours) {
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear, 0$savedHour:$savedMinute"
        } else {
            binding.tvTextTime.text = "$savedDay.$savedMonth.$savedYear, $savedHour:$savedMinute"
        }
    }
}