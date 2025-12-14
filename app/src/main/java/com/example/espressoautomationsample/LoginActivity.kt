package com.example.espressoautomationsample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val tvError = findViewById<TextView>(R.id.tvError)

        btnSignIn.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username == "demo" && password == "password") {
                tvError.visibility = View.GONE
                startActivity(Intent(this, ItemsActivity::class.java))
                finish()
            } else {
                tvError.visibility = View.VISIBLE
            }
        }
    }
}
