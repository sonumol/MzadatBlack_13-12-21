package app.platinummzadat.qa.views.login

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.ContactUsModel
import app.platinummzadat.qa.data.models.fetchcountrymodel

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
        fun showInvalidCredentials(message: String)
        fun showInvalidCredentials1(message: String)
        fun showInvalidCredentials2(message: String)
        fun showInvalidCredentials3(message: String)
        fun showInvalidCredentials4(message: String)
        fun showBlockedUser(messageResId: Int, append: String)
        fun showData(data: fetchcountrymodel)
    }

    interface Presenter : BasePresenter {
        fun authenticate(
            qatarId: String,
            phone: String,
            crnumber:String,
            countryCode:String,
            countryName:String,
            hash: String
        )
        fun fetchcountry()

    }
}