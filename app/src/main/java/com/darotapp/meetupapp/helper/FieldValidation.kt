package com.darotapp.meetupapp.helper

class FieldValidation() {

    private object HOLDER {
        val INSTANCE = FieldValidation()
    }

    companion object {
        val instance: FieldValidation by lazy { HOLDER.INSTANCE }
    }

    fun registerFieldInput(email:String, password:String):String{

        var message:String?=null
        val emailParttern = Regex("""^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*${'$'}""")
        val passwordPattern = Regex("""^[a-zA-Z0-9@$!%*#?&]{6,}$""")
        val matchedEmail = emailParttern.matches(email)
        val matchedPassword = passwordPattern.matches(password)

        if(!matchedEmail){
            message = "Invalid Email address"
            return message
        }
        else if(!matchedPassword){
            message = "Invalid password pattern"
            return message
        }
        message = "true"
        return message

    }

    fun passwordMatcher(password1:String, password2:String):String{

        var message:String?=null
        val passwordPattern = Regex("""^[a-zA-Z0-9@$!%*#?&]{6,}$""")
        val matchedPassword = passwordPattern.matches(password1)
        val matchedPassword2 = passwordPattern.matches(password2)
        if(!matchedPassword){
            message = "Invalid password pattern"
            return message
        }
        else if(!matchedPassword2){
            message = "Invalid password pattern"
            return message
        }
        else if(password1 != password2){
            message ="passwords do not match"
            return message
        }
        message = "true"
        return message

    }
}


