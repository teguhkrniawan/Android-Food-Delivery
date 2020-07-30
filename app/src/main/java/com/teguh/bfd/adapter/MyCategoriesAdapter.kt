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
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.models.Categories
import de.hdodenhof.circleimageview.CircleImageView

class MyCategoriesAdapter(
    internal val context: Context,
    internal val categoriesModel: List<Categories>
) : RecyclerView.Adapter<MyCategoriesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        // insialisasi view layout_item_category
        var categoriesName: TextView? = null
        var categoriesImage: ImageView? = null

        init{
            // lakukan binding saat pertama kali class ini di generate
            categoriesName = itemView.findViewById(R.id.txt_categories)
            categoriesImage = itemView.findViewById(R.id.img_categories)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // inflate view layout, lempar argument layout pada class MyViewHolder
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false))
    }

    override fun getItemCount(): Int {
        return categoriesModel.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // setelah view dibuat mau diapakan ?
        holder.categoriesName!!.setText(categoriesModel[position].name)
        Glide.with(context).load(categoriesModel[position].image)
            .into(holder.categoriesImage!!)
    }

    override fun getItemViewType(position: Int): Int {
        // kembalikan nilai 0 atau 1
        return if (categoriesModel.size == 1){ // jika panjang list model categories == 1
            Commons.COUNT_ITEM_DEFAULT // return 0
        } else { // jika list model size != 1 ( list size > 1 )
            if (categoriesModel.size % 2 == 0){ // jika list size nya genap
                Commons.COUNT_ITEM_DEFAULT // return 0
            } else { // jika list size ganjil
                if (position > 1 && position == categoriesModel.size-1){ // lakukan pengecekan dimana index trakhir > 1 dan indextrakhir == sizr - 1
                    Commons.FULL_WIDTH_COLUMN
                } else {
                    Commons.COUNT_ITEM_DEFAULT
                }
            }
        }
    }

}