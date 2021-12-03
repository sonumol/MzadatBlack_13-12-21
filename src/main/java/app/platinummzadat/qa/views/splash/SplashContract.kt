package app.platinummzadat.qa.views.splash

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.SplashModel

interface SplashContract {
    interface View : BaseView<Presenter> {
        fun showSuccess(data: SplashModel)
        fun showBlocked()

    }
    interface Presenter : BasePresenter {
        fun splash(type: Int)
    }
}