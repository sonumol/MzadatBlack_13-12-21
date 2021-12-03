package app.platinummzadat.qa.views.root.aboutus

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.AboutUsModel

interface AboutUsContract {
    interface View : BaseView<Presenter> {
        fun showData(data: AboutUsModel)
    }

    interface Presenter : BasePresenter {
        fun fetchAboutUs()
    }
}