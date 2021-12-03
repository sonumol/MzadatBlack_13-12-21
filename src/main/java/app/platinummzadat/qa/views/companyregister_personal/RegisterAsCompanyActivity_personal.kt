package com.platinummzadat.qa.views.companyregister

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.CheckCompanyRegisterRes
import app.platinummzadat.qa.data.models.CompanyData
import app.platinummzadat.qa.data.models.ComputerCheckRes
import app.platinummzadat.qa.views.registration.profilephoto.ProfilePhotoRegistrationActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.platinummzadat.qa.*

import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import kotlinx.android.synthetic.main.activity_register_as_company.*
import net.idik.lib.cipher.so.CipherClient
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*
import raj.nishin.wolfrequest.ERROR

class RegisterAsCompanyActivity_personal : MzActivity(), CompanyRegisterActContract_personal.View {
    override lateinit var presenter: CompanyRegisterActContract_personal.Presenter
    private var authLetterPath = ""
    private var authSigneeIdPath = ""
    private var companyCrPath = ""
    private var mTracker: Tracker?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_as_company)
        CompanyRegisterActPresenter_personal(this)
        btnCompanyCR.setOnClickListener {
            openCamera(RC_PICK_COMPANY_CR)

        }
        btnSubmit.setOnClickListener {
            validateFields()
        }
        authLtterBtn.setOnClickListener {
            openCamera(RC_PICK_AUTH_LETTER)
        }
        authSigneIdBtn.setOnClickListener {
            openCamera(RC_PICK_AUTH_SIGNEE_ID)
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
        mTracker!!.setScreenName("Image~" + "RegisterAsCompanyActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
        toast(getString(R.string.some_error_occurred_try_again))
    }

    override fun sessionTimeOut() {
    }
    fun validateFields() {

        when {
            et_cName.text.toString().isNullOrEmpty() -> {
                et_cName.error = resources.getString(R.string.enter_company_name)
                toast(resources.getString(R.string.enter_company_name))
            }
            etCNumber.text.toString().isNullOrEmpty() -> {
                etCNumber.error = resources.getString(R.string.enter_company_registration)
                toast(resources.getString(R.string.enter_company_registration))
            }
            etAuthQID.text.toString().isNullOrEmpty() -> {
                etAuthQID.error = resources.getString(R.string.enter_auth_signee_qid)
                toast(resources.getString(R.string.enter_auth_signee_qid))
            }
            authSigneeIdPath.isNullOrEmpty() -> {
                toast(resources.getString(R.string.attach_autherised_signee_id))
            }
            authLetterPath.isNullOrEmpty() -> {
                toast(resources.getString(R.string.attach_auth_letter))
            }
            companyCrPath.isNullOrEmpty() -> {
                toast(resources.getString(R.string.attach_company_cr_copy))
            }

            else -> {
                checkComputerCard()
            }
        }
    }
    fun checkComputerCard() {
        presenter.checkComputerCard(etCNumber.text.toString())
    }

    fun uploadDatas() {
        presenter.registerCompany(etCNumber.text.toString(),et_cName.text.toString(),etAuthQID.text.toString())
    }

    override fun showData(data: CheckCompanyRegisterRes) {
        loading?.visibility = View.GONE

        if(null!=data.data){
            val company: CompanyData= data.data

            etCNumber.setText(company.crNo.toString())
            et_cName.setText(company.companyName.toString())
            etAuthQID.setText(company.companySignId.toString())

            if(null!=company.crCopy){
                companyCrTv.setText(company.crCopy.toString())
                ivCompanyCr.visibility= View.VISIBLE
                setNetworkImage(ivCompanyCr,company.crCopyLink)
            }
            if(null!=company.signIdCopy){
                authSigneeIdTv.setText(company.signIdCopy.toString())
                ivSigneeID.visibility= View.VISIBLE
                setNetworkImage(ivSigneeID,company.signIdCopyLink)
            }
            if(null!=company.authorizationCopy){
                authLettertv.setText(company.authorizationCopy.toString())
                ivAuthLetter.visibility= View.VISIBLE
                setNetworkImage(ivAuthLetter,company.authorizationCopyLink)
            }
            if(company.companyStatus==1){
                etCNumber.isEnabled=false
                et_cName.isEnabled=false
                etAuthQID.isEnabled=false
                companyCrTv.visibility= View.GONE
                authLettertv.visibility= View.GONE
                authSigneeIdTv.visibility= View.GONE
//                text_input_layout_AuthQid.isEnabled=false
//                text_input_layout_companyName.isEnabled=false
//                text_input_layout_companyRg.isEnabled=false
                btnCompanyCR.visibility= View.GONE
                authSigneIdBtn.visibility= View.GONE
                authLtterBtn.visibility= View.GONE
                btnSubmit.visibility= View.GONE
            }
            if(company.companyStatus == 2){
                etCNumber.isEnabled=true
                et_cName.isEnabled=true
                etAuthQID.isEnabled=true
                text_input_layout_AuthQid.isEnabled=true
                text_input_layout_companyName.isEnabled=true
                text_input_layout_companyRg.isEnabled=true
                btnCompanyCR.visibility= View.VISIBLE
                authSigneIdBtn.visibility= View.VISIBLE
                authLtterBtn.visibility= View.VISIBLE
                btnSubmit.visibility= View.VISIBLE
            }
            tvSubtitile.text = data.message
        }

    }

    override fun showDataNoRegistration(data: CheckCompanyRegisterRes) {
        tvSubtitile.text = data.message

    }
    override fun showSuccess() {
        toast(resources.getString(R.string.company_details_updated))
        val i = Intent(this@RegisterAsCompanyActivity_personal, ProfilePhotoRegistrationActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
       // presenter.checkCompanyRegistartion()
    }

    override fun showSuccessComputerCard(data: ComputerCheckRes) {
        uploadDatas()
    }

    override fun showFailureComputerCard(data: ComputerCheckRes) {
        toast(data.message)
    }


    override fun showFailure() {
        toast(resources.getString(R.string.company_details_update_failed))
    }

    override fun showNoInternet() {
        this@RegisterAsCompanyActivity_personal?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)

    }

    private fun openCamera(requestCode: Int) {
        Pix.start(
                this@RegisterAsCompanyActivity_personal,
                Options.init()
                        .setRequestCode(requestCode)
                        .setCount(1)
                        .setImageQuality(ImageQuality.LOW)
        )

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_PICK_AUTH_SIGNEE_ID) {
                authSigneeIdPath = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                setImageFromFilePath(ivSigneeID, authSigneeIdPath)
                ivSigneeID.visibility(visible)
                authSigneeIdTv.text = authSigneeIdPath.takeLast(20)
                uploadPhoto(authSigneeIdPath, "upload_company_id")
            } else if (requestCode == RC_PICK_AUTH_LETTER) {
                authLetterPath = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                ivAuthLetter.visibility(visible)
                setImageFromFilePath(ivAuthLetter, authLetterPath)
                authLettertv.text = authLetterPath.takeLast(20)
                uploadPhoto(authLetterPath, "upload_company_authorization")
            } else if (requestCode == RC_PICK_COMPANY_CR) {
                companyCrPath = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                ivCompanyCr.visibility(visible)
                setImageFromFilePath(ivCompanyCr, companyCrPath)
                companyCrTv.text = companyCrPath.takeLast(20)
                uploadPhoto(companyCrPath, "upload_company_cr")
            }
        } else {
            authSigneeIdPath = ""
            authLetterPath = ""
            companyCrPath = ""
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadPhoto(filePath: String, function: String) {
        Log.d("Filespags", filePath)
        loading?.visibility(visible)
        this?.uploadFile(
                filePath, CipherClient.serverApi() + function
        ) { status, error ->
            loading?.visibility(gone)
            when {
                error == ERROR.API_ERROR -> {

                }
                status -> {
                    when (function) {
                        "upload_company_cr" -> {
                            showUpCompanyCrUploaded()
                        }
                        "upload_company_id" -> {
                            showUpCompanyIDUploaded()
                        }
                        "upload_company_authorization" -> {
                            showUpCompanyAutherizationUploaded()
                        }
                    }

                    // presenter.fetchProfile()
                }
                !status -> {
                    when (function) {
                        "upload_company_cr" -> {
                            showUpCompanyCrFailed()
                        }
                        "upload_company_id" -> {
                            showUpCompanyIDFailed()
                        }
                        "upload_company_authorization" -> {
                            showUpCompanyAutheLetterFailed()
                        }
                    }
                }
            }
        }
    }

    fun showUpCompanyCrFailed() {

        rootRegister.indefiniteSnackbar(getString(R.string.company_cr_upload_failed),  getString(R.string.retry)) {
            uploadPhoto(companyCrPath, "upload_company_cr")
        }
    }

    fun showUpCompanyIDFailed() {
        rootRegister.indefiniteSnackbar(getString(R.string.authorisation_signee_id_copy_upload_failed),  getString(R.string.retry)) {
            uploadPhoto(authSigneeIdPath, "upload_company_id")
        }
    }

    fun showUpCompanyAutheLetterFailed() {
        rootRegister.indefiniteSnackbar(getString(R.string.authorisation_letter_upload_failed),  getString(R.string.retry)) {
            uploadPhoto(authLetterPath, "upload_company_authorization")
        }
    }

    fun showUpCompanyCrUploaded() {
        toast(getString(R.string.company_cr_uploaded))
    }

    fun showUpCompanyIDUploaded() {
        toast(getString(R.string.authorisation_signee_id_copy_uploaded))
    }

    fun showUpCompanyAutherizationUploaded() {
        toast(getString(R.string.authorisation_letter_uploaded))
    }
}