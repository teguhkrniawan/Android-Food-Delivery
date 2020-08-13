package com.teguh.bfd.ui.foodlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.models.Foods

class FoodListViewModel : ViewModel() {

    private var mutableFoodListData: MutableLiveData<List<Foods>>? = null

    fun getMutableFoodListData(): MutableLiveData<List<Foods>> {
        if (mutableFoodListData == null){
            mutableFoodListData = MutableLiveData()
        }
        /*
              Di model category kan ada properti list foods
              jadi mutable live datanya deklarasikan dari properti tsb
         */
        mutableFoodListData!!.value = Commons.categorySelected!!.foods
        return mutableFoodListData!!
    }



}