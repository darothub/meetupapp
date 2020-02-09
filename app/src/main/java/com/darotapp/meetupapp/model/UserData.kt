package com.darotapp.meetupapp.model

import java.io.Serializable

class UserData(
    var name: String?,
    var email:String?,
    var password:String?

):Serializable {

    var loggedIn:Boolean = false
    var message = ""
    var error = false
    var token = ""
    var id =""

}