package com.mobile.dronesurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.dronesurvey.databinding.ActivityLocationFilterBinding
import com.mobile.dronesurvey.utils.KeyboardUtil

class LocationFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KeyboardUtil(this,binding.llRoot)

    }
}