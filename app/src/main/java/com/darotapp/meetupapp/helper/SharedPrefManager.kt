package com.darotapp.meetupapp.helper

import android.content.Context
import com.darotapp.meetupapp.model.ErrorData
import com.darotapp.meetupapp.model.UserData
import com.google.gson.GsonBuilder

class SharedPrefManager(val context: Context?) {
    companion object{
        fun clearShared(context: Context?){
            val sharedPrefs = context?.getSharedPreferences("secret", Context.MODE_PRIVATE)!!
            sharedPrefs.edit()
                .apply {
                    clear()
                    apply()
                }
        }

        fun saveData(context: Context?, user:UserData?, key:String?=null):ArrayList<String>{
            val result:ArrayList<String>
//           val  sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val sharedPrefs = context?.getSharedPreferences("secret", Context.MODE_PRIVATE)!!
            val gsonInstance = GsonBuilder().create()
            val json = gsonInstance.toJson(user)
            result = arrayListOf(json)


            sharedPrefs.edit()
                .apply {
                    putString(key, json)
                    putString("user", json)
                    apply()
                }
            return result
        }

        fun getData(context: Context?, user:String):UserData?{

//           val  sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val gsonInstance = GsonBuilder().create()

            return gsonInstance.fromJson(user, UserData::class.java)

        }

        fun getError(context: Context?, error:String):ErrorData?{

//           val  sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val gsonInstance = GsonBuilder().create()

            return gsonInstance.fromJson(error, ErrorData::class.java)

        }


    }


}