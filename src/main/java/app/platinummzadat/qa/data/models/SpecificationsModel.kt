package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by WOLF
 * at 15:25 on Saturday 20 April 2019
 */
data class SpecificationsModel(
    @SerializedName("make") val make: String,
    @SerializedName("model") val model: String,
    @SerializedName("year") val year: String,
    @SerializedName("exterior") val color: String,
    @SerializedName("transmission") val transmission: String,
    @SerializedName("fuel") val fuel: String,
    @SerializedName("keys_count") val keys: String,
    @SerializedName("odometer") val km: String,
    @SerializedName("body_type") val bodyType: String
):Serializable {
}