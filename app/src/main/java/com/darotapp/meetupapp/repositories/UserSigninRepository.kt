package com.darotapp.meetupapp.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.darotapp.meetupapp.helper.SharedPrefManager
import com.darotapp.meetupapp.helper.VolleyErrorHandler
import com.darotapp.meetupapp.model.UserData
import com.darotapp.meetupapp.network.AuthRequest
import com.darotapp.meetupapp.network.VolleySingleton
import org.json.JSONObject
import java.lang.Exception

class UserSigninRepository(application: Application):AuthRequest {
    override suspend fun login(
        email: String,
        password: String,
        context: Context
    ): LiveData<String> {
        val signinResponse = MutableLiveData<String>()

        val url = "https://reqres.in/api/login"
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password



        val jsonObject = JSONObject(params as Map<*, *>)


        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { response ->

                try{
                    val token = response.getString("token")


                    if(token.isNotEmpty()){

                        val message = "Login successful"


                        val user = UserData(null, email, password)
                        user.message = message
                        user.loggedIn = true
                        user.token += token
//                        user.id += "$id"
                        val userDetails = SharedPrefManager.saveData(context, user, user.email)
                        signinResponse.value = userDetails[0]

                    }


                }catch (e: Exception){
                    val user = UserData(null, email, password)
                    user.message = e.message.toString()
                    val userDetails = SharedPrefManager.saveData(context, user, user.email)
                    signinResponse.value = userDetails[0]


                }
            }, Response.ErrorListener { error ->


                val errorMessage=  VolleyErrorHandler.instance.erroHandler(error, context)
                val errorString = SharedPrefManager.getError(context, errorMessage)
                val userData = UserData(null, email, password)
                if (errorString != null) {
                    userData.message =errorString.error
                }
                userData.error = true
                val data = SharedPrefManager.saveData(context, userData, null)
                signinResponse.value = data[0]


            })




        VolleySingleton.getInstance(context.applicationContext).addToRequestQueue(request)

        return signinResponse

    }
}