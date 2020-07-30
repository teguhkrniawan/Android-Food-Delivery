package com.teguh.bfd.callback

import com.teguh.bfd.models.Categories

interface ICategoriesCallbackListener {
    fun onCategoriesLoadSuccess(categoriesList: List<Categories>)
    fun onCategoriesLoadFailed(message: String)
}