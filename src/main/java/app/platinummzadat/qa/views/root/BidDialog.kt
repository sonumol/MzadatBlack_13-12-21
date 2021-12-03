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
import app.platinummzadat.qa.errorShake
import kotlinx.android.synthetic.main.bid_alert_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.text.NumberFormat

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class BidDialog : Dialog {
    private var yesAction: ((amount: Double) -> Unit)? = null
    private var currentBid: Double = (-1).toDouble()
    private var increment: Double = (-1).toDouble()

    constructor(
        yesAction: ((amount: Double) -> Unit)?,
        currentBid: Double,
        increment: Double,
        context: Context
    ) : super(context) {
        this.yesAction = yesAction
        this.currentBid = currentBid
        this.increment = increment
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
        setContentView(R.layout.bid_alert_dialog)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        var amount = currentBid + increment
        tvAmount.text = "QAR: ${NumberFormat.getNumberInstance().format(amount)}"
        ivSubtract.onClick {
            if (amount > currentBid + increment) {
                amount -= increment
                setAmount(amount)
            } else {
                context.toast(
                    context.getString(
                        R.string.minimum_bid_amount_format,
                        NumberFormat.getNumberInstance().format(currentBid + increment)
                    )
                )
            }
        }
        ivAdd.onClick {
            amount += increment
            setAmount(amount)
        }
        tvAmount.onClick {
            context.bidManualAmountAlert(currentBid + increment) {
                amount = try {
                    it.toDouble()
                } catch (e: Exception) {
                    currentBid + increment
                }
                setAmount(amount)
            }
        }
        btnPlaceBid.onClick {
            if (amount > currentBid) {
                if (cb.isChecked) {
                    yesAction?.invoke(amount)
                    dismiss()
                } else {
                    cb.errorShake {
                        context.toast(context.getString(R.string.accept_tac_to_place_bid))
                    }
                }
            } else {
                tvAmount.errorShake {
                    context.toast(
                        context.getString(
                            R.string.minimum_bid_amount_format,
                            NumberFormat.getNumberInstance().format(currentBid + increment)
                        )
                    )
                }
            }
        }
    }

    private fun setAmount(amount: Double) {
        tvAmount.text = "QAR: ${NumberFormat.getNumberInstance().format(amount)}"
    }
}

fun Context.bidAlert(currentBid: Double, increment: Double, yesAction: ((amount: Double) -> Unit)) {
    BidDialog(yesAction, currentBid, increment, this).show()
}