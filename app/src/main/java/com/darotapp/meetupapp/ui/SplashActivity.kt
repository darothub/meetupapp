package com.darotapp.meetupapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darotapp.meetupapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val myThread = Thread(){
            try {
                kotlin.run {
                    Thread.sleep(3000)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            catch(e: Exception){
                e.printStackTrace()
            }

        }
        myThread.start()
    }
}
