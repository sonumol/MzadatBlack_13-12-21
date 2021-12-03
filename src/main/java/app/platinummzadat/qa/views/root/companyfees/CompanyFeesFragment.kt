package app.platinummzadat.qa.views.root.companyfees


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.CompanyFeesModel
import app.platinummzadat.qa.data.models.CompanyFeesRespose
import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_company_fees.*
import raj.nishin.wolfpack.*


class CompanyFeesFragment : MzFragment(),CompanyFeesContract.View {
    override lateinit var presenter: CompanyFeesContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchFees()
    }
    override fun showData(data: CompanyFeesRespose) {
        rvFees?.layoutManager(LinearLayoutManager(activity))
        rvFees?.itemOffset(8)
        tvMessage?.text = data.message
        rvFees?.adapter = CompanyFeesAdapter(data.data)
        llData?.visibility(visible)
    }


    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        noData?.visibility(gone)
        llData?.visibility(gone)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showEmptyData() {
        noData?.visibility(visible)
    }
    override fun showApiError() {
        fragmentListener?.onError {
            presenter.fetchFees()
        }
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CompanyFeesPresenter(this)
        return inflater.inflate(R.layout.fragment_company_fees, container, false)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.nav_company_fees))
    }
}
