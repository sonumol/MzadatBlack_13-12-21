package app.platinummzadat.qa.views.login

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.fetchcountrymodel
import app.platinummzadat.qa.views.otpverification.OtpVerificationActivity
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*

class LoginActivity : MzActivity(), LoginContract.View , CountryCodePicker.OnCountryChangeListener{
    override lateinit var presenter: LoginContract.Presenter
    private lateinit var progress: ProgressDialog
    lateinit var radioButton: RadioButton
    var radioGroup: RadioGroup? = null
    private val sharedPrefFile = "kotlinsharedpreference"
    private var mTracker: Tracker?=null

    private var ccp:CountryCodePicker?=null

    private lateinit var countryCode:String
    private lateinit var countryName:String
    private lateinit var countryCode1:String
    private lateinit var countryName1:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginPresenter(this)
        radioGroup = findViewById(R.id.radioGroupType)

        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        val name:String =radioButton.text.toString()
        presenter.fetchcountry()
        ccp = findViewById(R.id.country_code_picker)
        ccp!!.setOnCountryChangeListener(this)

        radioPersonal.onClick {
            // Toast.makeText(baseContext, radioPersonal.text, Toast.LENGTH_SHORT).show()
            etcrnumber.setVisibility(View.GONE)
            etMobileNumber1.setVisibility(View.GONE)
            etQatarId.setVisibility(View.VISIBLE)
            etMobileNumber.setVisibility(View.VISIBLE)
            country_code_picker1.setVisibility(View.GONE)
            country_code_picker.setVisibility(View.VISIBLE)

        }
        radioCompany.onClick {
            etcrnumber.setVisibility(View.VISIBLE)
            etMobileNumber1.setVisibility(View.VISIBLE)
            etQatarId.setVisibility(View.GONE)
            etMobileNumber.setVisibility(View.GONE)
            country_code_picker.setVisibility(View.GONE)
            country_code_picker1.setVisibility(View.VISIBLE)
            // Toast.makeText(baseContext, radioCompany.text, Toast.LENGTH_SHORT).show()
        }

        countryCode=ccp!!.selectedCountryCode
        countryName=ccp!!.selectedCountryNameCode
        if(countryCode.equals("974"))
        {
            val maxLength=8
            etMobileNumber.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength)))
        }



//        tvSkip.onClick {
//
//
//            startActivity<RootActivity>()
//            finish()
//        }
        tvSkip.onClick {
//                    val sharedPreference =  getSharedPreferences("prcount", Context.MODE_PRIVATE)
//                    var  product = sharedPreference.getString("pr_count", "").toString()
            //Toast.makeText(applicationContext,"a"+product,Toast.LENGTH_LONG).show()
//                    if(product.equals("0"))
//                    {
            startActivity<RootActivity>()
//                    }
//                    else
//                    {
//                        startActivity<Home_first_Activity>()
//                    }

            //startActivity<Home_first_Activity>()
            finish()
        }

        clSubmit.onClick {
            authenticate()
        }
        progress = getProgressDialog()
        tvTac?.onClick {
            startActivity<TermsOfServiceActivity>()
        }

        val application=application as MApp
        mTracker=application.getDefaultTracker()
        mTracker!!.send(
            HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build()
        )
//

    }
    override fun onResume() {
        super.onResume()

        mTracker!!.setScreenName("Image~" + "LoginActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }
    override fun showData(data:fetchcountrymodel ) {
//        Toast.makeText(applicationContext,""+data,Toast.LENGTH_LONG).show()
        country_code_picker.setExcludedCountries(data.toString())
    }
    private fun authenticate() {
        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        val name:String =radioButton.text.toString()
        editor.putString("id_key", name)
        editor.apply()
        editor.commit()

        if(name=="Personal" || name=="شخصي")
        {
            when {

                etMobileNumber.value.isEmpty() -> {
                    etMobileNumber.errorShake { }
                    etMobileNumber.requestFocus()
                }
                etQatarId.value.isEmpty() -> {
                    etQatarId.requestFocus()
                    etQatarId.errorShake { }
                }
                !cbAccept.isChecked->{
                    llTac.errorShake {  }
                    toast(getString(R.string.accept_terms_and_conditions))
                }
                else -> {
                    accountType=radioButton.text.toString()
                    countryCode=ccp!!.selectedCountryCode
                    val sharedPreferences: SharedPreferences= this.getSharedPreferences("countrycode",
                        Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putString("countryCode", countryCode)
                    editor.putString("countryname", countryName)
                    editor.apply()
                    editor.commit()
                    if(countryCode.equals("974"))
                    {
//                        Toast.makeText(this,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
         presenter.authenticate(etQatarId.value, etMobileNumber.value,etcrnumber.value,"974","QA", AppSignatureHelper(this).appSignatures[0])

                    }
                    else
                    {
                     presenter.authenticate(etQatarId.value, etMobileNumber.value,etcrnumber.value,countryCode,countryName, AppSignatureHelper(this).appSignatures[0])

                    }

                   // presenter.authenticate(etQatarId.value, etMobileNumber.value,etcrnumber.value,countryCode,countryName, AppSignatureHelper(this).appSignatures[0])
                }
            }
//            Toast.makeText(this,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
//            Toast.makeText(this,"Country Name "+countryName,Toast.LENGTH_SHORT).show()
        }
        else{
            when {
                etcrnumber.value.isEmpty() -> {
                    etcrnumber.requestFocus()
                    etcrnumber.errorShake { }
                }
                etMobileNumber1.value.isEmpty() -> {
                    etMobileNumber1.errorShake { }
                    etMobileNumber1.requestFocus()
                }
                !cbAccept.isChecked->{
                    llTac.errorShake {  }
                    toast(getString(R.string.accept_terms_and_conditions))
                }
                else -> {
                    val sharedPreferences: SharedPreferences= this.getSharedPreferences("crnumber",
                        Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    val name:String =radioButton.text.toString()
                    editor.putString("ER number", etcrnumber.value)
                    editor.apply()
                    editor.commit()
                    accountType=radioButton.text.toString()

                    presenter.authenticate(etQatarId.value, etMobileNumber1.value,etcrnumber.value,"974","QA", AppSignatureHelper(this).appSignatures[0])



                    //presenter.authenticate(etQatarId.value, etMobileNumber1.value,etcrnumber.value,countryCode1,countryName1, AppSignatureHelper(this).appSignatures[0])
                    // Toast.makeText(applicationContext,""+etMobileNumber1.value ,Toast.LENGTH_LONG).show()
                }
            }
//            Toast.makeText(this,"Country Code "+countryCode1,Toast.LENGTH_SHORT).show()
//            Toast.makeText(this,"Country Name "+countryName1,Toast.LENGTH_SHORT).show()
        }

    }

    override fun showSuccess() {
        val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
//        val sharedIdValue = sharedPreferences.getInt("id_key","")
        val sharedNameValue = sharedPreferences.getString("id_key","")
        //Toast.makeText(applicationContext,""+sharedNameValue ,Toast.LENGTH_LONG).show()
        if( sharedNameValue=="Personal" ||  sharedNameValue=="شخصي")
        {
            startActivity<OtpVerificationActivity>("mobile" to etMobileNumber.value)
            finish()
        }
        else
        {

            startActivity<OtpVerificationActivity>("mobile" to etMobileNumber1.value)
            finish()
        }

    }

    override fun showInvalidCredentials(message:String) {
//        etQatarId.error = getString(R.string.invalid_credentials)
        //Toast.makeText(applicationContext,"s"+message,Toast.LENGTH_LONG).show()
//        etQatarId.errorShake { }
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.invalid_credentials), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
        //   showBlockedUser(R.string.invalid_credentials2, "")
    }

    override fun showInvalidCredentials1(message: String) {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.invalid_credentials3), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showInvalidCredentials2(message: String) {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.approved_person_can_resister), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showInvalidCredentials3(message: String) {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.login_duplicate_mobile), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showInvalidCredentials4(message: String) {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.emailmismatchh), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showBlockedUser(messageResId: Int, append: String) {
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(messageResId, append), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }

//    override fun showBlockedUser(message: String) {
//        etMobileNumber.text = null
//        etQatarId.text = null
//        alert(Appcompat, message, getString(R.string.failed)) {
//            positiveButton(getString(R.string.ok)) { it.dismiss() }
//        }.show()
//    }


    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        etQatarId.lock()
        etMobileNumber.lock()
    }

    override fun hideLoading() {
        progress.hide()
        etQatarId.unlock()
        etMobileNumber.unlock()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            authenticate()
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

    override fun onCountrySelected() {
        countryCode=ccp!!.selectedCountryCode
        countryName=ccp!!.selectedCountryNameCode


//        countryCode1=ccp1!!.selectedCountryCode
//        if(!countryCode1.equals("974")) {
//            etcrnumber.setHint("Enter Your Passport Number")
//        }
        countryCode=ccp!!.selectedCountryCode

        val sharedPreferences: SharedPreferences= this.getSharedPreferences("countrycode",
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("countryCode", countryCode)
        editor.putString("countryname", countryName)
        editor.apply()
        editor.commit()
etMobileNumber.setText("")

        if(!countryCode.equals("974")) {


                val maxLength=1000
                etMobileNumber.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength)))

          etQatarId.setHint("Enter Your Email")
          etQatarId.setText("")
            etQatarId.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(140)))
          etQatarId.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            }
        if(countryCode.equals("974")) {
            val maxLength=8
            etMobileNumber.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength)))
            etQatarId.setHint(getString(R.string.enter_qatar_id))
            etQatarId.setText("")
            etQatarId.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(11)))
            etQatarId.setInputType(InputType.TYPE_CLASS_NUMBER)
        }

//        Toast.makeText(this,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
//        Toast.makeText(this,"Country Name "+countryName,Toast.LENGTH_SHORT).show()
    }
}