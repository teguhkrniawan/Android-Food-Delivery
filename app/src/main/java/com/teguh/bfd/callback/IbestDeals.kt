package com.teguh.bfd.callback

import com.teguh.bfd.models.BestDeals

interface IbestDeals {
    fun onBestDealsLoadSuccess(bestDealsList: List<BestDeals>)
    fun onBestDealsLoadFailed(message: String)
}