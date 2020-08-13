package com.teguh.bfd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teguh.bfd.R
import com.teguh.bfd.models.Foods
import org.w3c.dom.Text

class MyFoodListAdapter(
    internal var context: Context,
    internal var foodList: List<Foods>
) : RecyclerView.Adapter<MyFoodListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        // insialisasi layout_items_food
        var foodImage: ImageView? = null
        var foodName: TextView? = null
        var foodPrice: TextView? = null
        var imgFav: ImageView? = null
        var imgCart: ImageView? = null

        init {
            foodImage = itemView.findViewById(R.id.img_food_item)
            foodName = itemView.findViewById(R.id.txt_name_food_item)
            foodPrice = itemView.findViewById(R.id.txt_price_food_item)
            imgFav = itemView.findViewById(R.id.img_fav_food_item)
            imgCart = itemView.findViewById(R.id.img_cart_food_item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_food_items, parent, false))
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // manipulasi view yang sudah di insialisasi
        holder.foodName!!.text = foodList[position].name
        holder.foodPrice!!.text = foodList[position].price.toString()
        Glide.with(context).load(foodList[position].image).into(holder.foodImage!!)
    }

}