package app.platinummzadat.qa.views.root.companyfees

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.CompanyFeesData
import app.platinummzadat.qa.data.models.CompanyFeesModel
import app.platinummzadat.qa.data.models.CompanyFeesRespose

interface CompanyFeesContract {
    interface View : BaseView<Presenter> {
        fun showData(data: CompanyFeesRespose)
    }
    interface Presenter : BasePresenter {
        fun fetchFees()
    }
}