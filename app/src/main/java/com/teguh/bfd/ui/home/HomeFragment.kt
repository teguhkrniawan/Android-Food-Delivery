package com.teguh.bfd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.asksira.loopingviewpager.LoopingViewPager
import com.teguh.bfd.R
import com.teguh.bfd.adapter.MyBestDealsAdapter
import com.teguh.bfd.adapter.MyPopularCategoriesAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var layoutAnimationController: LayoutAnimationController

    private lateinit var recyclerPopular: RecyclerView
    private lateinit var viewpagerBestdeals: LoopingViewPager

    override fun onResume() {
        super.onResume()
        //viewpagerBestdeals.resumeAutoScroll()
    }

    override fun onPause() {
       // viewpagerBestdeals.pauseAutoScroll()
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initView(root)

        // bind data
        homeViewModel.popularList.observe(viewLifecycleOwner, Observer {
            val listData = it
            val adapter = MyPopularCategoriesAdapter(requireContext(), listData)
            recyclerPopular.adapter = adapter
            recyclerPopular.layoutAnimation = layoutAnimationController
        })

        homeViewModel.bestdelasList.observe(viewLifecycleOwner, Observer {
            val listData = it
            val adapter = MyBestDealsAdapter(requireContext(), listData, true)
            viewpagerBestdeals.adapter = adapter
        })

        return root
    }

    private fun initView(itemView: View) {
        // insialisasi animasi
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)

        viewpagerBestdeals = itemView.findViewById(R.id.viewpager_big_deals) as LoopingViewPager
        recyclerPopular = itemView.findViewById(R.id.rv_populer_category) as RecyclerView

        recyclerPopular.setHasFixedSize(true)
        recyclerPopular.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}