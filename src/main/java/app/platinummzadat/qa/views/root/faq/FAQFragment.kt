package app.platinummzadat.qa.views.root.faq


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.FaqRes
import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_faq.*
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*


class FAQFragment : MzFragment(), FAQContract.View {
    override lateinit var presenter: FAQContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLoad){
            presenter.fetchFaq()
        }
    }

    override fun showData(data: FaqRes) {
        loading?.visibility=View.GONE

        rv_content?.layoutManager(LinearLayoutManager(activity))
        rv_content?.itemOffset(8)
        rv_content?.adapter = FAQAdapter(data.data)
        rv_content?.visibility(visible)
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        rv_content?.visibility(gone)
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
        FAQPresenter(this)
        return super.onCreateView(R.layout.fragment_faq, inflater, container)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.nav_faq))
    }
}
