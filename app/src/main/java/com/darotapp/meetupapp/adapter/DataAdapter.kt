package com.darotapp.meetupapp.adapter

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.darotapp.meetupapp.R
import com.darotapp.meetupapp.model.ProductEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataAdapter(private var productEntity:List<ProductEntity?>? ): RecyclerView.Adapter<DataAdapter.DataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)

        return DataHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productEntity!!.size
    }

    fun setPost(products: List<ProductEntity?>?){
        this.productEntity = products as List<ProductEntity>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        productEntity?.let{
            val currentPost = it[position]
            holder.bind(it[position]!!)
        }
    }

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val productTitle = itemView.findViewById<TextView>(R.id.subject)
        val price = itemView.findViewById<TextView>(R.id.price)
        val discount = itemView.findViewById<TextView>(R.id.discount)


        fun bind(productEntity: ProductEntity){


            try {
                productTitle.setText(productEntity.title)
                price.setText(productEntity.price)
                discount.setText(productEntity.discount)
                image.setImageResource(productEntity.image!!)


                val color = arrayListOf<Int>(Color.parseColor("#EBD0B7"), Color.parseColor("#E73361"), Color.parseColor("#FF9800"), Color.RED, Color.GREEN, Color.BLUE)
                val range = color.size-1
                val rand = (0..range).shuffled().last()
//            val filter: ColorFilter = PorterDuffColorFilter(color[rand], PorterDuff.Mode.SRC_IN)
                image.setBackgroundColor(color[rand])
            } catch (e: Exception) {
            }

        }

    }

    interface OnProductListener{
        fun onPostClick(productEntity: ProductEntity)
    }


}