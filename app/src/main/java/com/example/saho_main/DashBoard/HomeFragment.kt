package com.example.saho_main.DashBoard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saho_main.Emergency.EmergencyActivity
import com.example.saho_main.NearBy.MainActivity
import com.example.saho_main.Quickshare.QuickshareActivity
import com.example.saho_main.Sos.SosActivity
import com.example.saho_main.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        SetUpUi()
        return binding.root

    }
    fun SetUpUi() {
        with(binding) {

            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")
            fullNametv.text = username
            val initial = username?.take(1)
            nameTv.text = initial
            cardView2.setOnClickListener {
                val intent = Intent(requireContext(), SosActivity::class.java)
                startActivity(intent)
            }
            cardView3.setOnClickListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
            cardView4.setOnClickListener {
                val intent = Intent(requireContext(), QuickshareActivity::class.java)
                startActivity(intent)
            }
            cardView5.setOnClickListener {
                val intent = Intent(requireContext(), EmergencyActivity::class.java)
                startActivity(intent)
            }

        }
    }

}