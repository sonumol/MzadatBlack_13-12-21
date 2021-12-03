package app.platinummzadat.qa.views.root.profile.deposit

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by Badhusha
 */

class PaymentPresenter (private val view: PaymentContract.View):PaymentContract.Presenter{
    init {
        view.presenter = this
    }

    override fun doPayment(amount: String) {
        view.showLoading()
        MApp.MzRepo.doPayment(currentUserId,amount){ status, data, error ->
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