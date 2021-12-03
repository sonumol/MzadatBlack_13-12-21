package app.platinummzadat.qa.views.root

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import app.platinummzadat.qa.R
import kotlinx.android.synthetic.main.bid_manual_amount_alert_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.value
import java.text.NumberFormat

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class BidManualAmountDialog : Dialog {
    private var result: ((feedback: String) -> Unit)? = null
    private var minAmount = (-1).toDouble()

    constructor(minAmount: Double, result: (feedback: String) -> Unit, context: Context?) : super(context) {
        this.result = result
        this.minAmount = minAmount
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, themeResId: Int) : super(context, themeResId)
    constructor(context: Context?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(
        context,
        cancelable,
        cancelListener
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.bid_manual_amount_alert_dialog)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        btnSubmit.onClick {
            tilAmount.error = null
            if (etAmount.value.isNotEmpty()) {
                val amount = try {
                    etAmount.value.toDouble()
                } catch (e: Exception) {
                    minAmount
                }

                if (amount >= minAmount) {
                    result?.invoke(etAmount.value)
                    dismiss()
                }else{
                    tilAmount.error = "Minimum amount is ${NumberFormat.getNumberInstance().format(minAmount)}"
                }
            } else {
                tilAmount.error = "Minimum amount is ${NumberFormat.getNumberInstance().format(minAmount)}"
            }
        }
    }
}

fun Context.bidManualAmountAlert(minAmount: Double, result: (feedback: String) -> Unit) {
    BidManualAmountDialog(minAmount, result, this).show()
}