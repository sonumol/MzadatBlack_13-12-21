package app.platinummzadat.qa.views.root

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.DashboardModel

interface RootContract {
    interface View : BaseView<Presenter> {
        fun lastActiveUpdated()
        fun showDashboard(data: DashboardModel)
    }
    interface Presenter : BasePresenter {
        fun updateLastActive()
        fun fetchDashboard()
    }
}