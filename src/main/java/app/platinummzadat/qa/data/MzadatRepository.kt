package app.platinummzadat.qa.data

import app.platinummzadat.qa.data.models.*
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:16 on Tuesday 02 April 2019
 */


class MzadatRepository(private val remote: MzDataSource) : MzDataSource {
    override fun fetchCompanyFees(
        userId: Int,
        result: (status: Boolean, data: CompanyFeesRespose, error: ERROR) -> Unit
    ) {
        remote.fetchCompanyFees(userId, result)
    }

    override fun getAmount(userId: Int, result: (status: Boolean, data: AmountData?, error: ERROR) -> Unit) {
        remote.getAmount(userId, result)
    }

    override fun registerCompany( company_name: String, company_sign_id: String,cr_no: String, result: (status: Boolean, data: CompanyRegistrationRes?, error: ERROR) -> Unit) {
        remote.registerCompany( company_name,company_sign_id,cr_no,result)
    }

    override fun checkCompanyRegistration(result: (status: Boolean, data: CheckCompanyRegisterRes?, error: ERROR) -> Unit) {
        remote.checkCompanyRegistration( result)
    }

    override fun checkComputerCard(cr_no: String, result: (status: Boolean, data: ComputerCheckRes?, error: ERROR) -> Unit) {
        remote.checkComputerCard(cr_no, result)
    }

    override fun getRefundRequest(imei: String, result: (status: String, data: RefundRequestRes?, error: ERROR) -> Unit) {
        remote.getRefundRequest(imei, result)
    }


    override fun fetchContactUs(result: (status: Boolean, data: ContactUsModel?, error: ERROR) -> Unit) {
        remote.fetchContactUs(result)
    }
    override fun fetchcountry(result: (status: Boolean, data: fetchcountrymodel?, error: ERROR) -> Unit) {
        remote.fetchcountry(result)
    }

    override fun fetchAboutUs(result: (status: Boolean, data: AboutUsModel?, error: ERROR) -> Unit) {
        remote.fetchAboutUs(result)
    }

    override fun splash(
        userId: Int,
        firebaseId: String,
        type:Int,
        result: (status: Boolean, data: SplashModel?, error: ERROR) -> Unit
    ) {
        remote.splash(userId, firebaseId,type, result)
    }

    override fun FAQ(result: (status: Boolean, data: FaqRes?, error: ERROR) -> Unit) {
        remote.FAQ(  result)
    }

    override fun fetchTac(result: (status: Boolean, data: TacModel?, error: ERROR) -> Unit) {
        remote.fetchTac(result)
    }

    override fun fetchDepositHistory(
        userId: Int,
        result: (status: Boolean, data: DepositModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDepositHistory(userId, result)
    }

    override fun uploadQid(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.uploadQid(userId, path, result)
    }

    override fun fetchNotifications(
        userId: Int,
        result: (status: Boolean, data: ArrayList<NotificationModel>, error: ERROR) -> Unit
    ) {
        remote.fetchNotifications(userId, result)
    }

    override fun submitFeedback(
        userId: Int,
        auctionId: Int,
        content: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.submitFeedback(userId, auctionId, content, result)
    }

    override fun updateLastActive(
        userId: Int,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.updateLastActive(userId, result)
    }

    override fun resendOtp(
        mobile: String,
        country_name:String,
        hash: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.resendOtp(mobile,country_name, hash, result)
    }

    override fun uploadProfilePhoto(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.uploadProfilePhoto(userId, path, result)
    }

    override fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        countryCode: String,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.updateProfile(userId, name, email,countryCode, password, result)
    }

    override fun updatePassword(
        userId: Int,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.updatePassword(userId, password, result)
    }

    override fun fetchProfile(
        userId: Int,
        result: (status: Boolean, data: ProfileModel?, error: ERROR) -> Unit
    ) {
        remote.fetchProfile(userId, result)
    }

    override fun addToWishList(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: String?, error: ERROR) -> Unit
    ) {
        remote.addToWishList(userId, auctionId, result)
    }

    override fun placeBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        type: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.placeBid(userId, auctionId, amount,type, result)
    }
    override fun placeproduct(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.placeproduct(userId, auctionId, amount, result)
    }
    override fun BUYplaceBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.BUYplaceBid(userId, auctionId, amount, result)
    }
    override fun wishingBids(
        wishlistid:String,
        userId: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.wishingBids(wishlistid,userId, result)
    }
    override fun winningBids(
            userId: Int,

            result: (status: Boolean, data: ArrayList<WinningBidsDetails>, error: ERROR) -> Unit
    ) {
        remote.winningBids(userId, result)
    }
    override fun winningBidsPayment(
            userId: Int,
            bidId:Int,
            result: (status: Boolean, data: String, error: ERROR) -> Unit
    ) {
        remote.winningBidsPayment(userId, bidId,result)
    }
    override fun doPayment(
            userId: Int,
            amount:String,
            result: (status: Boolean, data: String, error: ERROR) -> Unit
    ) {
        remote.doPayment(userId, amount,result)
    }
    override fun myBids(
        userId: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.myBids(userId, result)
    }

    override fun searchAuctions(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.searchAuctions(userId, searchQuery, result)
    }
    override fun searchAuctions1(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.searchAuctions1(userId, searchQuery, result)
    }
    override fun verifyOtp(
        mobile_no: String,
        otp: String,
        country_name: String,

        firebaseId: String,
        result: (message: Int, data: String, error: ERROR,status:Boolean) -> Unit
    ) {
        remote.verifyOtp(mobile_no, otp,country_name, firebaseId, result)
    }

    override fun fetchDetails(
        userId: Int,
        auctionId: Int,
        type: Int,

        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDetails(userId, auctionId,type, result)
    }
    override fun fetchDetails1(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDetails1(userId, auctionId, result)
    }
    override fun fetchAuctions(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        type: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.fetchAuctions(userId, categoryId, filter, offset, limit,type, result)
    }
    override fun fetchAuctions1(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.fetchAuctions1(userId, categoryId, filter, offset, limit, result)
    }
    override fun fetchproducts(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.fetchproducts(userId, categoryId, filter, offset, limit, result)
    }
    override fun fetchDashboard(
        firebaseId: String,
        type: Int,
        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDashboard(firebaseId,type ,result)
    }

    override fun fetchDashboard1(
        firebaseId: String,
        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDashboard1(firebaseId, result)
    }


    override fun fetchFavAutionsIDs(
            result: (status: Boolean, data: FavAutionIdsRes?, error: ERROR) -> Unit
    ) {
        remote.fetchFavAutionsIDs( result)
    }

    override fun login(
        qatarId: String,
        phone: String,
        crnumber:String,
        countryCode:String,
        countryName:String,
        hash: String,
        result: (status: Boolean, message:String, data: Int, error: ERROR) -> Unit
    ) {
        remote.login(qatarId, phone,crnumber,countryCode,countryName, hash, result)

    }

    companion object {
        const val OTP_TYPE_REGISTER = 1
        const val OTP_TYPE_LOGIN = 2
        const val OTP_TYPE_MORE= 3
        const val OTP_TYPE_MORE_EXPIRE=4
        const val OTP_TYPE_BLOCKED=5
        const val OTP_TYPE_INVALID=0
    }
}