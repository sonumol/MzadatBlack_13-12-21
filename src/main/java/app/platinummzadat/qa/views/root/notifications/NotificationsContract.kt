package app.platinummzadat.qa.views.root.notifications

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.NotificationModel

interface NotificationsContract {
    interface View : BaseView<Presenter> {
        fun showData(dataSet: ArrayList<NotificationModel>)
    }
    interface Presenter : BasePresenter {
        fun fetchNotifications()
    }
}