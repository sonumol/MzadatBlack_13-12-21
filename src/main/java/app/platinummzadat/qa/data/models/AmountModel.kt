package app.platinummzadat.qa.data.models
import com.google.gson.annotations.SerializedName


data class AmountModel(
    @SerializedName("data")
    val `data`: AmountData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class AmountData(
    @SerializedName("minimum_deposit_amount")
    val minimumDepositAmount: String
)



data class DashResponse(
        @SerializedName("data")
        val `data`: DashboardModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)
data class DashResponse1(
    @SerializedName("data")
    val `data`: DashboardModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
data class SplashModelRes(
        @SerializedName("data")
        val `data`: SplashModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean,
         @SerializedName("product_count")
       val product_count: Int,
       @SerializedName("user_type")
      val user_type: Int
)

data class FaqRes(
    @SerializedName("data")
    val `data`: ArrayList<FAQData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class CheckCompanyRegisterRes(
        @SerializedName("data")
        val `data`: CompanyData,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: String
)

data class ComputerCheckRes(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class CompanyRegistrationRes(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class checkConmpanyStatus(
    @SerializedName("data")
    val `data`: CompanyData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class CompanyData(
    @SerializedName("authorization_copy")
    val authorizationCopy: String,
    @SerializedName("authorization_copy_link")
    val authorizationCopyLink: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("company_sign_id")
    val companySignId: String,
    @SerializedName("company_status")
    val companyStatus: Int,
    @SerializedName("cr_copy")
    val crCopy: String,
    @SerializedName("cr_copy_link")
    val crCopyLink: String,
    @SerializedName("cr_no")
    val crNo: String,
    @SerializedName("sign_id_copy")
    val signIdCopy: String,
    @SerializedName("sign_id_copy_link")
    val signIdCopyLink: String
)

data class FAQData(
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)

data class TacModelRes(
        @SerializedName("data")
        val `data`: TacModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Any
)


data class RefundRequestRes(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)


data class FavAutionIdsRes(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class VerifyResponse(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("status")
    val status: String
)



data class TokenData(
        @SerializedName("Token")
        val Token: String
)




data class CompanyFeesModelRes(
        @SerializedName("data")
        val `data`: CompanyFeesModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Any
)

data class CompanyFeesRespose(
    @SerializedName("data")
    val `data`: ArrayList<CompanyFeesData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class CompanyFeesData(
    @SerializedName("fees")
    val fees: String,
    @SerializedName("price_from")
    val priceFrom: String,
    @SerializedName("price_to")
    val priceTo: String
)


data class DetailsModelRes(
        @SerializedName("data")
        val `data`: DetailsModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Any
)
data class DetailsModelRes1(
    @SerializedName("data")
    val `data`: DetailsModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Any
)
data class BUYDetailsModelRes(
    @SerializedName("data")
    val `data`: DetailsModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Any
)
data class addWishRes(
        @SerializedName("data")
        val `data`: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)


/*
data class WishingDatasRes(
        @SerializedName("data")
        val `data`:ArrayList<AuctionItemModel>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)*/
data class WishingBidRes(
    @SerializedName("data")
    val `data`: ArrayList<WishingBiData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class WishingBiData(
    @SerializedName("bid_status")
    val bidStatus: BidStatus,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("closing_date")
    val closingDate: String,
    @SerializedName("closing_time")
    val closingTime: String,
    @SerializedName("expired")
    val expired: Boolean,
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
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String
)

data class BidStatus(
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("reason")
    val reason: String
)




data class WinninggDatasRes(
        @SerializedName("data")
        val `data`:ArrayList<WinningBidsDetails>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)

data class WinninggBidsPaymentDatasRes(
        @SerializedName("data")
        val `data`:String,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)

data class MyBidsRes(
        @SerializedName("data")
        val `data`:ArrayList<AuctionItemModel>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)

data class searchproduct(
    @SerializedName("data")
    val `data`:ArrayList<AuctionItemModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class fetchDetailsRes(
        @SerializedName("data")
        val `data`:DetailsModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)
data class fetchDetailsRes1(
    @SerializedName("data")
    val `data`:DetailsModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
data class FetchAuctionsRes(
        @SerializedName("data")
        val `data`:ArrayList<AuctionItemModel>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)
data class FetchAuctionsRes1(
    @SerializedName("data")
    val `data`:ArrayList<AuctionItemModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
data class Fetchproduct(
    @SerializedName("data")
    val `data`:ArrayList<AuctionItemModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
data class LoginRes(
        @SerializedName("data")
        val `data`:Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: String
)

data class LoginRes1(
    @SerializedName("data")
    val `data`: LoginRes1Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class LoginRes1Data(
    @SerializedName("device_type")
    val deviceType: Int,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("mobile_otp")
    val mobileOtp: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("otp_attempt")
    val otpAttempt: Int,
    @SerializedName("otp_time")
    val otpTime: String,
    @SerializedName("qatar_id")
    val qatarId: String,
    @SerializedName("status")
    val status: Int
)




data class ResendRes(
        @SerializedName("data")
        val `data`:Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)


data class CommonResponse(
        @SerializedName("data")
        val `data`: Any,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)


data class SetprofileRes(
        @SerializedName("data")
        val `data`: Any,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)


data class DepositModelRes(
        @SerializedName("data")
        val `data`: DepositModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)

data class  NotificationModelRes(
        @SerializedName("data")
        val `data`:  ArrayList<NotificationModel>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)




data class ProfileModelRes(
        @SerializedName("data")
        val `data`: ProfileModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)




data class ContactUsModelRes(
        @SerializedName("data")
        val `data`: ContactUsModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)
data class fetchcountrycodeRes(
    @SerializedName("data")
    val `data`: fetchcountrymodel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
data class AboutUsModelRes(
        @SerializedName("data")
        val `data`: AboutUsModel,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)



