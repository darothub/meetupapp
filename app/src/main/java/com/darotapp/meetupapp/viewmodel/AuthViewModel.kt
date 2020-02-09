package com.darotapp.meetupapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.darotapp.meetupapp.repositories.RegisterRepository

class AuthViewModel(application: Application):AndroidViewModel(application) {

    private var registerRepository = RegisterRepository(application)
    suspend fun registerUser(fullName:String, email: String, pin:String, context: Context): LiveData<String> {
        return registerRepository.registerUser(fullName, email, pin, context)
    }
}