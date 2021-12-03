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
import kotlinx.android.synthetic.main.by_product_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

import java.text.NumberFormat

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class BuyproductDialog : Dialog {
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
        setContentView(R.layout.by_product_dialog)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        var amount = currentBid + increment

        btnPlaceBid.onClick {
            yesAction?.invoke(amount)
            dismiss()
        }
        no.onClick {
            dismiss()
        }
    }

    private fun setAmount(amount: Double) {
        //tvAmount.text = "QAR: ${NumberFormat.getNumberInstance().format(amount)}"
    }
}

fun Context.bidAlert1(currentBid: Double, increment: Double, yesAction: ((amount: Double) -> Unit)) {
    BuyproductDialog(yesAction, currentBid, increment, this).show()
}