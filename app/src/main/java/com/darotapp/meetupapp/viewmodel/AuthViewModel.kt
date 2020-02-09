package com.darotapp.meetupapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.darotapp.meetupapp.repositories.UserRegisterRepository
import com.darotapp.meetupapp.repositories.UserSigninRepository

class AuthViewModel(application: Application):AndroidViewModel(application) {

    private var registerRepository = UserRegisterRepository(application)
    private var userSigninRepository =
        UserSigninRepository(application)
    suspend fun registerUser(fullName:String, email: String, pin:String, context: Context): LiveData<String> {
        return registerRepository.registerUser(fullName, email, pin, context)
    }

    suspend fun userLogin(email:String, password:String, context: Context):LiveData<String>{
        return userSigninRepository.login(email, password, context)
    }
}