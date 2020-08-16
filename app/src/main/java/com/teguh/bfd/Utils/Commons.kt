package com.teguh.bfd.Utils

import com.teguh.bfd.models.Categories
import com.teguh.bfd.models.Foods
import com.teguh.bfd.models.Users

object Commons {

    var categorySelected: Categories? = null
    var foodSelected: Foods? = null
    var currentUser: Users? = null

    const val FULL_WIDTH_COLUMN: Int = 1
    const val COUNT_ITEM_DEFAULT: Int = 0

    const val CATEGORY_REF: String = "Category"
    const val BEST_DEALS_REF: String = "BestDeals"
    const val POPULAR_REF: String = "MostPopular"
    const val USER_REFERENCES = "Users"

}