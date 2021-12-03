package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 14:22 on Wednesday 24 April 2019
 */
data class DepositModel(
    @SerializedName("deposit_amount") val id: Int,
    @SerializedName("deposit_phone") val depositPhone: String,
    @SerializedName("refund_request_status") val refund_request_status: Boolean,
    @SerializedName("details") val details: ArrayList<DepositDetailsModel>
)