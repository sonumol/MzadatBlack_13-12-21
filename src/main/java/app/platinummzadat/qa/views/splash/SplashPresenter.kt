package app.platinummzadat.qa.views.splash

import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import app.platinummzadat.qa.firebaseId
import app.platinummzadat.qa.trendingSearch
import raj.nishin.wolfpack.gson
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:08 on Tuesday 14 May 2019
 */
class SplashPresenter(private val view:SplashContract.View):SplashContract.Presenter {
    init {
        view.presenter =this
    }

    override fun splash( type: Int) {
        view.showLoading()
        MzRepo.splash(currentUserId, firebaseId, type){status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                error == ERROR.BLOCKED_USER -> {
                    view.showBlocked()
                }
                null == data->{
                    view.showApiError()
                }
                status -> {
                  //  trendingSearch = gson.toJson(data.items.map { it.name })
                    view.showSuccess(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}