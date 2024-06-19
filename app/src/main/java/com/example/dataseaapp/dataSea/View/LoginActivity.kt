package com.example.dataseaapp.dataSea.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dataseaapp.dataSea.Model.DbUsersHelper
import com.example.dataseaapp.databinding.ActivityLoginBinding
import com.example.dataseaapp.databinding.ActivitySignupBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbUsersHelper: DbUsersHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbUsersHelper = DbUsersHelper(this)

        binding.enterButton.setOnClickListener {
            val signupUsername = binding.entLoginText.text.toString()
            val signupPassword = binding.entPasswordText.text.toString()
            loginDatabase(signupUsername, signupPassword)
        }

        binding.regUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginDatabase(username: String, password: String) {
        val userExists = dbUsersHelper.readUser(username, password)
        if (userExists){
            Toast.makeText(this, "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
        }
    }
}