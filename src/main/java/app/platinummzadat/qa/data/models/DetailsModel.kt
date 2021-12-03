package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import app.platinummzadat.qa.R
import raj.nishin.wolfpack.getMillisFromDate
import raj.nishin.wolfpack.getTimeFromMillis
import java.io.Serializable

/**
 * Created by WOLF
 * at 16:07 on Wednesday 03 April 2019
 */
data class DetailsModel(
        @SerializedName("id") val id: Int,

        @SerializedName("user_blocked") val user_blocked: Boolean,
        @SerializedName("category") val categoryId: Int,
        @SerializedName("title") val name: String,
        @SerializedName("reference") val referenceNumber: String,
        @SerializedName("title_arabic") val arabicName: String,
        @SerializedName("location") val location: String,
        @SerializedName("company") val company: String,
        @SerializedName("closing_date") val endDate: String,
        @SerializedName("closing_time") val endTime: String,
        @SerializedName("qatar_date") val qatarDate: String,
        @SerializedName("qatar_time") val qatarTime: String,
        @SerializedName("auction_status") val auction_status: Int,
        @SerializedName("starting_time") val starting_time: String,
        @SerializedName("starting_date") val starting_date: String,
        @SerializedName("button_name") val button_name: String,
        @SerializedName("previous_bid_amount") val price: Double,
        @SerializedName("inspection_lat") val inspectionLat: String,
        @SerializedName("inspection_long") val inspectionLng: String,
        @SerializedName("inspection_report") val inspectionReport: String,
        @SerializedName("description") val description: String,
        @SerializedName("terms") val terms: String,
        @SerializedName("inspect_name") val inspectionReportTitle: String,
        @SerializedName("specification") val specifications: ArrayList<KeyValueModel>,
        @SerializedName("description_arabic") val descriptionArabic: String,
        @SerializedName("web_url") val webUrl: String,
        @SerializedName("company_logo") val companyLogo: String?,
        @SerializedName("min_increment_amount") val increment: Double,
        @SerializedName("bid_status") val bidStatus: BidStatusModel,
        @SerializedName("previous_bid") val previousBid: Boolean,
        @SerializedName("bid_count") val totalBids: Int,
        @SerializedName("whish_list") val wishList: Int,
        @SerializedName("deposit_amount") val deposit_amount: Double,
        @SerializedName("images") val images: ArrayList<String>,
        @SerializedName("judge_live_button_text") val judge_live_button_text: String?,
        @SerializedName("judge_live_link") val judge_live_link: String?,
        @SerializedName("brochure_link") val brochure_link: String?,
        @SerializedName("price_label") val price_label: String,
        @SerializedName("price_label_name") val price_label_name: String,
        @SerializedName("prev_user_country") val prev_user_country: String,
        @SerializedName("prev_user_msg") val prev_user_msg: String,


       @SerializedName("brochure_button_text") val brochure_button_text: String?
) : Serializable {

//    val isInWishList: Boolean
//        get() = when (wishList) {
//            0 -> false
//            else -> true
//        }



    val endTimeStamp: String
        get() = if (auction_status==3) "1970-01-01#00:00:00" else "$endDate#$endTime"

    val getUpcomingTime: String
        get() = if (auction_status==2) "$starting_date#$starting_time" else "1970-01-01#00:00:00"



    val qatarTimeStamp: String
        get() = "$qatarDate#$qatarTime"

    val totalBidsBadge: String
        get() = "$totalBids"
//    val followAuctionString: Int
//        get() = if (isInWishList) R.string.following_auction else R.string.follow_this_auction
//    val followAuctionImage: Int
//        get() = if (isInWishList) R.drawable.ic_item_view_unfollow_auction else R.drawable.ic_item_view_follow_auction
    val endingDetails: String
        get() = "${getTimeFromMillis(getMillisFromDate(endDate, "yyyy-MM-dd"), "dd MMM")} ${
            getTimeFromMillis(
                    getMillisFromDate(endTime, "HH:mm:ss"),
                    "hh:mm a"
            )
        }"
    val upcomingStartingDetails: String
        get() = "${getTimeFromMillis(getMillisFromDate(starting_date, "yyyy-MM-dd"), "dd MMM")} ${
            getTimeFromMillis(
                    getMillisFromDate(starting_time, "HH:mm:ss"),
                    "hh:mm a"
            )
        }"
    val millisUntilExpiry: Long
        get() = getMillisFromDate(endTimeStamp, "yyyy-MM-dd#HH:mm:ss") - getMillisFromDate(
                qatarTimeStamp,
                "yyyy-MM-dd#HH:mm:ss"
        )//currentLocalTimeInMillis
    val upComimgBidTimer: Long
        get() = getMillisFromDate(getUpcomingTime, "yyyy-MM-dd#HH:mm:ss") - getMillisFromDate(
                qatarTimeStamp,
                "yyyy-MM-dd#HH:mm:ss"
        )//currentLocalTimeInMillis
}


/*
data class DetailRes(
        @SerializedName("data")
        val `data`: AuctDats,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)

data class AuctDats(
        @SerializedName("approved_flag")
        val approvedFlag: Int,
        @SerializedName("auction_status")
        val auctionStatus: Int,
        @SerializedName("bid_count")
        val bidCount: Int,
        @SerializedName("bid_status")
        val bidStatus: BidStatus,
        @SerializedName("blocked_flag")
        val blockedFlag: Int,
        @SerializedName("button_name")
        val buttonName: String,
        @SerializedName("category")
        val category: String,
        @SerializedName("closing_date")
        val closingDate: String,
        @SerializedName("closing_time")
        val closingTime: String,
        @SerializedName("company")
        val company: String,
        @SerializedName("company_logo")
        val companyLogo: String,
        @SerializedName("deposit_amount")
        val depositAmount: String,
        @SerializedName("deposit_flag")
        val depositFlag: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("images")
        val images: List<String>,
        @SerializedName("inspect_name")
        val inspectName: String,
        @SerializedName("inspection_lat")
        val inspectionLat: String,
        @SerializedName("inspection_long")
        val inspectionLong: String,
        @SerializedName("inspection_report")
        val inspectionReport: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("min_increment_amount")
        val minIncrementAmount: String,
        @SerializedName("previous_bid")
        val previousBid: Boolean,
        @SerializedName("previous_bid_amount")
        val previousBidAmount: String,
        @SerializedName("previous_bid_user")
        val previousBidUser: Int,
        @SerializedName("qatar_date")
        val qatarDate: String,
        @SerializedName("qatar_time")
        val qatarTime: String,
        @SerializedName("reference")
        val reference: String,
        @SerializedName("specification")
        val specification: List<Any>,
        @SerializedName("starting_date")
        val startingDate: String,
        @SerializedName("starting_time")
        val startingTime: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("terms")
        val terms: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("user_blocked")
        val userBlocked: Boolean,
        @SerializedName("web_url")
        val webUrl: String,
        @SerializedName("whish_list")
        val whishList: Int
)

data class BidStatus(
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("reason")
        val reason: String
)*/
