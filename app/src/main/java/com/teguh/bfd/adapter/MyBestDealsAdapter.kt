package com.teguh.bfd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.teguh.bfd.R
import com.teguh.bfd.models.BestDeals

class MyBestDealsAdapter(
        context: Context,
        itemList: List<BestDeals>,
        isInfinite: Boolean
) : LoopingPagerAdapter<BestDeals>(context, itemList, isInfinite){

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val image = convertView.findViewById<ImageView>(R.id.img_best_deals)
        val name = convertView.findViewById<TextView>(R.id.txt_best_deals)

        Glide.with(context).load(itemList!![listPosition].image).into(image)
        name.text = itemList!![listPosition].name
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.layout_best_deal_item, container, false)
    }

}