package app.platinummzadat.qa.views.registration.profilephoto

import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 12:05 on Friday 19 April 2019
 */
class ProfilePhotoPresenter(private val view: ProfilePhotoContract.View) : ProfilePhotoContract.Presenter {
    init {
        view.presenter = this
    }

    override fun uploadPhoto(path: String) {
        view.showLoading()
        MzRepo.uploadProfilePhoto(currentUserId,path){status, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status -> {
                    view.showSuccess()
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
    override fun fetchProfile() {
        // view.showLoading()
        MzRepo.fetchProfile(currentUserId) { status, data, error ->
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
                    view.showData(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }

}