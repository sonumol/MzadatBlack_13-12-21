package app.platinummzadat.qa.views.root.tac

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.TacModel

interface TermsAndConditionsContract {
    interface View : BaseView<Presenter> {
        fun showTac(data: TacModel)
    }
    interface Presenter :BasePresenter {
        fun fetchTac()
    }
}