package com.darotapp.meetupapp.ui


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.darotapp.meetupapp.R
import com.darotapp.meetupapp.helper.FieldValidation
import com.darotapp.meetupapp.helper.IsEmptyCheck
import com.darotapp.meetupapp.helper.SharedPrefManager
import com.darotapp.meetupapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {

    var userViewModel:AuthViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        fullNameEditText.requestFocus()

        pinEditText.doOnTextChanged { text, start, count, after ->
            val passwordPattern = Regex("""^[a-zA-Z0-9@$!%*#?&]{6,}$""")
            val matchedPassword = passwordPattern.matches(text!!)
            if(!matchedPassword){
                passwordStandard.visibility = View.VISIBLE
                passwordStandard.setTextColor(Color.RED)
            }
            else{
                passwordStandard.visibility = View.GONE
            }
        }

        registerBtn.setOnClickListener {
            registerRequest()
        }

        termsAndCondition.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                try {
                    registerRequest()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun registerRequest(){
        hideKeyboard()
        val fullName = fullNameEditText.text.toString().trim()
        val emailAddress = emailEditText.text.toString().trim()
        val passwordString = pinEditText.text.toString().trim()
        val repeatPasswordString = repeatPinEditText.text.toString().trim()
        val value = IsEmptyCheck(fullNameEditText, emailEditText,pinEditText)

        if(value){
            val validateField = FieldValidation.instance.registerFieldInput(emailAddress, passwordString)
            val validatePasswords = FieldValidation.instance.passwordMatcher(passwordString, repeatPasswordString)

            if(validateField != "true"){
                Toast.makeText(context, validateField, Toast.LENGTH_LONG).show()
            }
            else if(validatePasswords != "true"){
                Toast.makeText(context, validatePasswords, Toast.LENGTH_LONG).show()
            }
            else if(!termsAndCondition.isChecked){
               Toast.makeText(context, "Accept terms and condition", Toast.LENGTH_LONG).show()
            }
            else{

                loading.visibility = View.VISIBLE
                registerBtn.visibility = View.GONE
                CoroutineScope(Dispatchers.Main).launch {
                    val res= userViewModel!!.registerUser(fullName, emailAddress, passwordString, context!!)
                    res.observeForever { t->
                        Log.i("reg", t)
                        val userObject = t.let { SharedPrefManager.getData(context!!, it) }
                        if(!userObject?.error!!){
                            loading.visibility = View.GONE
                            registerBtn.visibility = View.VISIBLE

//                            val actionToVerifyCode = RegisterFragmentDirections.actionToVerification()
//                            actionToVerifyCode.user = userObject
//                            Navigation.findNavController(registerBtn).navigate(actionToVerifyCode)
                            Toast.makeText(context, userObject.message, Toast.LENGTH_LONG).show()
                        }
                        else{
                            loading.visibility = View.GONE
                            registerBtn.visibility = View.VISIBLE
                            Toast.makeText(context, userObject.message, Toast.LENGTH_LONG).show()

                        }

                    }
                }
            }
        }


    }



    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
