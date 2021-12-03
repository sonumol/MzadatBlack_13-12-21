package com.platinummzadat.qa.views.root.companyfees

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView

interface WinningBidsPaymentContract {
    interface View : BaseView<Presenter> {
        fun showData(data: String)
    }
    interface Presenter : BasePresenter {
        fun winningBidsPayment(bidId: Int)
    }
}