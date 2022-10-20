package com.mobile.dronesurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.mobile.dronesurvey.databinding.ActivityLoginBinding
import com.mobile.dronesurvey.utils.KeyboardUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KeyboardUtil(this,binding.loginRoot)

        binding.btnLogin.setOnClickListener {
            val i = Intent(this, LocationFilterActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i)
        }

    }
}