package com.app.andromedical3a.administrationModulo

import androidx.lifecycle.ViewModel

class LoginAdministrationViewModel : ViewModel() {

    var password = "admin"

    fun checkpassword(password: String): Boolean {
        if (password==(this.password))
            return true;

     return false;
    }

}