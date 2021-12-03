package app.platinummzadat.qa.views.root

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import app.platinummzadat.qa.firebaseId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 16:04 on Saturday 20 April 2019
 */
class RootPresenter(private val view: RootContract.View) : RootContract.Presenter {
    init {
        view.presenter = this
    }

    override fun updateLastActive() {
        view.showLoading()
        MzRepo.updateLastActive(currentUserId){status, error ->
            view.hideLoading()
            view.lastActiveUpdated()
        }
    }
    override fun fetchDashboard() {
       // view.showLoading()
        MzRepo.fetchDashboard(firebaseId,1) { status, data, error ->
           // view.hideLoading()
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
                    view.showDashboard(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}