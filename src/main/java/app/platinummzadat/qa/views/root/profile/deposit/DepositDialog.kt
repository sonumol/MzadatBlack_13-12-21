package app.platinummzadat.qa.views.root.profile.deposit

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import app.platinummzadat.qa.R
import app.platinummzadat.qa.RC_PAYMENT
import app.platinummzadat.qa.errorShake
import kotlinx.android.synthetic.main.deposit_alert_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivityForResult
import raj.nishin.wolfpack.value

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class DepositDialog : Dialog {
    private var result: ((amount: String) -> Unit)? = null

    constructor(result: (amount: String) -> Unit, context: Context?) : super(context!!) {
        this.result = result
    }

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, themeResId: Int) : super(context!!, themeResId)
    constructor(context: Context?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(
        context!!,
        cancelable,
        cancelListener
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.deposit_alert_dialog)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        btnSubmit.onClick {
            tilAmount.error = null
            if (etAmount.value.isNotEmpty()) {
                result?.invoke(etAmount.value)
                dismiss()
            } else {
                tilAmount.errorShake { etAmount.requestFocus() }
            }
        }
    }
}

fun Context.depositAlert(result: (amount: String) -> Unit) {
    DepositDialog(result, this).show()
}

