package app.platinummzadat.qa.views.otpverification

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView

interface OtpContract {
    interface View : BaseView<Presenter> {
        fun showSuccessLogin(message: Int)
        fun showSuccessRegister(message: Int)
        fun showTimeError()
        fun showCompanyCheck(message: Int)
        fun showRegisterAndCompanyCheck(message: Int)
        fun showSuccessLoginWithCompany(message: Int)
        fun showWrongOTP(message: Int)
        fun showOTPExpire()
        fun showBlockedUser(messageResId: Int, append: String)
        fun showFailed()
    }

    interface Presenter :BasePresenter {
        fun verifyOtp(otp: String,mobile:String,country_name: String)
        fun resendOtp(hash:String,mobile: String,country_name:String)
    }
}