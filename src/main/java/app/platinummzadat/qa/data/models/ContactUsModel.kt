package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 10:17 on Monday 24 June 2019
 */
data class ContactUsModel(
    @SerializedName("address") val address: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("call") val call: String,
    @SerializedName("whatsapp") val whatsapp: String,
    @SerializedName("fax") val fax: String,
    @SerializedName("email") val email: String
) {
}