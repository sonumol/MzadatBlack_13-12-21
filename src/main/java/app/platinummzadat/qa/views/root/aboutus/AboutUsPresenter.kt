package app.platinummzadat.qa.views.root.aboutus

import app.platinummzadat.qa.MApp.Companion.MzRepo
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 10:21 on Monday 24 June 2019
 */
class AboutUsPresenter(private val view:AboutUsContract.View): AboutUsContract.Presenter {
    init {
        view.presenter = this
    }

    override fun fetchAboutUs() {
        view.showLoading()
        MzRepo.fetchAboutUs{ status, data, error ->
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