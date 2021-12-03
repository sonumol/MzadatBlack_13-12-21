package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by WOLF
 * at 14:53 on Thursday 18 April 2019
 */
data class BidStatusModel(
    @SerializedName("enabled") val enabled: Boolean,
    @SerializedName("reason") val reason: String
) : Serializable