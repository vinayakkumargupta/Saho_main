package com.example.saho_main.DashBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saho_main.R
import com.example.saho_main.databinding.FragmentFuelBinding


class FuelFragment : Fragment() {
    private lateinit var binding : FragmentFuelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFuelBinding.inflate(inflater,container,false)
        return binding.root
    }

}