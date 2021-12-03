package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 14:31 on Monday 13 May 2019
 */
data class TacModel(
    @SerializedName("title") val title:String,
    @SerializedName("description") val description:String
) {
}