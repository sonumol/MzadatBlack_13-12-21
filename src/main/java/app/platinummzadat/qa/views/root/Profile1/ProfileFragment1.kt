package app.platinummzadat.qa.views.root.Profile1
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.AmountData
import app.platinummzadat.qa.data.models.ProfileModel
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.root.drawer.MzNav
import app.platinummzadat.qa.views.root.home_firstpage.Home_first_Activity
import app.platinummzadat.qa.views.root.profile.deposit.FAILED
import app.platinummzadat.qa.views.root.profile.deposit.PaymentActivity
import app.platinummzadat.qa.views.root.profile.deposit.SUCCESS
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.custome_dialog_amout.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.launch
import net.idik.lib.cipher.so.CipherClient
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import raj.nishin.wolfrequest.ERROR
import java.io.File
import java.text.NumberFormat


class ProfileFragment1 : MzFragment(), ProfileContract1.View {
    override lateinit var presenter: ProfileContract1.Presenter
    private var qidPath1 = ""
    private var qidPath2 = ""
    private var profilePath = ""
    private lateinit var progress: ProgressDialog
    private val CHOOSER_PERMISSIONS_REQUEST_CODE = 7459
    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 0
        private const val FILE_PICKER_REQUEST_CODE = 1

        private const val ALARMS_EXTERNAL_STORAGE_FOLDER = "mzadat"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchProfile()
    }

    override fun showData(data: ProfileModel) {
        tvIncreaseDeposit?.visibility(visible)
        Log.d("avatar",profilePhotoUrl)
       // tvDepositOffline?.visibility(visible)
        tvDepositAmount?.text =
            getString(R.string.deposit_amount_format, NumberFormat.getNumberInstance().format(data.depositAmount))
        val depositOfflineString =getString(R.string.deposit_offline_format, data.depositPhone)
        setTextWithSpan(tvDepositOffline, depositOfflineString, data.depositPhone, ForegroundColorSpan(Color.parseColor("#1770AA")))
        tvDepositOffline.onClick {
            activity?.dialNumber(data.depositPhone)
        }
        tvEmail?.text = data.email
        tvMobile?.text = data.mobile
        tvMyBids?.text = getString(R.string.my_bids_format, data.userBids)
        tvQatarId?.text = getString(R.string.qatar_id_format, data.qid)
        tvWinningBids?.text = getString(R.string.winning_bids_format, data.winningBids)
        username = data.username
        profilePhotoUrl = data.profilePhoto
        tvUsername?.text = username
        ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle)
        tvQidStatus?.text = getString(R.string.qid_upload_status_format, data.qidStatus)
        ivQid1?.loadFromUrl(data.qidImage1)
        ivQid2?.loadFromUrl(data.qidImage2)
        /*tvUpload?.visibility(if (data.showUpload) visible else gone)
        tvUpload?.onClick {
            if ("" == qidPath) {
                openCamera(RC_PICK_QID_IMAGE)
            } else {
                uploadPhoto(qidPath)
            }
        }*/
        ivProfilePhoto?.onClick {
            if ("" == profilePath) {
               // openFilePicker(RC_PICK_PROFILE_IMAGE)
              //  checkPermission(RC_PICK_PROFILE_IMAGE)
                openCamera(RC_PICK_PROFILE_IMAGE)
            } else {
                uploadPhoto(profilePath, "upload_profile_photo")
            }
        }
        btnWinningBid?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.WINNING_BIDS)
        }
        tvIncreaseDeposit?.onClick {
            loading.visibility=View.VISIBLE
           // presenter.getAmount()
            showDialog()
//            activity?.depositAlert {
//                /*val builder = CustomTabsIntent.Builder()
//                val customTabsIntent = builder.build()
//                customTabsIntent.launchUrl(
//                    activity,
//                    Uri.parse("${RemoteDataSource.PAYMENT_URL}?user_id=$currentUserId&amount=$it")
//                )*/
//                startActivityForResult<PaymentActivity>(RC_PAYMENT, "amount" to it)
//            }
        }
        tvQidUploadInfo?.visibility(if (data.showUpload1 || data.showUpload2) visible else gone)
        if (data.showUpload1) {
            ivQid1?.onClick {
                if ("" == qidPath1) {
                    openCamera(RC_PICK_QID_IMAGE1)
                } else {
                    uploadPhoto(qidPath1)
                }
            }
            tvUpload1?.onClick {
                if ("" == qidPath1) {
                    openCamera(RC_PICK_QID_IMAGE1)
                } else {
                    uploadPhoto(qidPath1)
                }
            }
            tvUpload1?.visibility(visible)
        }
        if (data.showUpload2) {
            ivQid2?.onClick {
                if ("" == qidPath2) {
                    openCamera(RC_PICK_QID_IMAGE2)
                } else {
                    uploadPhoto(qidPath2)
                }
            }
            tvUpload2?.onClick {
                if ("" == qidPath2) {
                    openCamera(RC_PICK_QID_IMAGE2)
                } else {
                    uploadPhoto(qidPath2)
                }
            }
            tvUpload2?.visibility(visible)
        }

        tvSkip.onClick {
            val sharedPreference=context!!.getSharedPreferences("prcount", Context.MODE_PRIVATE)
            var product=sharedPreference.getString("pr_count", "").toString()
            //Toast.makeText(applicationContext,"a"+product,Toast.LENGTH_LONG).show()
            if (product.equals("0")) {
               val intent=Intent(context,RootActivity::class.java)
                startActivity(intent)
            } else {
                val intent=Intent(context,Home_first_Activity::class.java)
                startActivity(intent)
            }
        }
        content?.visibility(visible)
    }

    override fun showDataAmount(data: AmountData) {
        loading.visibility=View.GONE
        //showDialog(data.minimumDepositAmount)
    }

    private fun showDialog() {
        val dialog = Dialog(this@ProfileFragment1.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custome_dialog_amout)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val body = dialog.findViewById(R.id.etAmount) as TextInputEditText
        val btnPayment = dialog.findViewById(R.id.btnSubmit) as MaterialButton
        btnPayment.setOnClickListener {
            if(body.text!!.isNotEmpty()){
                startActivityForResult<PaymentActivity>(RC_PAYMENT, "amount" to body.text.toString())
                dialog.dismiss()
            }else
                dialog.tilAmount.error=resources.getString(R.string.enter_deposite_amount)
        }
        dialog.show()

    }
    override fun showNoInternet() {
        activity?.noInternetAlert()
    }


    override fun showLoading() {
        loading?.visibility(visible)
        content?.visibility(gone)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
        fragmentListener?.onError {
            presenter.fetchProfile()
        }
    }

    override fun hideUploadingQid() {
        progress.hide()
    }

    override fun showUploadQidFailed(side: String) {
        fragmentListener?.onErrorWithMessage(getString(R.string.qid_upload_failed), null) {
            uploadPhoto(if (side == "1") qidPath1 else qidPath2, side)
        }

    }

    override fun showUploadedQid(side: String) {
        if (side == "1") qidPath1 = "" else qidPath2 = ""
        toast(getString(R.string.qid_uploaded))
        tvQidStatus?.text = getString(R.string.qid_upload_status_format, getString(R.string.uploaded))
//        tvUpload?.visibility(gone)
    }

    override fun showUploadingQid() {
        progress.show()
    }

    override fun sessionTimeOut() {
    }


    override fun showUploadProfilePhotoFailed() {
        fragmentListener?.onErrorWithMessage(getString(R.string.profile_photo_upload_failed), null) {
            uploadPhoto(profilePath, "upload_profile_photo")
        }
    }

//    override fun backpress() {
//        Toast.makeText(context,"ahha",Toast.LENGTH_LONG).show()
//        TODO("Not yet implemented")
//    }

    override fun showUploadedProfilePhoto() {
        profilePath = ""
        toast(getString(R.string.profile_photo_uploaded))
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.my_profile))
    }

    private fun openCamera(requestCode: Int) {
        Pix.start(
                this@ProfileFragment1,
                Options.init()
                        .setRequestCode(requestCode)
                        .setCount(1)
                        .setImageQuality(ImageQuality.LOW)
        )

    }

    fun openFilePicker(requestCode: Int) {
//        FilePickerBuilder.instance
//                    .setMaxCount(1) //optional
//                    .setActivityTheme(R.style.LibAppTheme) //optional
//                    .pickPhoto(this, requestCode);

    }



    fun arePermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (activity?.let { ContextCompat.checkSelfPermission(it, permission) } != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    fun requestPermissionsCompat(permissions: Array<String>, requestCode: Int) {
        try {
            ActivityCompat.requestPermissions(activity as AppCompatActivity, permissions, requestCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_PAYMENT) {
            if (resultCode == SUCCESS) {
                activity?.alert(
                        Appcompat,
                        "Payment completed successfully",
                        "Payment"
                ) { okButton { it.dismiss() } }?.show()
            } else if (resultCode == FAILED) {
                activity?.alert(
                        Appcompat,
                        "Payment failed. Please try again",
                        "Payment"
                ) { okButton { it.dismiss() } }?.show()
            } else {
                toast("Payment cancelled by user")
            }
        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_PICK_QID_IMAGE1) {
                qidPath1 = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                setImageFromFilePath(ivQid1, qidPath1)
                uploadPhoto(qidPath1, side = "1")
            } else if (requestCode == RC_PICK_QID_IMAGE2) {
                qidPath2 = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                setImageFromFilePath(ivQid2, qidPath2)
                uploadPhoto(qidPath2, side = "2")
            } else if (requestCode == RC_PICK_PROFILE_IMAGE) {
                profilePath = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                setAvatar(ivProfilePhoto, profilePath)
                uploadPhoto(profilePath, "upload_profile_photo")
            }
        } else {
            qidPath1 = ""
            qidPath2 = ""
            profilePath = ""
        }
//        if(requestCode== RC_PICK_PROFILE_IMAGE)
//            if (resultCode === Activity.RESULT_OK && android.R.attr.data != null) {
//                val docPaths = ArrayList<Any>()
//                docPaths.add(data!!.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_DOCS))
//                // Toast.makeText(this@AddSuccessStories.requireContext(), "Picked file: $docPaths", Toast.LENGTH_LONG).show()
//                if(docPaths.isNotEmpty()){
//
//                    val str=docPaths[0].toString()
//                    val tempuri=str.replace("[", "")
//                    val uri=tempuri.replace("]", "")
//                    val f= File(uri)
//                    profilePath = uri
//                    setAvatar(ivProfilePhoto, profilePath)
//                    uploadPhoto(profilePath, "upload_profile_photo")
//                }
//            }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ProfilePresenter1(this)
        progress = activity!!.getProgressDialog()
        return super.onCreateView(R.layout.fragment_profile1, inflater, container)
    }

    private fun uploadPhoto(filePath: String, function: String = "upload_qatar_id", side: String = "") {
        Log.d("Filespags",filePath)
        showUploadingQid()
        activity?.uploadFile(
                filePath, CipherClient.serverApi() + function,
                "side" to side
        ) { status, error ->
            hideUploadingQid()
            when {
                error == ERROR.API_ERROR -> {
                    if ("upload_qatar_id" == function) {
                        showUploadQidFailed(side)
                    } else {
                        showUploadProfilePhotoFailed()
                    }
                }
                status -> {
                    if ("upload_qatar_id" == function) {
                        showUploadedQid(side)
                    } else {
                        showUploadedProfilePhoto()
                    }
                    presenter.fetchProfile()
                }
                else -> {
                    if ("upload_qatar_id" == function) {
                        showUploadQidFailed(side)
                    } else {
                        showUploadProfilePhotoFailed()
                    }
                }
            }
        }
    }

}
