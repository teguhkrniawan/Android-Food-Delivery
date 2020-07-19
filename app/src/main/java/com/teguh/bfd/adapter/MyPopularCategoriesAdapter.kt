package com.teguh.bfd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teguh.bfd.R
import com.teguh.bfd.models.PopularCategories
import de.hdodenhof.circleimageview.CircleImageView

class MyPopularCategoriesAdapter(
        internal var context: Context,
        internal var popularCategoriesModel: List<PopularCategories>
    ) : RecyclerView.Adapter<MyPopularCategoriesAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var categoryName: TextView? = null
        var categoryImage: CircleImageView? = null

        init {
            categoryName = itemView.findViewById(R.id.txt_category) as TextView
            categoryImage = itemView.findViewById(R.id.img_category) as CircleImageView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_populer_category_item, parent, false))
    }

    override fun getItemCount(): Int {
        return popularCategoriesModel.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(popularCategoriesModel[position].image).into(holder.categoryImage!!)
        holder.categoryName!!.setText(popularCategoriesModel[position].name)
    }

}