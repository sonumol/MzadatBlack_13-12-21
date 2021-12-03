package app.platinummzadat.qa.views.root.faq

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.ContactUsModel
import app.platinummzadat.qa.data.models.FAQData
import app.platinummzadat.qa.data.models.FaqRes

interface FAQContract {
    interface View : BaseView<Presenter> {
        fun showData(data: FaqRes)
    }

    interface Presenter : BasePresenter {
        fun fetchFaq()
    }
}