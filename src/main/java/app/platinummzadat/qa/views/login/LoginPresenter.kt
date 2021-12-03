package app.platinummzadat.qa.views.login

import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.R
import app.platinummzadat.qa.tempUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 14:21 on Wednesday 17 April 2019
 */
class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {
    init {
        view.presenter = this
    }

    override fun authenticate(qatarId: String, phone: String, crnumber:String,countryCode :String,countryName :String,hash: String) {
        view.showLoading()
        MzRepo.login(qatarId, phone, crnumber,countryCode,countryName, hash) { status,message, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                error == ERROR.INVALID_USER -> {
                    view.showInvalidCredentials(message)
                }
                error == ERROR.BLOCKED_USER -> {
                    view.showBlockedUser(R.string.account_blocked, "")
                }
                error == ERROR.DUPLICATE_QID -> {
                    view.showInvalidCredentials(message)
//                    view.showBlockedUser(R.string.duplicate_qatar_id, qatarId)
                }
                error == ERROR.DUPLICATE_MOBILE -> {
                    view.showInvalidCredentials(message)
//                    view.showBlockedUser(R.string.duplicate_mobile, phone)
                }
                error == ERROR.DUPLICATE_CR -> {
                    view.showInvalidCredentials1(message)
//                    view.showBlockedUser(R.string.duplicate_mobile, phone)
                }
                error == ERROR.NO_REGISTERD_PERSON -> {
                    view.showInvalidCredentials2(message)
//                    view.showBlockedUser(R.string.duplicate_mobile, phone)
                }

                error == ERROR.CR_MISMACH -> {
                    view.showInvalidCredentials3(message)
//                    view.showBlockedUser(R.string.duplicate_mobile, phone)
                }
                error == ERROR.EMAIL_MISMATCH -> {
                    view.showInvalidCredentials4(message)
//                    view.showBlockedUser(R.string.duplicate_mobile, phone)
                }
                status -> {
                    view.showSuccess()
                    tempUserId = data
                }
            }
        }
    }
    override fun fetchcountry() {
       // view.showLoading()
        MzRepo.fetchcountry { status, data, error ->
            //view.hideLoading()
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