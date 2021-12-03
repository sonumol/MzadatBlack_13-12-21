package app.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import raj.nishin.wolfpack.getMillisFromDate
import java.io.Serializable
import java.text.NumberFormat

/**
 * Created by WOLF
 * at 16:12 on Tuesday 02 April 2019
 */
data class AuctionItemModel(
    @SerializedName("id") val id: Int,
    @SerializedName("listing_count") val listing_count: Int,

    @SerializedName("title") val name: String,
    @SerializedName("image") val imgUrl: String,
    @SerializedName("closing_date") val endDate: String,
    @SerializedName("closing_time") val endTime: String,
    @SerializedName("starting_date") val starting_date: String,
    @SerializedName("starting_time") val starting_time: String,
    @SerializedName("qatar_date") val qatarDate: String,
    @SerializedName("qatar_time") val qatarTime: String,
    @SerializedName("previous_bid_amount") val price: Double,
    @SerializedName("previous_bid") val previousBid: Boolean,
    @SerializedName("button_name") val button_name: String,
    @SerializedName("auction_status") val auction_status: Int,
    @SerializedName("status") val status: String,
    @SerializedName("price_label") val price_label: String,
    @SerializedName("price_label_name") val price_label_name: String,
    @SerializedName("min_increment_amount") val increment: Double
) : Serializable {
    val endTimeStamp: String
        get() = if (auction_status==3) "1970-01-01#00:00:00" else "$endDate#$endTime"

    val getUpcomingTime: String
        get() = if (auction_status==2) "$starting_date#$starting_time" else "1970-01-01#00:00:00"

    val qatarTimeStamp: String
        get() = "$qatarDate#$qatarTime"

    val priceString: String
        get() = NumberFormat.getNumberInstance().format(price)
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


data class AuctionDataRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class Data(
    @SerializedName("auction_status")
    val auctionStatus: Int,
    @SerializedName("button_name")
    val buttonName: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("closing_date")
    val closingDate: String,
    @SerializedName("closing_time")
    val closingTime: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("min_increment_amount")
    val minIncrementAmount: String,
    @SerializedName("previous_bid")
    val previousBid: Boolean,
    @SerializedName("previous_bid_amount")
    val previousBidAmount: String,
    @SerializedName("qatar_date")
    val qatarDate: String,
    @SerializedName("qatar_time")
    val qatarTime: String,
    @SerializedName("starting_date")
    val startingDate: String,
    @SerializedName("starting_time")
    val startingTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String
)