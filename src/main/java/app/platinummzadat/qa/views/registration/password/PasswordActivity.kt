package app.platinummzadat.qa.views.registration.password

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import app.platinummzadat.qa.*
import app.platinummzadat.qa.views.companyregister.RegisterAsCompanyActivity
import app.platinummzadat.qa.views.registration.profilephoto.ProfilePhotoRegistrationActivity
import app.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import kotlinx.android.synthetic.main.activity_password.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*


class PasswordActivity : MzActivity(), PasswordContract.View {
    override lateinit var presenter: PasswordContract.Presenter
    private lateinit var progress: ProgressDialog
    private val sharedPrefFile = "kotlinsharedpreference"
    private var mTracker: Tracker?=null
    private  var countryCode :String ="countryCode"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        PasswordPresenter(this)
        clConfirm.onClick {
            updatePassword()
        }
        llTac.onClick {
            /*val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                this@PasswordActivity,
                Uri.parse(TOS_URL)
            )*/
            startActivity<TermsOfServiceActivity>()
        }

        val sharedPreferences: SharedPreferences= this.getSharedPreferences("countrycode",
            Context.MODE_PRIVATE)
       countryCode = sharedPreferences.getString("countryCode","").toString()
        // Toast.makeText(applicationContext,"hi"+countryCode,Toast.LENGTH_LONG).show()

        progress = getProgressDialog()


        val application=application as MApp
        mTracker=application.getDefaultTracker()
        mTracker!!.send(
            HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build()
        )
        if(countryCode.equals("974"))
        {
            etEmail.setVisibility(View.VISIBLE)

        }
        else{
            etEmail.setVisibility(View.GONE)
            etName.setHint(getString(R.string.fullnamepassport))
        }


//
    }
    override fun onResume() {
        super.onResume()

        mTracker!!.setScreenName("Image~" + "PasswordActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }

    private fun updatePassword() {
        val sharedPreferences: SharedPreferences= this.getSharedPreferences("countrycode",
            Context.MODE_PRIVATE)
         countryCode =sharedPreferences.getString("countryCode","").toString()

         if(countryCode.equals("974"))
         {
             when {
                 etName.value.isEmpty() -> {
                     etName.errorShake { }
                     etName.requestFocus()
                 }
                 etEmail.value.isEmpty() -> {
                     etEmail.errorShake { }
                     etEmail.requestFocus()
                 }

                 else -> {
                     presenter.updateProfile(etName.value, etEmail.value,countryCode, "1234")
                 }
             }
         }
        else{
             when {
                 etName.value.isEmpty() -> {
                     etName.errorShake { }
                     etName.requestFocus()
                 }


                 else -> {
                     presenter.updateProfile(etName.value, "Null", countryCode,"1234")
                     Log.d("maaaa", countryCode)
                 }
             }
         }

    }

    override fun showSuccess() {
        var tempflap=true
        with(intent?.extras?.keySet()) {
            if (null != this) {
                if (this.contains("toPath")) {
                    if ("company" == intent?.extras?.getString("toPath")) {
                        tempflap=false
                        val i = Intent(this@PasswordActivity, RegisterAsCompanyActivity::class.java)
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(i)
                        finish()
                    }
                }

            }

        }
        if(tempflap){
            val i = Intent(this@PasswordActivity, ProfilePhotoRegistrationActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            finish()
        }

//        val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
//            Context.MODE_PRIVATE)
//        val sharedIdValue = sharedPreferences.getString("id_key",null)
//        Toast.makeText(baseContext, sharedIdValue, Toast.LENGTH_SHORT).show()
//        if(sharedIdValue=="Personal" || sharedIdValue=="شخصي")
//        {
//            val i = Intent(this@PasswordActivity, ProfilePhotoRegistrationActivity::class.java)
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(i)
//            finish()
//        }
//        else
//        {
//            val i = Intent(this@PasswordActivity, RegisterAsCompanyActivity::class.java)
//                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        startActivity(i)
//                        finish()
//        }

    }

    override fun showDuplicate() {
        root.longSnackbar(getString(R.string.try_with_diffferent_data), getString(R.string.ok)) {
            updatePassword()
        }
    }

    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        etPassword.lock()
        etConfirmPassword.lock()
        clConfirm.lock()
    }

    override fun hideLoading() {
        progress.hide()
        etPassword.unlock()
        etConfirmPassword.unlock()
        clConfirm.unlock()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            updatePassword()
        }
    }

    override fun sessionTimeOut() {
    }

    var backPressTime = 0L
    override fun onBackPressed() {
        if (currentLocalTimeInMillis < backPressTime + 500) {
            super.onBackPressed()
        } else {
            backPressTime = currentLocalTimeInMillis
            toast(getString(R.string.press_back_again_to_exit))
        }
    }

}
