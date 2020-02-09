package com.darotapp.meetupapp.network

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface AuthRegisterCall {
    suspend fun registerUser(fullName:String, email:String, password:String, context: Context): LiveData<String> {
        val go = MutableLiveData<String>()
        return go
    }
}