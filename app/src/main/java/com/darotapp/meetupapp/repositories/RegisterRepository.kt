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
import com.darotapp.meetupapp.model.ErrorData
import com.darotapp.meetupapp.model.UserData
import com.darotapp.meetupapp.network.AuthRegisterCall
import com.darotapp.meetupapp.network.VolleySingleton
import org.json.JSONObject
import java.lang.Exception

class RegisterRepository(application: Application):AuthRegisterCall {
    override suspend fun registerUser(
        fullName: String,
        email: String,
        password: String,
        context: Context
    ): LiveData<String> {
        val registerResponse = MutableLiveData<String>()

        val url = "https://reqres.in/api/register"
        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password



        val jsonObject = JSONObject(params as Map<*, *>)

//                Toast.makeText(context!!.applicationContext, "Data $country", Toast.LENGTH_SHORT).show()
//                    Log.e("Registration", "Response $jsonObject")
        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { response ->

                try{
//                                Log.i("Registration", "Response $response")
                    val token = response.getString("token")
                    val id = response.getInt("id").toString()


                    if(token.isNotEmpty()){

                        val message = "You have successfully registered"


                        val user = UserData(fullName, email, password)
                        user.message = message
                        user.token += token
                        user.id += id
                        val userDetails = SharedPrefManager.saveData(context, user, user.email)
                        registerResponse.value = userDetails[0]

                    }


                }catch (e: Exception){
//                    val sharedPrefs = context.getSharedPreferences("secret", Context.MODE_PRIVATE)!!
//
//                    val message = response.getString("message")
//                    TOKEN += sharedPrefs.getString("TOKEN", "");
//                    val user = UserData(fullName,phone, pin)
//                    user.password = password
//                    user.message = message
//                    val userDetails = SharedPrefManager.saveData(context, user)
//                    registerResponse.value = userDetails[0]
//

                }
            }, Response.ErrorListener { error ->


                val errorMessage=  VolleyErrorHandler.instance.erroHandler(error, context)
                val errorString = SharedPrefManager.getError(context, errorMessage)
                val user = UserData(fullName, email, password)
                if (errorString != null) {
                    user.message =errorString.error
                }
                user.error = true
                val data = SharedPrefManager.saveData(context, user, null)
                registerResponse.value = data[0]


            })




        VolleySingleton.getInstance(context.applicationContext).addToRequestQueue(request)

        return registerResponse

    }

}