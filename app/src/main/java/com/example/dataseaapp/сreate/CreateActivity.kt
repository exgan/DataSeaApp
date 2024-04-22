package com.example.dataseaapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dataseaapp.R
import com.example.dataseaapp.сreate.Model.DbManager


class CreateActivity : AppCompatActivity() {

    val DbManager = DbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }

    fun onClickSave(view: View) {
        val tv:TextView = findViewById(R.id.tv)
        val edTitle:TextView = findViewById(R.id.edTitle)
        val edContent:TextView = findViewById(R.id.edContent)
        tv.text = ""
        DbManager.openDb()
        DbManager.insertToDb(edTitle.text.toString(), edContent.text.toString())
        val dataList = DbManager.readDbData()
        for (item in dataList){
            tv.append(item)
            tv.append("\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DbManager.closeDb()
    }
}