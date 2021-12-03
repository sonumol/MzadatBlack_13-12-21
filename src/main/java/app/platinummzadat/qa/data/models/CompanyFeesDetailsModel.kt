package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 10:55 on Thursday 15 August 2019
 */
data class CompanyFeesDetailsModel(
    @SerializedName("price_from") val priceFrom: String,
    @SerializedName("price_to") val priceTo: String,
    @SerializedName("fees") val fees: String
)