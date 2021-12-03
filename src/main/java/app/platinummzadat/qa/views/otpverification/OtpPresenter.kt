package app.platinummzadat.qa.views.otpverification

import android.provider.Settings.Global.getString
import app.platinummzadat.qa.*
import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_BLOCKED
import app.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_INVALID
import app.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_LOGIN
import app.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_MORE
import app.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_MORE_EXPIRE
import app.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_REGISTER
import raj.nishin.wolfrequest.ERROR
import java.security.MessageDigest

/**
 * Created by WOLF
 * at 15:49 on Wednesday 17 April 2019
 */
class OtpPresenter(private val view: OtpContract.View) : OtpContract.Presenter {
    init {
        view.presenter = this
    }

    override fun resendOtp(hash: String, mobile: String,country_name:String) {
        MzRepo.resendOtp(mobile,country_name, hash) { status, error ->

        }
    }

    override fun verifyOtp(otp: String, mobile: String,country_name: String) {
        view.showLoading()
        MzRepo.verifyOtp(mobile, otp,country_name, firebaseId) { message, data, error, status ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                OTP_TYPE_INVALID==message -> {
                    view.showWrongOTP(message)
                }

               status->{

                   if(accountType=="Personal" || accountType=="شخصي") {
                       when (message) {
                           OTP_TYPE_LOGIN -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessLogin(message)
                           }
                           OTP_TYPE_MORE -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessLogin(message)
                           }
                           OTP_TYPE_REGISTER -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessRegister(message)
                           }
                       }

                   }else{
                       when (message) {
                           OTP_TYPE_LOGIN -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessLoginWithCompany(message)
                           }
                           OTP_TYPE_MORE -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showCompanyCheck(message)
                           }
                           OTP_TYPE_REGISTER -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showRegisterAndCompanyCheck(message)
                           }
                       }
                   }
                }

                !status ->{
                   if(OTP_TYPE_MORE == message) {
                        view.showTimeError()
                   }
                }

                OTP_TYPE_MORE_EXPIRE == message -> {
                    view.showOTPExpire()
                }
                OTP_TYPE_BLOCKED == message-> {
                    view.showBlockedUser(R.string.account_blocked, "")

                }
            }
        }
    }

}