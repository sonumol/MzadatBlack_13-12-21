package app.platinummzadat.qa.views.root.profile

import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 17:57 on Thursday 18 April 2019
 */
class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {
    init {
        view.presenter = this
    }

    /*override fun uploadQid(path: String) {
        view.showUploadingQid()
        MzRepo.uploadQid(currentUserId,path){status, error ->
            view.hideUploadingQid()
            when {
                error == ERROR.API_ERROR -> {
                    view.showUploadQidFailed()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status -> {
                    view.showUploadedQid()
                }
                else -> {
                    view.showUploadQidFailed()
                }
            }
        }
    }*/
    override fun fetchProfile() {
        view.showLoading()
        MzRepo.fetchProfile(currentUserId) { status, data, error ->
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
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status -> {
                    data?.let { view.showDataAmount(it) }

                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}