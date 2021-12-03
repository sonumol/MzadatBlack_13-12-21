package app.platinummzadat.qa.data.remote

import app.platinummzadat.qa.appLanguage
import app.platinummzadat.qa.data.MzDataSource
import app.platinummzadat.qa.data.models.*
import app.platinummzadat.qa.mToken
import app.platinummzadat.qa.networking.HTTPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.idik.lib.cipher.so.CipherClient
import raj.nishin.wolfrequest.ERROR
import raj.nishin.wolfrequest.WolfRequest
import retrofit2.HttpException

/**
 * Created by WOLF
 * at 10:29 on Wednesday 17 April 2019
 */
val url=CipherClient.serverApi()
private  val SERVER_ROOT = CipherClient.serverApi()
/*
* Error Constants
* */
private const val E_LOGIN_DUPLICATE_QID = "login_duplicate_qid"
private const val E_LOGIN_DUPLICATE_MOBILE = "login_duplicate_mobile"
private const val E_LOGIN_QID_MOBILE_MISMATCH = "login_qid_mobile_mismatch"
private const val E_LOGIN_BLOCKED_USER = "login_blocked_user"
private const val E_BID_EXPIRED = "bid_expired"
private const val E_LOGIN_DUPLICATE_CR = "login_duplicate_cr_no"
private const val E_LOGIN_ONLY_REGISTERED = "login_cr_user_not_approved"
private const val E_LOGIN_CR_NO_MOBILE_MISMATCH = "login_cr_no_mobile_mismatch"
private const val E_LOGIN_EMAIL_MOBILE_MISMATCH = "login_email_mobile_mismatch"

private const val DEVICE_TYPE = 1

/*

//Resend otp
//whishlist not used set_deposit_amount

* define\('([\w]+)', "([\w]+)"\);
* private const val $1 = "$2"
* */
class RemoteDataSource : MzDataSource {
    override fun fetchCompanyFees(
        userId: Int,
        result: (status: Boolean, data: CompanyFeesRespose, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "get_payment", {
//            result(it.status, CompanyFeesModel(it.message, it.parseList()), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchCompanyFees( DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 400){

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }


    }



    override fun getAmount(userId: Int, result: (status: Boolean, data: AmountData?, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "get_minimum_deposit_amount", {
//            result(it.status,it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).GET()


//        RetrofitClient.instance.getAmount()
//                .enqueue(object : Callback<AmountModel> {
//                    override fun onFailure(call: Call<AmountModel>, t: Throwable) {
//                        result(false, null, ERROR.API_ERROR)
//                    }
//                    override fun onResponse(
//                            call: Call<AmountModel>,
//                            response: Response<AmountModel>
//                    ) {
//                        result(response.body()?.status!!,response.body()?.data!!, ERROR.NONE)
//
//                    }
//
//                })

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.getAmount( ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)

            } catch (e: java.lang.Exception) {
               result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }

    override fun registerCompany( company_name: String, company_sign_id: String,cr_no: String, result: (status: Boolean, data: CompanyRegistrationRes?, error: ERROR) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.companyRegistration(mToken,company_name, company_sign_id,cr_no ).await()
                result(result.status!!.toBoolean(),result, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
               // result(false, null, ERROR.API_ERROR)

            } catch (e: java.lang.Exception) {
                //result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun checkCompanyRegistration(result: (status: Boolean, data: CheckCompanyRegisterRes?, error: ERROR) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.checkCompanyRegistration( mToken, appLanguage).await()
                result(result.status!!.toBoolean(),result!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
               // result(false, null, ERROR.API_ERROR)

            } catch (e: java.lang.Exception) {
              //  result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun checkComputerCard(cr_no: String, result: (status: Boolean, data: ComputerCheckRes?, error: ERROR) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.checkComputerCard(mToken, appLanguage,cr_no ).await()
                result(result.status!!.toBoolean(),result, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()

            } catch (e: java.lang.Exception) {

                e.printStackTrace()
            }
        }
    }

    override fun getRefundRequest(imei: String, result: (status: String, data: RefundRequestRes?, error: ERROR) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.getRefundRequest(mToken,DEVICE_TYPE, appLanguage,imei  ).await()
                result(result.status!!,result, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result("false", null, ERROR.API_ERROR)

            } catch (e: java.lang.Exception) {
                result("false", null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun fetchContactUs(result: (status: Boolean, data: ContactUsModel?, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "get_contactus", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchContactUs( appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
                if (e.code() == 400){

                }
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }


    override fun fetchcountry(result: (status: Boolean, data: fetchcountrymodel?, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "get_contactus", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchcountry( appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
                if (e.code() == 400){

                }
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }


    override fun fetchAboutUs(result: (status: Boolean, data: AboutUsModel?, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "get_aboutus", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("device_type" to DEVICE_TYPE, "lang" to appLanguage)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchAboutUs( appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
                if (e.code() == 400){

                }
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }


    }

    override fun splash(
        userId: Int,
        firebaseId: String,
        type: Int,
        result: (status: Boolean, data: SplashModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "splash", {
//            when {
//                it.status -> result(it.status, it.parseItem(), ERROR.NONE)
//                it.message == E_LOGIN_BLOCKED_USER -> result(false, null, ERROR.BLOCKED_USER)
//                else -> result(false, null, ERROR.API_ERROR)
//            }
//        },
//            {
//                result(false, null, ERROR.API_ERROR)
//            },
//            {
//                result(false, null, ERROR.NO_INTERNET)
//            }).POST(
//            "user_id" to userId,
//            "firebase_id" to firebaseId,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.splash(mToken,firebaseId,DEVICE_TYPE,type, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }


    }

    override fun FAQ(result: (status: Boolean, data: FaqRes?, error: ERROR) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.getFAQ( appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.API_ERROR)
                e.printStackTrace()
            }
        }
    }

    override fun fetchTac(result: (status: Boolean, data: TacModel?, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "terms_of_use", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchTac(DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }

    override fun fetchDepositHistory(
        userId: Int,
        result: (status: Boolean, data: DepositModel?, error: ERROR) -> Unit
    ) {
/*        WolfRequest(SERVER_ROOT + "get_deposit_amount", {
            result(it.status, it.parseItem(), ERROR.NONE)
        }, {
            result(false, null, ERROR.API_ERROR)
        }, {
            result(false, null, ERROR.NO_INTERNET)
        }).POST("user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)*/

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchDepositHistory(mToken,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }



    override fun uploadQid(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        WolfRequest(SERVER_ROOT + "upload_qatar_id", {
            result(it.status, ERROR.NONE)
        }, {
            result(false, ERROR.API_ERROR)
        }, {
            result(false, ERROR.NO_INTERNET)
        }).UPLOAD(path, "user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)
    }

    override fun fetchNotifications(
        userId: Int,
        result: (status: Boolean, data: ArrayList<NotificationModel>, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "notifications", {
//            result(it.status, it.parseList(), ERROR.NONE)
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchNotifications(mToken,DEVICE_TYPE, appLanguage).await()
                result(result.status!!.toString().toBoolean(),result.data, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ArrayList(), ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }

    override fun submitFeedback(
        userId: Int,
        auctionId: Int,
        content: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "add_feedback", {
//            result(it.status, ERROR.NONE)
//        }, {
//            result(false, ERROR.API_ERROR)
//        }, {
//            result(false, ERROR.NO_INTERNET)
//        }).POST(
//            "user_id" to userId,
//            "auction_id" to auctionId,
//            "content" to content,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.submitFeedback(mToken,content,auctionId,DEVICE_TYPE, appLanguage).await()
                result(result.status!!.toString().toBoolean(), ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }


    }

    override fun updateLastActive(
        userId: Int,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "last_active", {
//            result(it.status, ERROR.NONE)
//        }, {
//            result(false, ERROR.API_ERROR)
//        }, {
//            result(false, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.updateLastActive(mToken,DEVICE_TYPE, appLanguage).await()
                result(result.status!!.toString().toBoolean(), ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }

    override fun resendOtp(
        mobile: String,
        country_name:String,
        hash: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "resend_otp", {
//            result(it.status, ERROR.NONE)
//        }, {
//            result(false, ERROR.API_ERROR)
//        }, {
//            result(false, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "app_hash" to hash, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.resendOtp(mobile,country_name,hash,DEVICE_TYPE).await()
                result(result.status!!.toString().toBoolean(), ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }



    override fun uploadProfilePhoto(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        WolfRequest(CipherClient.serverApi() + "upload_profile_photo", {
            result(it.status, ERROR.NONE)
        }, {
            result(false, ERROR.API_ERROR)
        }, {
            result(false, ERROR.NO_INTERNET)
        }).UPLOAD(path, "user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)




    }

    override fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        countryCode: String,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "set_profile", {
//            result(it.status, ERROR.NONE)
//        }, {
//            result(false, ERROR.API_ERROR)
//        }, {
//            result(false, ERROR.NO_INTERNET)
//        }).POST(
//            "user_id" to userId,
//            "name" to name,
//            "email" to email,
//            "password" to password,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.updateProfile(mToken,name,email,countryCode,password ).await()

                if(result.data==-1){
                    result(result.status!!, ERROR.DUPLICATE_DETAILS)
                }else{
                    result(result.status!!, ERROR.NONE)
                   // Log.d("gagag", result.toString())
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }

    override fun updatePassword(
        userId: Int,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "set_password", {
//            result(it.status, ERROR.NONE)
//        }, {
//            result(false, ERROR.API_ERROR)
//        }, {
//            result(false, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "password" to password, "device_type" to DEVICE_TYPE, "lang" to appLanguage)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.updatePassword(mToken,password,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun fetchProfile(
        userId: Int,
        result: (status: Boolean, data: ProfileModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "profile", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchProfile(mToken,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null,ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null,ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun addToWishList(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: String?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "add_wishlist", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "auction_id" to auctionId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.addToWishList(mToken,auctionId,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null,ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null,ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }

    }

    override fun placeBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        type: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "add_bid", {
//            if (it.status) {
//                result(it.status, it.parseItem(), ERROR.NONE)
//            } else {
//                when (it.message) {
//                    E_BID_EXPIRED -> result(false, null, ERROR.BID_EXPIRED)
//                }
//            }
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST(
//            "user_id" to userId,
//            "auction_id" to auctionId,
//            "amount" to amount,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.placeBid(mToken,auctionId,amount,type,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null,ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null,ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }


    override fun placeproduct(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "add_bid", {
//            if (it.status) {
//                result(it.status, it.parseItem(), ERROR.NONE)
//            } else {
//                when (it.message) {
//                    E_BID_EXPIRED -> result(false, null, ERROR.BID_EXPIRED)
//                }
//            }
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST(
//            "user_id" to userId,
//            "auction_id" to auctionId,
//            "amount" to amount,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.placeproduct(mToken,auctionId,amount,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null,ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null,ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }
    override fun BUYplaceBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "add_bid", {
//            if (it.status) {
//                result(it.status, it.parseItem(), ERROR.NONE)
//            } else {
//                when (it.message) {
//                    E_BID_EXPIRED -> result(false, null, ERROR.BID_EXPIRED)
//                }
//            }
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST(
//            "user_id" to userId,
//            "auction_id" to auctionId,
//            "amount" to amount,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.BUYplaceBid(mToken,auctionId,amount,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toString().toBoolean(),result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null,ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, null,ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }
    override fun wishingBids(
        wishlistid:String,
        userId: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "view_auction", {
//            result(it.status, it.parseList(), ERROR.NONE)
//
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "wish_user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.wishingBids(mToken,wishlistid,userId, DEVICE_TYPE, appLanguage).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ArrayList(),ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, ArrayList(),ERROR.NONE)
                e.printStackTrace()
            }
        }
    }

    override fun winningBids(userId: Int, result: (status: Boolean, data: ArrayList<WinningBidsDetails>, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "winning_bids", {
//            Log.d("djsjsdd",userId.toString())
//            result(it.status, it.parseList(), ERROR.NONE)
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to userId,  "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.winningBids(mToken,DEVICE_TYPE, appLanguage ).await()
                if(null==result.data){
                    result(result.status!!,ArrayList<WinningBidsDetails>(), ERROR.NONE)
                }else{
                    result(result.status!!,result.data!!, ERROR.NONE)

                }

            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()

            }
        }
    }


    override fun winningBidsPayment(userId: Int,bidId:Int, result: (status: Boolean, data: String, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "winning_bid_payment_ios", {
//            Log.d("djsjsdd",userId.toString())
//            result(it.status, it.data.toString(), ERROR.NONE)
//        }, {
//
//            result(false,"", ERROR.API_ERROR)
//        }, {
//            result(false, "", ERROR.NO_INTERNET)
//        }).POST("user_id" to userId,  "bid_id" to bidId,"device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.winningBidsPayment(mToken,bidId,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false,"", ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, "", ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun doPayment(userId: Int,amount:String, result: (status: Boolean, data: String, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "payment_ios_new", {
//
//            result(it.status, it.data.toString(), ERROR.NONE)
//        }, {
//
//            result(false,"", ERROR.API_ERROR)
//        }, {
//            result(false, "", ERROR.NO_INTERNET)
//        }).POST("user_id" to userId,  "amount" to amount, "lang" to appLanguage,"device_type" to DEVICE_TYPE)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.doPayment(mToken,amount,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false,"", ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, "", ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

//    override fun winningBids(userId: Int, result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit) {
//        WolfRequest(SERVER_ROOT + "winning_bids", {
//            Log.d("djsjsdd",userId.toString())
//            result(it.status, it.parseList(), ERROR.NONE)
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to 4, "wish_user_id" to 4, "device_type" to DEVICE_TYPE, "lang" to appLanguage)
//    }

    override fun myBids(
        userId: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "view_auction", {
//            result(it.status, it.parseList(), ERROR.NONE)
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "bid_user_id" to userId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.myBids(mToken, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,  ArrayList(), ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun searchAuctions(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "view_auction", {
//            result(it.status, it.parseList(), ERROR.NONE)
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "search_data" to searchQuery, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.searchAuctions(mToken,searchQuery,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,  ArrayList(), ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }
    override fun searchAuctions1(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "view_auction", {
//            result(it.status, it.parseList(), ERROR.NONE)
//        }, {
//            result(false, ArrayList(), ERROR.API_ERROR)
//        }, {
//            result(false, ArrayList(), ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "search_data" to searchQuery, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.searchAuctions1(mToken,searchQuery,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,  ArrayList(), ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    override fun fetchDetails(
        userId: Int,
        auctionId: Int,
        type: Int,

        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "auction_details_android", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "auction_id" to auctionId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchDetails(mToken,auctionId,DEVICE_TYPE,type, appLanguage ).await()
                result(result.status,result.data, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,  null, ERROR.NONE)
                e.printStackTrace()
            }
        }
    }
    override fun fetchDetails1(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "auction_details_android", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "auction_id" to auctionId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchDetails1(mToken,auctionId,DEVICE_TYPE, appLanguage ).await()
                result(result.status,result.data, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,  null, ERROR.NONE)
                e.printStackTrace()
            }
        }
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
/*        WolfRequest(SERVER_ROOT + "view_auction", {
            result(it.status, it.parseList(), ERROR.NONE)
        }, {
            result(false, ArrayList(), ERROR.API_ERROR)
        }, {
            result(false, ArrayList(), ERROR.NO_INTERNET)
        }).POST(
            "user_id" to userId,
            "category_id" to categoryId,
            "filter" to filter,
            "offset" to offset,
            "limit" to limit,
            "device_type" to DEVICE_TYPE,
            "lang" to appLanguage
        )*/

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchAuctions(mToken,categoryId,filter,offset,limit,type,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false,  ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,   ArrayList(), ERROR.API_ERROR)
                e.printStackTrace()
            }
        }

    }
    override fun fetchAuctions1(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
/*        WolfRequest(SERVER_ROOT + "view_auction", {
            result(it.status, it.parseList(), ERROR.NONE)
        }, {
            result(false, ArrayList(), ERROR.API_ERROR)
        }, {
            result(false, ArrayList(), ERROR.NO_INTERNET)
        }).POST(
            "user_id" to userId,
            "category_id" to categoryId,
            "filter" to filter,
            "offset" to offset,
            "limit" to limit,
            "device_type" to DEVICE_TYPE,
            "lang" to appLanguage
        )*/

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchAuctions1(mToken,categoryId,filter,offset,limit,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false,  ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,   ArrayList(), ERROR.API_ERROR)
                e.printStackTrace()
            }
        }

    }
    override fun fetchproducts(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
/*        WolfRequest(SERVER_ROOT + "view_auction", {
            result(it.status, it.parseList(), ERROR.NONE)
        }, {
            result(false, ArrayList(), ERROR.API_ERROR)
        }, {
            result(false, ArrayList(), ERROR.NO_INTERNET)
        }).POST(
            "user_id" to userId,
            "category_id" to categoryId,
            "filter" to filter,
            "offset" to offset,
            "limit" to limit,
            "device_type" to DEVICE_TYPE,
            "lang" to appLanguage
        )*/

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchProducts(mToken,categoryId,filter,offset,limit,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!,result.data!!, ERROR.NONE)
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false,  ArrayList(), ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false,   ArrayList(), ERROR.API_ERROR)
                e.printStackTrace()
            }
        }

    }
    override fun fetchDashboard(
        firebaseId: String,
        type: Int,
        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    ) {

//        WolfRequest(SERVER_ROOT + "dashboard", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "firebase_id" to firebaseId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchDashboard(mToken,firebaseId, DEVICE_TYPE,type, appLanguage).await()
                result(result.status!!,result.data, ERROR.NONE)


            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 400){
                    result(false, null, ERROR.API_ERROR)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            }
        }
    }
    override fun fetchDashboard1(
        firebaseId: String,
        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    ) {

//        WolfRequest(SERVER_ROOT + "dashboard", {
//            result(it.status, it.parseItem(), ERROR.NONE)
//        }, {
//            result(false, null, ERROR.API_ERROR)
//        }, {
//            result(false, null, ERROR.NO_INTERNET)
//        }).POST("user_id" to userId, "firebase_id" to firebaseId, "device_type" to DEVICE_TYPE, "lang" to appLanguage)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.fetchDashboard1(mToken,firebaseId, DEVICE_TYPE, appLanguage).await()
                result(result.status!!,result.data, ERROR.NONE)


            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 400){
                    result(false, null, ERROR.API_ERROR)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            }
        }
    }
    override fun fetchFavAutionsIDs(result: (status: Boolean, data: FavAutionIdsRes?, error: ERROR) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.getFavAutionsIds(mToken).await()
                result(result.status!!,result, ERROR.NONE)


            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 400){
                    result(false, null, ERROR.API_ERROR)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                result(false, null, ERROR.API_ERROR)
            }
        }
    }

    override fun verifyOtp(
        mobile_no: String,
        otp: String,
        country_name: String,
        firebaseId: String,
        result: (message: Int, data: String, error: ERROR,status:Boolean) -> Unit
    ) {
//        WolfRequest(SERVER_ROOT + "check_otp", {
//            result(it.message, it.data, ERROR.NONE)
//        }, {
//            result(false, -1, ERROR.API_ERROR)
//        }, {
//            result(false, -1, ERROR.NO_INTERNET)
//        }).POST(
//            "user_id" to mobile_no,
//            "otp" to otp,
//            "firebase_id" to firebaseId,
//            "device_type" to DEVICE_TYPE,
//            "lang" to appLanguage
//        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.verifyOtp( otp,mobile_no,country_name,firebaseId,DEVICE_TYPE,appLanguage).await()
                result(result.message, result.data, ERROR.NONE,result.status.toBoolean())
            } catch (e: HttpException) {
                result(0,".1", ERROR.API_ERROR,false)
                e.printStackTrace()

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                result(0,".1", ERROR.API_ERROR,false)
            }
        }


    }



    override fun login(
        qatarId: String,
        phone: String,
        cr_no: String,
        countryCode:String,
        countryName:String,
        hash: String,

        result: (status: Boolean,message :String, data: Int, error: ERROR) -> Unit
    ) {
/*        WolfRequest(SERVER_ROOT + "login_android", {
            if (it.status) {
                result(true, it.parseItem(), ERROR.NONE)
            } else {
                when (it.message) {
                    E_LOGIN_QID_MOBILE_MISMATCH -> {
                        result(false, -1, ERROR.INVALID_USER)
                    }
                    E_LOGIN_DUPLICATE_MOBILE -> {
                        result(false, -1, ERROR.DUPLICATE_MOBILE)
                    }
                    E_LOGIN_DUPLICATE_QID -> {
                        result(false, -1, ERROR.DUPLICATE_QID)
                    }
                    E_LOGIN_BLOCKED_USER -> {
                        result(false, -1, ERROR.BLOCKED_USER)
                    }

                    else -> {
                        result(false, -1, ERROR.INVALID_USER)
                    }
                }
            }
        }, {
            result(false, -1, ERROR.API_ERROR)
        }, {
            result(false, -1, ERROR.NO_INTERNET)
        }).POST(
            "qatar_id" to qatarId,
            "mobile_no" to phone,
            "app_hash" to hash,
            "device_type" to DEVICE_TYPE,
            "lang" to appLanguage
        )*/
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = HTTPClient.coroutineRestfulAPI.login(qatarId,phone,cr_no,countryCode,countryName,hash,DEVICE_TYPE, appLanguage ).await()
                result(result.status!!.toBoolean(),result.message,result.data!!, ERROR.NONE)

                if (result.status.equals("true")) {
                    result(true, result.message,result.data, ERROR.NONE)
                } else {
                    when (result.message) {
                        E_LOGIN_QID_MOBILE_MISMATCH -> {
                            result(false, result.message,-1, ERROR.INVALID_USER)
                        }
                        E_LOGIN_DUPLICATE_MOBILE -> {
                            result(false, result.message,-1, ERROR.DUPLICATE_MOBILE)
                        }
                        E_LOGIN_DUPLICATE_QID -> {
                            result(false, result.message,-1, ERROR.DUPLICATE_QID)
                        }
                        E_LOGIN_BLOCKED_USER -> {
                            result(false, result.message,-1, ERROR.BLOCKED_USER)
                        }
                        E_LOGIN_DUPLICATE_CR -> {
                            result(false, result.message,-1, ERROR. DUPLICATE_CR)
                        }
                        E_LOGIN_ONLY_REGISTERED -> {
                            result(false, result.message,-1, ERROR. NO_REGISTERD_PERSON)
                        }
                        E_LOGIN_CR_NO_MOBILE_MISMATCH -> {
                            result(false, result.message,-1, ERROR. CR_MISMACH)
                        }
                        E_LOGIN_EMAIL_MOBILE_MISMATCH -> {
                            result(false, result.message,-1, ERROR. EMAIL_MISMATCH)
                        }
                        else -> {
                            result(false,result.message, -1, ERROR.INVALID_USER)
                        }
                    }
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                result(false, "",-1, ERROR.API_ERROR)
            } catch (e: java.lang.Exception) {
                result(false, "",-1, ERROR.NO_INTERNET)
                e.printStackTrace()
            }
        }
    }

    companion object {
         val TOS_URL = CipherClient.serverApi() + "terms_of_use"
         val PAYMENT_URL = CipherClient.serverApi() + "payment_ios_new"
    }
}