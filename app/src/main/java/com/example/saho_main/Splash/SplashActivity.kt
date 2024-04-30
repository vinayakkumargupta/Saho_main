package com.example.saho_main.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.saho_main.Login.LoginActivity
import com.example.saho_main.R
import com.example.saho_main.databinding.ActivitySplashBinding


 class SplashActivity : AppCompatActivity() {


         private lateinit var binding: ActivitySplashBinding

         override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)

             binding = ActivitySplashBinding.inflate(layoutInflater)
             setContentView(binding.root)

//             val animationView: LottieAnimationView = binding.animationView
//             animationView.setAnimation(R.raw.ic_splash)
//             animationView.playAnimation()

             Handler().postDelayed({
                 val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                 finish()
             }, 5000)
         }

}