package app.platinummzadat.qa.views.root.companyfees

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:14 on Thursday 15 August 2019
 */

class CompanyFeesPresenter (private val view:CompanyFeesContract.View):CompanyFeesContract.Presenter{
    init {
        view.presenter = this
    }
    override fun fetchFees() {
        view.showLoading()
        MApp.MzRepo.fetchCompanyFees(currentUserId){ status, data, error ->
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