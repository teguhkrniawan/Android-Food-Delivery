package com.teguh.bfd.ui.detailfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andremion.counterfab.CounterFab
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.teguh.bfd.R
import com.teguh.bfd.models.Foods
import java.lang.StringBuilder

class DetailFoodFragment : Fragment() {

    private lateinit var detailFoodViewModel: DetailFoodViewModel

    // komponen dalam layout
    private var imgFood: ImageView? = null
    private var txtFoodName: TextView? = null
    private var txtFoodPrice: TextView? = null
    private var btnQuantity: ElegantNumberButton? = null
    private var floatinngCart: CounterFab? = null
    private var floatingStar: FloatingActionButton? = null
    private var txtDescription: TextView? = null
    private var ratingBar: RatingBar? = null
    private var btnShowComment: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailFoodViewModel = ViewModelProviders.of(this).get(DetailFoodViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_detail_food, container, false)
        initViews(root)

        detailFoodViewModel.getMutableLiveDataFood().observe(viewLifecycleOwner, Observer {
            displayFood(it)
        })

        return root
    }

    private fun initViews(root: View?) {
        imgFood = root!!.findViewById(R.id.detail_food_img)
        txtFoodName = root.findViewById(R.id.detail_txt_name)
        txtFoodPrice = root.findViewById(R.id.detail_txt_price)
        txtDescription = root.findViewById(R.id.detail_food_description)
    }

    private fun displayFood(it: Foods?) {
        Glide.with(requireContext()).load(it!!.image).into(imgFood!!)
        txtFoodName!!.text = StringBuilder(it.name)
        txtFoodPrice!!.text = StringBuilder(it.price.toString())
        if (it.description == ""){
            txtDescription!!.text = "Tidak ada deskripsi"
        } else{
            txtDescription!!.text = StringBuilder(it.description)
        }
    }
}