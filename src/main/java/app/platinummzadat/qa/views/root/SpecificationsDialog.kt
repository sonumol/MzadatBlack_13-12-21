package app.platinummzadat.qa.views.root

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.KeyValueModel
import kotlinx.android.synthetic.main.specs_alert_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.itemOffset

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class SpecificationsDialog : Dialog {
    private var data: ArrayList<KeyValueModel> = ArrayList()

    constructor(
        data: ArrayList<KeyValueModel>,
        context: Context
    ) : super(context) {
        this.data = data
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
        setContentView(R.layout.specs_alert_dialog)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rvSpecs?.layoutManager = LinearLayoutManager(context)
        rvSpecs?.itemOffset(8)
        rvSpecs?.adapter = SpecificationsAdapter(data)
        btnClose.onClick {
            dismiss()
        }
    }
}

fun Context.specsAlert(data: ArrayList<KeyValueModel>) {
    SpecificationsDialog(data, this).show()
}