package com.mobile.dronesurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.mobile.dronesurvey.databinding.ActivityLoginBinding
import com.mobile.dronesurvey.utils.KeyboardUtil
import com.mobile.dronesurvey.utils.ValidationUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KeyboardUtil(this,binding.loginRoot)



        binding.btnLogin.setOnClickListener {
            if(isInputFieldValid()){
                val i = Intent(this, LocationFilterActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i)
            }

        }

    }

    private fun isInputFieldValid(): Boolean {
        if(!ValidationUtil.checkEmailIdValidation(binding.edEmail.text.toString().trim())){
            binding.edEmail.requestFocus()
            binding.llEmail.error =  getString (R.string.enter_valid_emailID)
            return false
        }else if ( binding.edPassword.text.toString().trim().isEmpty()) {
            binding.edPassword.requestFocus()
            binding.llPassword.error = getString(R.string.enter_valid_password)

            return false
        }
        return true
    }
}