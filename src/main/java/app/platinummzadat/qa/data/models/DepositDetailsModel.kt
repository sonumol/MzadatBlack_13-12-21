package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import app.platinummzadat.qa.R

/**
 * Created by WOLF
 * at 14:23 on Wednesday 24 April 2019
 */
data class DepositDetailsModel(
    @SerializedName("date") val date: String,
    @SerializedName("mode") val remarks: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("payment_type") val paymentType: Int
) {
    val textColor: Int
        get() = when (paymentType) {
            1 -> R.color.colorDepositAmountRed
            else -> R.color.colorDepositAmountBlack
        }
}