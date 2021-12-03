package app.platinummzadat.qa.views.root.dashboard2home

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.DashboardModel
import app.platinummzadat.qa.data.models.FavAutionIdsRes


interface DashboardContracthome {
    interface View : BaseView<Presenter> {
        fun showDashboard(data: DashboardModel)
        fun showFavIds(data: FavAutionIdsRes)
        fun lastActiveUpdated()
    }
    interface Presenter : BasePresenter {
        fun fetchDashboard(type:Int)
        fun fetchFavAutionsIDs()
        fun updateLastActive()
    }
}