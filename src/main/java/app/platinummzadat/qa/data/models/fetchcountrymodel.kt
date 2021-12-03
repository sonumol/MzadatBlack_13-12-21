package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 10:15 on Monday 24 June 2019
 */
data class fetchcountrymodel(
    @SerializedName("excluded_country") val excluded_country: String

) {
}