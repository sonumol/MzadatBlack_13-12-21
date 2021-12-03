package app.platinummzadat.qa.views.root.dasoboard1

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.DashboardModel
import app.platinummzadat.qa.data.models.FavAutionIdsRes


interface DashboardContract1 {
    interface View : BaseView<Presenter> {
        fun showDashboard(data: DashboardModel)
        fun showFavIds(data: FavAutionIdsRes)
        fun lastActiveUpdated()
    }
    interface Presenter : BasePresenter {
        fun fetchDashboard1()
        fun fetchFavAutionsIDs()
        fun updateLastActive()
    }
}