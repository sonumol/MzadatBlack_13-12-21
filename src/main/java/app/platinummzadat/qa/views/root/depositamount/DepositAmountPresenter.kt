package app.platinummzadat.qa.views.root.depositamount

import android.widget.Toast
import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 16:22 on Wednesday 24 April 2019
 */
class DepositAmountPresenter(private val view: DepositAmountContract.View) : DepositAmountContract.Presenter {
    init {
        view.presenter = this
    }

    override fun fetchDepositAmount() {
        view.showLoading()
        MzRepo.fetchDepositHistory(currentUserId) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                data!!.details.isEmpty() -> {
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

    override fun getAmount() {
        MzRepo.getAmount(currentUserId) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status -> {
                    data?.let { view.showAmountData(it) }

                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }

    override fun getRefundRequest(imei:String) {
        view.showLoading()
        MzRepo.getRefundRequest(imei) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status=="true" -> {
                    data?.let { view.showRefundData(it) }
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}