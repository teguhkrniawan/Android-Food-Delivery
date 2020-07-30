package com.teguh.bfd.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.callback.ICategoriesCallbackListener
import com.teguh.bfd.models.Categories

class MenuViewModel : ViewModel(), ICategoriesCallbackListener {

    private var categoriesListMutable: MutableLiveData<List<Categories>>? = null
    private var messageError: MutableLiveData<String>? = MutableLiveData()
    private var categoriesCallbakListener: ICategoriesCallbackListener

    init {
        categoriesCallbakListener = this
    }

    override fun onCategoriesLoadSuccess(categoriesList: List<Categories>) {
        categoriesListMutable!!.value = categoriesList
    }

    override fun onCategoriesLoadFailed(message: String) {
        messageError!!.value = message
    }

    /**
     * @params : MutableLiveData<List<Categories>>
     */
    fun getCategoryList() : MutableLiveData<List<Categories>>{
        if (categoriesListMutable == null){
            categoriesListMutable = MutableLiveData()
            loadCategories()
        }
        return categoriesListMutable!!
    }

    fun getMessageError() : MutableLiveData<String> {
        return messageError!!
    }

    private fun loadCategories() {
        val templist = ArrayList<Categories>() // isi arraylist == 0
        val categoiesRef = FirebaseDatabase.getInstance().getReference(Commons.CATEGORY_REF)

        categoiesRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                categoriesCallbakListener.onCategoriesLoadFailed(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children){
                    val model = itemSnapshot.getValue<Categories>(Categories::class.java)
                    model!!.menu_id == itemSnapshot.key // ini buat ambil menu_01, menu_02, dst
                    templist.add(model)
                }
                categoriesCallbakListener.onCategoriesLoadSuccess(templist)
            }

        })
    }

}