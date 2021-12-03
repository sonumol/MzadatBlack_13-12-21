package app.platinummzadat.qa.views.registration.password

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView

interface PasswordContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
        fun showDuplicate()
    }

    interface Presenter : BasePresenter {
        fun setPassword(password: String)
        fun updateProfile(name: String, email: String,countryCode :String, password: String)
    }
}