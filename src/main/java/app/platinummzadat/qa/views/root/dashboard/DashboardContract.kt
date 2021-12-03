package app.platinummzadat.qa.views.root.dashboard

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.DashboardModel
import app.platinummzadat.qa.data.models.FavAutionIdsRes


interface DashboardContract {
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