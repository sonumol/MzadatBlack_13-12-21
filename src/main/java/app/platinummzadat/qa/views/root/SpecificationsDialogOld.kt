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
import app.platinummzadat.qa.data.models.SpecificationsModel
import kotlinx.android.synthetic.main.specs_alert_dialog_old.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by WOLF
 * at 21:32 on Wednesday 03 April 2019
 */
class SpecificationsDialogOld : Dialog {
    private var data: SpecificationsModel? = null

    constructor(
        data: SpecificationsModel,
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
        setContentView(R.layout.specs_alert_dialog_old)
        setCancelable(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        data?.let {
            tvMake.text = it.make
            tvModel.text = it.model
            tvYear.text = it.year
            tvColor.text = it.color
            tvTransmission.text = it.transmission
            tvFuel.text = it.fuel
            tvKey.text = it.keys
            tvKm.text = it.km
            tvBodyType.text = it.bodyType
        }
        btnClose.onClick {
            dismiss()
        }
    }
}

//fun Context.specsAlert(data: SpecificationsModel) {
//    SpecificationsDialog(data, this).show()
//}