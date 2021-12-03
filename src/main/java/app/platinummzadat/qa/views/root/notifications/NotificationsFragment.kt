package app.platinummzadat.qa.views.root.notifications


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.NotificationModel
import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_notifications.*
import raj.nishin.wolfpack.*


class NotificationsFragment : MzFragment(), NotificationsContract.View {
    override lateinit var presenter: NotificationsContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchNotifications()
    }

    override fun showData(dataSet: ArrayList<NotificationModel>) {
        rvNotifications?.layoutManager(LinearLayoutManager(activity))
        rvNotifications?.itemOffset(8)
        rvNotifications?.adapter = NotificationsAdapter(dataSet)
        rvNotifications?.visibility(visible)
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        noData?.visibility(gone)
        rvNotifications?.visibility(gone)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showEmptyData() {
        noData?.visibility(visible)
    }

    override fun showApiError() {
        fragmentListener?.onError {
            presenter.fetchNotifications()
        }
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        NotificationsPresenter(this)
        return super.onCreateView(R.layout.fragment_notifications, inflater, container)
    }


    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.nav_notifications))
    }
}
