package app.platinummzadat.qa.views.root.contactus

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.ContactUsModel

interface ContactUsContract {
    interface View : BaseView<Presenter> {
        fun showData(data: ContactUsModel)
    }

    interface Presenter : BasePresenter {
        fun fetchContactUs()
    }
}