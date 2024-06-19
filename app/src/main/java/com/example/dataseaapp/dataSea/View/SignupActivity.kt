package com.example.dataseaapp.dataSea.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dataseaapp.dataSea.Model.DbUsersHelper
import com.example.dataseaapp.databinding.ActivitySignupBinding

class SignupActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var dbUsersHelper: DbUsersHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbUsersHelper = DbUsersHelper(this)

        binding.regSaveButton.setOnClickListener {
            val signupUsername = binding.regLoginText.text.toString()
            val signupPassword = binding.regPasswordText.text.toString()
            signupDatabase(signupUsername, signupPassword)
        }

        binding.loginUpButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signupDatabase(username: String, password: String) {
        val insertedRowId = dbUsersHelper.insertUsers(username, password)
        if (insertedRowId != -1L){
            Toast.makeText(this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Регистрация не прошла успешно", Toast.LENGTH_SHORT).show()
        }
    }
}