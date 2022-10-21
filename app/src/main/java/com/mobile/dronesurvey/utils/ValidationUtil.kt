package com.mobile.dronesurvey.utils

import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

object ValidationUtil {

    fun checkMobileValidation(mobileNo:String,mobileNoLength:Int): Boolean {
        if (mobileNo.isEmpty() || mobileNo.length != mobileNoLength) {
            return false;
        }
        return android.util.Patterns.PHONE.matcher(mobileNo).matches();
    }

    fun checkEmailIdValidation(emailId: String): Boolean {
        if (emailId.isEmpty()) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
    }


}