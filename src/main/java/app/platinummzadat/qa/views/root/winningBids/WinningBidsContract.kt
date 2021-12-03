package com.platinummzadat.qa.views.root.companyfees

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.WinningBidsDetails

interface WinningBidsContract {
    interface View : BaseView<Presenter> {
        fun showData(data: ArrayList<WinningBidsDetails>)
    }
    interface Presenter :BasePresenter {
        fun winningBids()
    }
}