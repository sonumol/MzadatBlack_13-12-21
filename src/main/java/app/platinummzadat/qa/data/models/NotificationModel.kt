package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 14:13 on Wednesday 24 April 2019
 */
data class NotificationModel(
    @SerializedName("id") val id:Int,
    @SerializedName("date") val date:String,
    @SerializedName("text") val content:String
)