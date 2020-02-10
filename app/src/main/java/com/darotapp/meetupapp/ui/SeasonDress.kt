package com.darotapp.meetupapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.darotapp.meetupapp.R
import com.darotapp.meetupapp.adapter.DataAdapter
import com.darotapp.meetupapp.model.ProductEntity
import kotlinx.android.synthetic.main.fragment_season_dress.*

/**
 * A simple [Fragment] subclass.
 */
class SeasonDress : Fragment() {

    private var adapter:DataAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_season_dress, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newProducts = arrayListOf(
            ProductEntity(R.drawable.model_one, "Empire Dress", "$120.89", "20% Off"),
            ProductEntity(R.drawable.model_one, "Summer Vibes", "$130.89", "20% Off"),
            ProductEntity(R.drawable.model_one, "Flora un", "$140.89", "10% Off"),
            ProductEntity(R.drawable.model_one, "Fit and Flare", "$120.89", "20% Off")

        )

        adapter = DataAdapter(newProducts)
        recycler_view.layoutManager = GridLayoutManager(context?.applicationContext, 2)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

}
