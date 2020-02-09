package com.darotapp.meetupapp.ui


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.darotapp.meetupapp.R
import com.darotapp.meetupapp.helper.FieldValidation
import com.darotapp.meetupapp.helper.IsEmptyCheck
import com.darotapp.meetupapp.helper.SharedPrefManager
import com.darotapp.meetupapp.model.UserData
import com.darotapp.meetupapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.emailEditText
import kotlinx.android.synthetic.main.fragment_sign_in.pinEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : Fragment() {

    var userData: UserData? = null
    var userViewModel: AuthViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //receive safe-arg from register fragment
        arguments?.let {
           userData = SignInFragmentArgs.fromBundle(it).user
//            Toast.makeText(context, "start $user", Toast.LENGTH_SHORT).show()
        }

        userViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


        if(userData != null){

            signinTitle.setText(R.string.congratulation)
            signinadvise.setText(R.string.signinafter_registration)
            emailEditText.setText(userData!!.email)
            pinEditText.setText(userData!!.password)

        }

        signinBtn.setOnClickListener {
            signinRequest()
        }

        pinEditText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                try {
                    signinRequest()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun signinRequest(){
        hideKeyboard()

        val emailAddress = emailEditText.text.toString().trim()
        val passwordString = pinEditText.text.toString().trim()
        val value = IsEmptyCheck(emailEditText,pinEditText)

        if(value){
            val validateField = FieldValidation.instance.registerFieldInput(emailAddress, passwordString)


            if(validateField != "true"){
                signinPasswordStandard.visibility = View.VISIBLE
                Toast.makeText(context, validateField, Toast.LENGTH_LONG).show()
            }
            else{

                loadingSignin.visibility = View.VISIBLE
                signinBtn.visibility = View.GONE
                signinPasswordStandard.visibility = View.GONE
                CoroutineScope(Dispatchers.Main).launch {
                    val res= userViewModel!!.userLogin(emailAddress, passwordString, context!!)
                    res.observeForever { t->
                        Log.i("signin", t)
                        val userObject = t.let { SharedPrefManager.getData(context!!, it) }
                        if(!userObject?.error!!){
                            loadingSignin.visibility = View.GONE
                            signinBtn.visibility = View.VISIBLE

                            val action = SignInFragmentDirections.toIntrdouction()
                            Navigation.findNavController(signinBtn).navigate(action)
                            Toast.makeText(context, userObject.message, Toast.LENGTH_LONG).show()
                        }
                        else{
                            loadingSignin.visibility = View.GONE
                            signinBtn.visibility = View.VISIBLE
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
