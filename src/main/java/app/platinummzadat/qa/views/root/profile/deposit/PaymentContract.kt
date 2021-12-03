package app.platinummzadat.qa.views.root.profile.deposit

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView

interface PaymentContract {
    interface View : BaseView<Presenter> {
        fun showData(data: String)
    }
    interface Presenter : BasePresenter {
        fun doPayment(amount: String)
    }
}