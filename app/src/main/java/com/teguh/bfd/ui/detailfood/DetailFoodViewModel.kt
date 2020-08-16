package com.teguh.bfd.ui.detailfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.models.Foods

class DetailFoodViewModel : ViewModel() {

   private var mutableLiveDataFood: MutableLiveData<Foods>? = null

   fun getMutableLiveDataFood(): MutableLiveData<Foods>{
       if (mutableLiveDataFood == null){
           mutableLiveDataFood = MutableLiveData()
       }
       mutableLiveDataFood!!.value = Commons.foodSelected
       return mutableLiveDataFood!!
   }
}