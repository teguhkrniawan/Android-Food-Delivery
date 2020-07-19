package com.teguh.bfd.callback

import com.teguh.bfd.models.PopularCategories

// membuat load callback dari livedata
interface IpopularCategories {
    fun onPopularLoadSuccess(popularModelList: List<PopularCategories>)
    fun onPopularLoadFailed(message: String)
}