package com.platinummzadat.qa.views.root.companyfees

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:14 on Thursday 15 August 2019
 */

class WinningBidsPresenter (private val view:WinningBidsContract.View):WinningBidsContract.Presenter{
    init {
        view.presenter = this
    }
    override fun winningBids() {
        view.showLoading()
        MApp.MzRepo.winningBids(currentUserId){ status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null==data -> {
                    view.showEmptyData()
                }
                status -> {
                    view.showData(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }



}