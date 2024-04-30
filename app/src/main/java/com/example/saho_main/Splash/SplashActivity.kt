package com.example.saho_main.Splash

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.saho_main.Database.DatabaseContract
import com.example.saho_main.Database.DatabaseHelper
import com.example.saho_main.Database.DatabaseInitializer
import com.example.saho_main.Database.User
import com.example.saho_main.HomeScreenActivity
import com.example.saho_main.Login.LoginActivity
import com.example.saho_main.R
import com.example.saho_main.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DatabaseHelper(this)

        // Retrieve user data from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UserEntry.COLUMN_NAME_USERNAME, username)
            put(DatabaseContract.UserEntry.COLUMN_NAME_PASSWORD, password)
        }
        db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values)

        Handler().postDelayed({
            val intent = if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, HomeScreenActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 5000)
    }
}
