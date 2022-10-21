package com.mobile.dronesurvey

import android.content.Intent
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

        binding.btnInitialize.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i)
        }

    }
}