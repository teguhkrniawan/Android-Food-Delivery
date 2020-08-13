package com.teguh.bfd.ui.menu

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teguh.bfd.R
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.Utils.SpaceItemDecoration
import com.teguh.bfd.adapter.MyCategoriesAdapter
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_category.*

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var dialog: AlertDialog
    private lateinit var layoutAnimationController: LayoutAnimationController
    private var adapter: MyCategoriesAdapter? = null
    private var rvCategories: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_category, container, false)

        // insialisasi semua kebutuhan view
        iniViews(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //secara reactive (stream) pantau data
        menuViewModel.getMessageError().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Error : $it", Toast.LENGTH_SHORT).show()
        })

        // secara reactive (stream) pantau data
        menuViewModel.getCategoryList().observe(viewLifecycleOwner, Observer {
            dialog.dismiss()
            adapter = MyCategoriesAdapter(requireContext(), it)
            rvCategories!!.adapter = adapter
            rvCategories!!.layoutAnimation = layoutAnimationController
        })
    }

    private fun iniViews(root: View) {
        dialog = SpotsDialog.Builder().setContext(context)
            .setCancelable(false)
            .build()

        rvCategories = root.findViewById(R.id.rv_categories) as RecyclerView

        dialog.show()
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
        rvCategories!!.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.orientation = RecyclerView.VERTICAL
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter != null){
                    when(adapter!!.getItemViewType(position)){
                        Commons.COUNT_ITEM_DEFAULT -> 1
                        Commons.FULL_WIDTH_COLUMN -> 2
                        else -> 1
                    }
                }else {
                    -1
                }
            }
        }
        rvCategories!!.layoutManager = layoutManager
        rvCategories!!.addItemDecoration(SpaceItemDecoration(8))

    }
}