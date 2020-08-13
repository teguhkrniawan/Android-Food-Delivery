package com.teguh.bfd.ui.foodlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teguh.bfd.R
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.adapter.MyFoodListAdapter

class FoodListFragment : Fragment() {

    private lateinit var foodListViewModel: FoodListViewModel
    var rvFoodList: RecyclerView? = null
    private lateinit var layoutAnimationController: LayoutAnimationController

    var adapter: MyFoodListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        foodListViewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_food_list, container, false)

        initView(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodListViewModel.getMutableFoodListData().observe(viewLifecycleOwner, Observer {
            adapter = MyFoodListAdapter(requireContext(), it)
            rvFoodList!!.adapter = adapter
            rvFoodList!!.layoutAnimation = layoutAnimationController
        })
    }

    private fun initView(root: View?) {
        rvFoodList = root!!.findViewById(R.id.rv_food_list)
        rvFoodList!!.setHasFixedSize(true)
        rvFoodList!!.layoutManager = LinearLayoutManager(context)

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context ,R.anim.layout_item_from_left)

        (activity as AppCompatActivity).supportActionBar!!.title = Commons.categorySelected!!.name
    }

}