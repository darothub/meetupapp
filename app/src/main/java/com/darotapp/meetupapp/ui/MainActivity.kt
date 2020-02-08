package com.darotapp.meetupapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.darotapp.meetupapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.fragment)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
//        }
    }


    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment), null
        )
    }
}
