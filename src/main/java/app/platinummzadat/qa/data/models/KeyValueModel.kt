package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 15:32 on Thursday 02 May 2019
 */
data class KeyValueModel(
    @SerializedName("key") val key: String,
    @SerializedName("value") val value: String
)