package app.platinummzadat.qa.views.root.tac

import app.platinummzadat.qa.MApp.Companion.MzRepo
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 14:32 on Monday 13 May 2019
 */
class TermsAndConditionsPresenter(private val view:TermsAndConditionsContract.View):TermsAndConditionsContract.Presenter {
    init {
        view.presenter = this
    }

    override fun fetchTac() {
        view.showLoading()
        MzRepo.fetchTac{status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showEmptyData()
                }
                status -> {
                    view.showTac(data)
                }
                else -> {
                    view.showApiError()
                }
            }

        }
    }
}