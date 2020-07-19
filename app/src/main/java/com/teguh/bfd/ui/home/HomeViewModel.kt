package com.teguh.bfd.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.callback.IpopularCategories
import com.teguh.bfd.models.PopularCategories

class HomeViewModel : ViewModel(), IpopularCategories {

    private var popularListMutableLiveData: MutableLiveData<List<PopularCategories>>? = null
    private lateinit var messageError: MutableLiveData<String>
    private lateinit var popularLoadCallbackListener: IpopularCategories

    val popularList: LiveData<List<PopularCategories>>
        get(){
            if (popularListMutableLiveData == null){
                popularListMutableLiveData = MutableLiveData()
                messageError = MutableLiveData()
                loadPopularList()
            }
            return popularListMutableLiveData!!
        }

    private fun loadPopularList() {
        val tempList = ArrayList<PopularCategories>()
        val populerRef = FirebaseDatabase.getInstance().getReference(Commons.POPULAR_REF)

        populerRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                popularLoadCallbackListener.onPopularLoadFailed(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children){
                    val modelPopular = itemSnapshot.getValue<PopularCategories>(PopularCategories::class.java)
                    tempList.add(modelPopular!!)
                }
                popularLoadCallbackListener.onPopularLoadSuccess(tempList)
            }

        })
    }

    init {
        popularLoadCallbackListener = this
    }

    override fun onPopularLoadSuccess(popularModelList: List<PopularCategories>) {
        popularListMutableLiveData!!.value = popularModelList
    }

    override fun onPopularLoadFailed(message: String) {
        messageError.value = message
    }
}