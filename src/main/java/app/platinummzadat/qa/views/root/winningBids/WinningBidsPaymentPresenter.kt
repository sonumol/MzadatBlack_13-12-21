package com.platinummzadat.qa.views.root.companyfees

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by Badhusha
 */

class WinningBidsPaymentPresenter (private val view:WinningBidsPaymentContract.View):WinningBidsPaymentContract.Presenter{
    init {
        view.presenter = this
    }

    override fun winningBidsPayment(bidId: Int) {
        view.showLoading()
        MApp.MzRepo.winningBidsPayment(currentUserId,bidId){ status, data, error ->
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