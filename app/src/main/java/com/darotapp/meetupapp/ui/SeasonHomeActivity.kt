package com.darotapp.meetupapp.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.darotapp.meetupapp.R
import kotlinx.android.synthetic.main.activity_season_home.*
import java.util.*
import kotlin.collections.ArrayList

class SeasonHomeActivity : AppCompatActivity() {

    private lateinit var  sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_home)

//        toolbar.inflateMenu(R.menu.season_home)
        val navController = Navigation.findNavController(this, R.id.fragment2)


        sharedPref = this.getSharedPreferences("secret", Context.MODE_PRIVATE)
        val backPressed = sharedPref.getBoolean("backpressed", false)


        val products = resources.getStringArray(R.array.products_lists)

        val productList = ArrayList<String>()
        productList.addAll(products)
//        val greenWallCountries = arrayListOf<String>("Great green")

        Collections.sort(productList, String.CASE_INSENSITIVE_ORDER)


        val adapter = ArrayAdapter(this, R.layout.spinner_item, productList)
        locationSpinner.adapter = adapter



        changeBackground(dressBtn, pantsBtn, blazersBtn, jacketBtn)
        changeBackground(pantsBtn, dressBtn,  blazersBtn, jacketBtn)
        changeBackground(blazersBtn, pantsBtn, dressBtn,   jacketBtn)
        changeBackground(jacketBtn, blazersBtn, pantsBtn, dressBtn)

        dressBtn.setBackgroundResource(R.drawable.btn_radius_corner)
        dressBtn.setTextColor(resources.getColor(R.color.colorAccent))


    }

    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment2), null
        )
    }

    fun changeBackground(button: Button, vararg buttons: Button) {

        button.setOnClickListener {
            button.setBackgroundResource(R.drawable.btn_radius_corner)
            button.setTextColor(resources.getColor(R.color.colorAccent))

            for (other in buttons) {
                other.setBackgroundColor(resources.getColor(R.color.colorAccent))
                other.setTextColor(resources.getColor(R.color.black))
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()


        try {
            sharedPref.edit()
                .apply {
                    putBoolean("backpressed", true)
                    apply()
                }

//        Toast.makeText(this, "back", Toast.LENGTH_LONG).show()


            findNavController(R.id.fragment2)
        } catch (e: Exception) {

        }

    }

}
