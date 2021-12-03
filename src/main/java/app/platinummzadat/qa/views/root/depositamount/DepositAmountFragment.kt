package app.platinummzadat.qa.views.root.depositamount


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.AmountData
import app.platinummzadat.qa.data.models.DepositModel
import app.platinummzadat.qa.data.models.RefundRequestRes
import app.platinummzadat.qa.views.root.profile.deposit.FAILED
import app.platinummzadat.qa.views.root.profile.deposit.PaymentActivity
import app.platinummzadat.qa.views.root.profile.deposit.SUCCESS
import kotlinx.android.synthetic.main.custome_dialog_amout.*
import kotlinx.android.synthetic.main.fragment_my_deposit_amount.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import java.text.NumberFormat


class DepositAmountFragment : MzFragment(), DepositAmountContract.View {
    override lateinit var presenter: DepositAmountContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentListener?.setTitle(getString(R.string.nav_deposit_amount))
        presenter.fetchDepositAmount()
        tvIncreaseDeposit?.onClick {
            loading.visibility=View.VISIBLE
         //   presenter.getAmount()
            showDialog()
        }
        tvDepositRefund.setOnClickListener {
            this@DepositAmountFragment.requireContext().alert(Appcompat, getString(R.string.sure_you_wanna_proceed), "") {
                positiveButton(getString(R.string.proceed)) {
                    presenter.getRefundRequest( AppSignatureHelper(this@DepositAmountFragment.requireContext()).appSignatures[0])
                    it.dismiss()
                }
                negativeButton(getString(R.string.cancel)) {
                    it.dismiss()
                }
            }.show()

        }
    }


    override fun showData(data: DepositModel) {
        Log.d("TAGasd", data.toString())

       // tvDepositOffline?.visibility(visible)
        if(data.refund_request_status){
            tvDepositRefund.visibility=View.VISIBLE
            tvDepositRefund.isEnabled=false
            tvDepositRefund.isClickable=false
            tvDepositRefund.setBackgroundColor(resources.getColor(R.color.colorNavIcons))
            tvDepositRefund.setText(resources.getString(R.string.your_request_refund_is_progress))
        }else{
            tvDepositRefund.visibility=View.VISIBLE
            tvDepositRefund.isEnabled=true
            tvDepositRefund.isClickable=true
            tvDepositRefund.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            tvDepositRefund.setText(resources.getString(R.string.click_here_to_ireques_refund))
        }
        tvTotalDeposit?.text =
            getString(R.string.total_deposit_amount_format, NumberFormat.getNumberInstance().format(data.id))
        val depositOfflineString =getString(R.string.deposit_offline_format, data.depositPhone)
        setTextWithSpan(tvDepositOffline,depositOfflineString,data.depositPhone, ForegroundColorSpan(Color.parseColor("#0091EA")))
        tvDepositOffline.onClick {
            activity?.dialNumber(data.depositPhone)
        }
        rvDepositHistory?.layoutManager(LinearLayoutManager(activity))
        rvDepositHistory?.itemOffset(8)
        rvDepositHistory?.adapter = DepositAmountAdapter(data.details) {
            activity?.alert(
                Appcompat,
                "${getString(R.string.date)}: ${it.date}\n${getString(R.string.remarks)}: ${it.remarks}\n${getString(R.string.amount)}: ${it.amount}",
                getString(R.string.details)
            ) { positiveButton(getString(R.string.ok)) {} }
                ?.show()
        }
        content?.visibility(visible)

    }

    override fun showRefundData(data: RefundRequestRes) {

        showRefundDialog(data.message)
    }

    override fun showAmountData(data: AmountData) {
        loading.visibility=View.GONE
       //  showDialog(data.minimumDepositAmount)
//        activity?.depositAlert {
//            /*val builder = CustomTabsIntent.Builder()
//            val customTabsIntent = builder.build()
//            customTabsIntent.launchUrl(
//                activity,
//                Uri.parse("${RemoteDataSource.PAYMENT_URL}?user_id=$currentUserId&amount=$it")
//            )*/
//            startActivityForResult<PaymentActivity>(RC_PAYMENT, "amount" to data.minimumDepositAmount.toString())
//        }
    }
    private fun showDialog() {
        val dialog = Dialog(this@DepositAmountFragment.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custome_dialog_amout)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val body = dialog.findViewById(R.id.etAmount) as TextInputEditText
      //  body.setText(amt)
        val btnPayment = dialog.findViewById(R.id.btnSubmit) as MaterialButton
        btnPayment.setOnClickListener {
            if(body.text!!.isNotEmpty()){
                startActivityForResult<PaymentActivity>(RC_PAYMENT, "amount" to body.text.toString())
                dialog.dismiss()
            }else
                dialog.tilAmount.error=resources.getString(R.string.enter_deposite_amount)

        }
        dialog.show()

    }
    private fun showRefundDialog(amt: String) {
        val dialog = Dialog(this@DepositAmountFragment.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.refund_info_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val body = dialog.findViewById(R.id.tvName) as TextView
        body.setText(amt)
        val btnPayment = dialog.findViewById(R.id.btnSubmit) as MaterialButton
        btnPayment.setOnClickListener {
            presenter.fetchDepositAmount()
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        content?.visibility(gone)
        noData?.visibility(gone)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
        fragmentListener?.onError {
            presenter.fetchDepositAmount()
        }
    }

    override fun showEmptyData() {
        noData?.visibility(visible)
       // Toast.makeText(activity,"aj",Toast.LENGTH_LONG).show()
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DepositAmountPresenter(this)
        return super.onCreateView(R.layout.fragment_my_deposit_amount, inflater, container)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_PAYMENT) {
            if (resultCode == SUCCESS) {
                activity?.alert(
                    Appcompat,
                    "Payment completed successfully",
                    "Payment"
                ) { okButton { it.dismiss() } }?.show()
            } else if (resultCode == FAILED) {
                activity?.alert(
                    Appcompat,
                    "Payment failed. Please try again",
                    "Payment"
                ) { okButton { it.dismiss() } }?.show()
            } else {
                toast("Payment cancelled by user")
            }
        }
    }

}
