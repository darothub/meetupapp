package com.darotapp.meetupapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.darotapp.meetupapp.R
import kotlinx.android.synthetic.main.fragment_introduction.*

/**
 * A simple [Fragment] subclass.
 */
class IntroductionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        skipBtn.setOnClickListener {
            val action = IntroductionFragmentDirections.toSeasonHome()
            Navigation.findNavController(it).navigate(action)
        }

        requireActivity().onBackPressedDispatcher.addCallback {

            findNavController().popBackStack(R.id.signInFragment, true)
//            activity!!.finish()
        }
    }

}
