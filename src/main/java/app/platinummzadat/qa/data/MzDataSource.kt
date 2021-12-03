package app.platinummzadat.qa.data

import app.platinummzadat.qa.data.models.*
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:07 on Tuesday 02 April 2019
 */
interface MzDataSource {
    fun fetchCompanyFees(
        userId: Int,
        result: (status: Boolean, data: CompanyFeesRespose, error: ERROR) -> Unit
    )
    fun getAmount(
            userId: Int,
            result: (status: Boolean, data: AmountData?, error: ERROR) -> Unit
    )


    fun registerCompany(

            company_name: String,
            company_sign_id: String,
            cr_no: String,

            result: (status: Boolean, data: CompanyRegistrationRes?, error: ERROR) -> Unit
    )

    fun checkCompanyRegistration(
            result: (status: Boolean, data: CheckCompanyRegisterRes?, error: ERROR) -> Unit
    )

    fun checkComputerCard(
            cr_no: String,
            result: (status: Boolean, data: ComputerCheckRes?, error: ERROR) -> Unit
    )

    fun getRefundRequest(
            imei: String,
            result: (status: String, data: RefundRequestRes?, error: ERROR) -> Unit
    )
    fun fetchContactUs(
        result: (status: Boolean, data: ContactUsModel?, error: ERROR) -> Unit
    )
    fun fetchcountry(
        result: (status: Boolean, data: fetchcountrymodel?, error: ERROR) -> Unit
    )
    fun fetchAboutUs(
        result: (status: Boolean, data: AboutUsModel?, error: ERROR) -> Unit
    )

    fun splash(
        userId: Int,
        firebaseId: String,
        type: Int,
        result: (status: Boolean, data: SplashModel?, error: ERROR) -> Unit
    )

    fun FAQ(
            result: (status: Boolean, data: FaqRes?, error: ERROR) -> Unit
    )

    fun fetchTac(
        result: (status: Boolean, data: TacModel?, error: ERROR) -> Unit
    )

    fun fetchDepositHistory(
        userId: Int,
        result: (status: Boolean, data: DepositModel?, error: ERROR) -> Unit
    )

    fun uploadQid(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    )

    fun fetchNotifications(
        userId: Int,
        result: (status: Boolean, data: ArrayList<NotificationModel>, error: ERROR) -> Unit
    )

    fun submitFeedback(
        userId: Int,
        auctionId: Int,
        content: String,
        result: (status: Boolean, error: ERROR) -> Unit
    )

    fun updateLastActive(
        userId: Int,
        result: (status: Boolean, error: ERROR) -> Unit
    )


    fun resendOtp(
            mobile: String,
            country_name:String,

            hash: String,
        result: (status: Boolean, error: ERROR) -> Unit
    )

    fun uploadProfilePhoto(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    )

    fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        countryCode: String,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    )


    fun updatePassword(
        userId: Int,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    )

    fun fetchProfile(
        userId: Int,
        result: (status: Boolean, data: ProfileModel?, error: ERROR) -> Unit
    )

    fun addToWishList(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: String?, error: ERROR) -> Unit
    )

    fun placeBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        type: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    )
    fun placeproduct(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    )
    fun BUYplaceBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    )
    fun fetchDetails(
        userId: Int,
        auctionId: Int,
        type: Int,

        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    )
    fun fetchDetails1(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    )
    fun wishingBids(
        wishlistid:String,
        userId: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )

    fun winningBids(
            userId: Int,
            result: (status: Boolean, data: ArrayList<WinningBidsDetails>, error: ERROR) -> Unit
    )
    fun winningBidsPayment(
            userId: Int,
            bidId: Int,
            result: (status: Boolean, data: String, error: ERROR) -> Unit
    )
    fun doPayment(
            userId: Int,
            amount: String,
            result: (status: Boolean, data: String, error: ERROR) -> Unit
    )
    fun myBids(
        userId: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )

    fun searchAuctions(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )
    fun searchAuctions1(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )
    fun fetchAuctions(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        type: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )
    fun fetchAuctions1(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )
    fun fetchproducts(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    )
    fun fetchDashboard(
        firebaseId: String,
        type: Int,

        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    )
    fun fetchDashboard1(
        firebaseId: String,
        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    )

    fun fetchFavAutionsIDs(
            result: (status: Boolean, data: FavAutionIdsRes?, error: ERROR) -> Unit
    )

    fun verifyOtp(
            mobile_no: String,
           otp: String,
            countryName: String,

            firebaseId: String,
            result: (message: Int, data: String, error: ERROR,status:Boolean)-> Unit
    )

    fun login(
        qatarId: String,
        phone: String,
        crnumber:String,
        countryCode:String,
        countryName:String,
        hash: String,

        result: (status: Boolean,message:String, data: Int, error: ERROR) -> Unit
    )
}