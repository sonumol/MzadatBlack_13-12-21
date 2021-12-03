package app.platinummzadat.qa.views.root.tac


import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.TacModel
import kotlinx.android.synthetic.main.fragment_terms_and_conditions.*
import raj.nishin.wolfpack.gone
import raj.nishin.wolfpack.visibility
import raj.nishin.wolfpack.visible

class TermsAndConditionsFragment : MzFragment(), TermsAndConditionsContract.View {

    override lateinit var presenter: TermsAndConditionsContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchTac()
    }

    override fun showTac(data: TacModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTerms.setText(Html.fromHtml( data.description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTerms.setText(Html.fromHtml( data.description));
        }

     //   tvTerms?.text = data.description
        tvTerms?.visibility(visible)
    }

    override fun showNoInternet() {
    }

    override fun showLoading() {
        tvTerms?.visibility(gone)
        loading?.visibility(visible)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
        fragmentListener?.onError { presenter.fetchTac() }
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TermsAndConditionsPresenter(this)
        return super.onCreateView(R.layout.fragment_terms_and_conditions, inflater, container)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.nav_terms_and_conditions))
    }

}
