package com.darotapp.meetupapp.helper

import android.widget.EditText

class IsEmptyCheck {

    companion object{
        operator fun invoke(vararg edits : EditText):Boolean {
            var value:Boolean? = null
            for(edit in edits){
                if(edit.text.toString().trim().isEmpty()){
                    edit.error = "This field is required"
                    value = false
                }
                else{
                    value = true
                }

            }
            return value!!

        }

    }
}