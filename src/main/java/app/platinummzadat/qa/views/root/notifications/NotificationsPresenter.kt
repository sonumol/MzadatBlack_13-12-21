package app.platinummzadat.qa.views.root.notifications

import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 14:29 on Wednesday 24 April 2019
 */
class NotificationsPresenter(private val view:NotificationsContract.View):NotificationsContract.Presenter {
    init {
        view.presenter = this
    }
    override fun fetchNotifications() {
        view.showLoading()
        MzRepo.fetchNotifications(currentUserId){ status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                data.isEmpty() -> {
                    view.showEmptyData()
                }
                status -> {
                    view.showData(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}