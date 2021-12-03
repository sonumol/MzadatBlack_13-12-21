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
import kotlinx.android.synthetic.main.feedback_alert_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.value

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class FeedbackDialog : Dialog {
    private var result: ((feedback: String) -> Unit)? = null

    constructor(result: (feedback: String) -> Unit, context: Context?) : super(context) {
        this.result = result
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
        setContentView(R.layout.feedback_alert_dialog)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        btnSubmit.onClick {
            tilFeedback.error = null
            if (etFeedback.value.isNotEmpty()) {
                result?.invoke(etFeedback.value)
                dismiss()
            } else {
                tilFeedback.error = context.getString(R.string.feed_back_cant_be_empty)
            }
        }
    }
}

fun Context.feedbackAlert(result: (feedback: String) -> Unit) {
    FeedbackDialog(result, this).show()
}