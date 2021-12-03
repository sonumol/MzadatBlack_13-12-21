package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Badhusha
 * at 10:55 on Thursday 09 Oct 2020
 */

data class WinningBidsDetails(
        @SerializedName("bid_amount") val bid_amount: String,
        @SerializedName("id") val id: String,
        @SerializedName("image") val image: String,
        @SerializedName("payment_date") val payment_date: String,
        @SerializedName("payment_status") val payment_status: String,
        @SerializedName("title") val title: String
)
