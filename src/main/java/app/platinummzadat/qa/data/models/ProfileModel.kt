package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 17:50 on Thursday 18 April 2019
 */
data class ProfileModel(
    @SerializedName("username") val username: String,
    @SerializedName("qid") val qid: String,
    @SerializedName("user_id") val user_id: String,

    @SerializedName("profile_photo") val profilePhoto: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("email") val email: String,
    @SerializedName("deposit_phone") val depositPhone: String,
//    @SerializedName("qatar_id_image")
    val qidImage: String="http://hhhh.hh",

    @SerializedName("qatar_id_image1") val qidImage1: String,
    @SerializedName("qatar_id_image2") val qidImage2: String,

    @SerializedName("qatar_id_status") val qidStatus: String,
    @SerializedName("user_bids") val userBids: Int,
    @SerializedName("show_upload") val showUpload: Boolean,

    @SerializedName("show_upload1") val showUpload1: Boolean,
    @SerializedName("show_upload2") val showUpload2: Boolean,

    @SerializedName("wining_bids") val winningBids: Int,
    @SerializedName("user_international") val user_international:Int,

    @SerializedName("deposit_amount") val depositAmount: Double
)

