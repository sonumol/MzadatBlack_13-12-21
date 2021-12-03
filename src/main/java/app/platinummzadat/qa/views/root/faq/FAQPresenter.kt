package app.platinummzadat.qa.views.root.faq

import app.platinummzadat.qa.MApp.Companion.MzRepo
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 10:21 on Monday 24 June 2019
 */
class FAQPresenter(private val view:FAQContract.View):FAQContract.Presenter {
    init {
        view.presenter = this
    }

    override fun fetchFaq() {
        view.showLoading()
        MzRepo.FAQ { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showApiError()
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