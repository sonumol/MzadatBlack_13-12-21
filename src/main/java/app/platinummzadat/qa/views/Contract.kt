package app.platinummzadat.qa.views

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView

interface Contract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter :BasePresenter {

    }
}