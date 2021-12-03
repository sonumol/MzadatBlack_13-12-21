package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 10:57 on Tuesday 02 April 2019
 */
data class DashboardModel(
    @SerializedName("username") val username: String,
    @SerializedName("user_id") val user_id: String,

    @SerializedName("company_name") val company_name: String,
    @SerializedName("profile_photo") val profilePhoto: String,
    @SerializedName("mobile") val mobileNumber: String,
    @SerializedName("last_login") val lastLogin: String,
    @SerializedName("scroll_text") val scrollText: String,
    @SerializedName("show_qid_upload") val showQidUpload: Boolean,
    @SerializedName("qid_message") val qidMessage: String?,
    @SerializedName("user_blocked") val blocked: Boolean?,
    @SerializedName("version_name") val version_name: String?,
    @SerializedName("product_count") val product_count:Int,
    @SerializedName("user_approved") val user_approved:Int,
    @SerializedName("user_international") val user_international:Int,
    @SerializedName("category") val items: ArrayList<DashboardItemModel>
)