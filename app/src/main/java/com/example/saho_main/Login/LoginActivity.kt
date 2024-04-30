package com.example.saho_main.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saho_main.HomeScreenActivity
import com.example.saho_main.R

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var signup: TextView

    private val validUsername = "admin"
    private val validPassword = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
//        signup = findViewById(R.id.txtsignuptv)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (isValidCredentials(username, password)) {
                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()
            } else {

                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val storedUsername = sharedPreferences.getString("username", "")
        val storedPassword = sharedPreferences.getString("password", "")

        return (username == storedUsername && password == storedPassword) ||
                (username == validUsername && password == validPassword)
    }

    fun MovetoRegister(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}