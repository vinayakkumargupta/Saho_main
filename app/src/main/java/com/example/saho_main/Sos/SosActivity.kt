package com.example.saho_main.Sos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saho_main.databinding.ActivitySosBinding

class SosActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
    }

    fun setUpUi(){
        with(binding){
            title.text = "SOS"
            binding.title.setOnClickListener {
                onBackPressed()
            }
            Ambulance.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:102"))
                startActivity(intent)
            }
            policeBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:112"))
                startActivity(intent)
            }
            womenBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:1091"))
                startActivity(intent)
            }
            Firebtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:101"))
                startActivity(intent)
            }
        }

    }
}