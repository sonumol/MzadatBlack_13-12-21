package app.platinummzadat.qa.views.root.contactus


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.ContactUsModel
import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_contact_us.*
import org.jetbrains.anko.email
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.dialNumber
import raj.nishin.wolfpack.gone
import raj.nishin.wolfpack.visibility
import raj.nishin.wolfpack.visible


class ContactUsFragment : MzFragment(), ContactUsContract.View {
    override lateinit var presenter: ContactUsContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLoad){
            presenter.fetchContactUs()
        }
    }

    override fun showData(data: ContactUsModel) {
        llPhone.onClick {
            activity?.dialNumber(data.call)
        }
        whatsapp.onClick {
            val url="https://api.whatsapp.com/send?phone=${data.whatsapp}"
            val i=Intent(Intent.ACTION_VIEW)
            i.data=Uri.parse(url)
            startActivity(i)
        }
        llEmail.onClick {
            activity?.email(data.email)
        }
        tvEmail?.text = data.email
        tvAddress?.text = data.address
        tvPhone?.text = "Tel ${data.telephone}"
        tvwhatsapp.text=data.whatsapp
        content?.visibility(visible)
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        content?.visibility(gone)
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
        ContactUsPresenter(this)
        return super.onCreateView(R.layout.fragment_contact_us, inflater, container)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.nav_contact_us))
    }
}
