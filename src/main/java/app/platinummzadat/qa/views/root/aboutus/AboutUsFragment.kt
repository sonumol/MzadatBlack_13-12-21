package app.platinummzadat.qa.views.root.aboutus


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.AboutUsModel
import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_about_us.*
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.gone
import raj.nishin.wolfpack.loadFromUrl
import raj.nishin.wolfpack.visibility
import raj.nishin.wolfpack.visible


class AboutUsFragment : MzFragment(), AboutUsContract.View {
    override lateinit var presenter: AboutUsContract.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLoad) {
            presenter.fetchAboutUs()
        }
    }

    override fun showData(data: AboutUsModel) {
        tvTitle?.text = data.title
        tvDescription?.text = data.description
        if ("" != data.image && null != data.image)
            ivAboutUs.loadFromUrl(data.image)
        content?.visibility(visible)
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        content?.visibility(gone)
        loading?.visibility(visible)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
        toast(getString(R.string.some_error_occurred_try_again))
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AboutUsPresenter(this)
        return super.onCreateView(R.layout.fragment_about_us, inflater, container)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.nav_about_us))
    }

}
