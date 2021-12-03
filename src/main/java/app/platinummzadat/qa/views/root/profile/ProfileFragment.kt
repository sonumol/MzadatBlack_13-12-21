package app.platinummzadat.qa.views.root.profile
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
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
import app.platinummzadat.qa.networking.DatabaseHelper
import app.platinummzadat.qa.networking.DatabaseHelper1
import app.platinummzadat.qa.views.root.drawer.MzNav
import app.platinummzadat.qa.views.root.profile.deposit.FAILED
import app.platinummzadat.qa.views.root.profile.deposit.PaymentActivity
import app.platinummzadat.qa.views.root.profile.deposit.SUCCESS
import kotlinx.android.synthetic.main.custome_dialog_amout.*
import kotlinx.android.synthetic.main.fragment_main_drawer.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.tvUsername
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.idik.lib.cipher.so.CipherClient
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import raj.nishin.wolfrequest.ERROR
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.NumberFormat


class ProfileFragment : MzFragment(), ProfileContract.View {
    override lateinit var presenter: ProfileContract.Presenter
    private var qidPath1 = ""
    private var qidPath2 = ""
    private var user_id = ""
    private var profilePath = ""
    private lateinit var progress: ProgressDialog
    private val CHOOSER_PERMISSIONS_REQUEST_CODE = 7459
    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 0
        private const val FILE_PICKER_REQUEST_CODE = 1

        private const val ALARMS_EXTERNAL_STORAGE_FOLDER = "mzadat"
    }
    private lateinit var yourBitmap: Bitmap
    var outputStream: ByteArrayOutputStream?=null

    var mDatabaseHelper: DatabaseHelper?=null
    var mDatabaseHelper1: DatabaseHelper1?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchProfile()
        mDatabaseHelper=DatabaseHelper(context!!)
        mDatabaseHelper1=DatabaseHelper1(context!!)
//        mDatabaseHelper1!!.deleteallrecord();
    }

    override fun showData(data: ProfileModel) {
       // Toast.makeText(context,""+data.user_international,Toast.LENGTH_LONG).show()
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


        val sharedPreferences1: SharedPreferences=context!!.getSharedPreferences("user_id",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences1.edit()
        editor.putString("user_id", data.user_id)
        editor.apply()
        editor.commit()
         // Toast.makeText(MApp.applicationContext(), "no elements"+data.qidImage1, Toast.LENGTH_SHORT).show();
        val data1=mDatabaseHelper!!.getItemID(data.user_id)
        if (data1.count == 0) {

            // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
        } else {
            while (data1.moveToNext()) {

                val path=data1.getString(3)
                profilePhotoUrl = path
                ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle)
               // ivProfile.loadAvatar(path.toString(), R.drawable.ic_nav_account)
            }
        }

        val data2=mDatabaseHelper1!!.getItemID(data.user_id,"1")
        if (data2.count == 0) {
            //tvUpload1?.visibility(visible)
           // tvQidUploadInfo.visibility(visible)
                //  ivQid1?.loadAvatar("", R.drawable.bg_dark)
            ivQid1?.loadFromUrl(data.qidImage1)
        } else {

            while (data2.moveToNext()) {

                val path=data2.getString(3)


                ivQid1?.loadFromUrl(path)
                tvUpload1?.visibility(gone)
                tvQidUploadInfo.visibility(gone)

                // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
//                val path=data2.getString(3)
//                profilePhotoUrl = path
//                ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle)
            }
        }
        // Toast.makeText(MApp.applicationContext(), "no elements"+data.qidImage1, Toast.LENGTH_SHORT).show();

        val data3=mDatabaseHelper1!!.getItemID(data.user_id,"2")
        if (data3.count == 0) {
           // tvUpload2?.visibility(visible)
          //  tvQidUploadInfo.visibility(visible)
         //   ivQid2?.loadAvatar("", R.drawable.bg_dark)
            ivQid2?.loadFromUrl(data.qidImage2)

        } else {


            while (data3.moveToNext()) {

                val path=data3.getString(3)


                ivQid2?.loadFromUrl(path)
                tvUpload2?.visibility(gone)
                tvQidUploadInfo.visibility(gone)

                //  Toast.makeText(MApp.applicationContext(), "no elements"+path, Toast.LENGTH_SHORT).show();

//                val path=data2.getString(3)
//                profilePhotoUrl = path
//                ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle)
            }
        }





        val sharedPreferences: SharedPreferences= activity!!.getSharedPreferences("countrycode",
            Context.MODE_PRIVATE)
        var countryCode = sharedPreferences.getString("countryCode","")
        tvEmail?.text = data.email
        tvMobile?.text = data.mobile
        tvMyBids?.text = getString(R.string.my_bids_format, data.userBids)

        if(data.user_international==0){
            tvQatarId?.text = getString(R.string.qatar_id_format, data.qid)
            tvQidUploadInfo.setText(getString(R.string.upload_both_sides_of_your_qatar_id))
        }
        else{
            tvQidUploadInfo.setText(getString(R.string.upload_both_sides_of_your_qatar_id1))
            your_qatar_id.setText(getString(R.string.yourqatarpassport))
        }


        tvWinningBids?.text = getString(R.string.winning_bids_format, data.winningBids)
        username = data.username
     //   profilePhotoUrl = data.profilePhoto
        tvUsername?.text = username
       // ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle)
        tvQidStatus?.text = getString(R.string.qid_upload_status_format, data.qidStatus)
        //ivQid1?.loadFromUrl(data.qidImage1)
        //ivQid2?.loadFromUrl(data.qidImage2)
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

                val file = File(profilePath);
                val uri = Uri.fromFile(file);
                yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                outputStream=ByteArrayOutputStream()
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val path=MediaStore.Images.Media.insertImage(
                    context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                )



                uploadPhoto(path, "upload_profile_photo")
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


                    val file = File(qidPath1);
                    val uri = Uri.fromFile(file);
                    yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                    val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                    outputStream=ByteArrayOutputStream()
                    scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    val path=MediaStore.Images.Media.insertImage(
                        context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                    )

                    uploadPhoto(path)
                }
            }
            tvUpload1?.onClick {
                if ("" == qidPath1) {
                    openCamera(RC_PICK_QID_IMAGE1)
                } else {

                    val file = File(qidPath1);
                    val uri = Uri.fromFile(file);
                    yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                    val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                    outputStream=ByteArrayOutputStream()
                    scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    val path=MediaStore.Images.Media.insertImage(
                        context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                    )


                    uploadPhoto(path)
                }
            }
            tvUpload1?.visibility(visible)
        }
        if (data.showUpload2) {
            ivQid2?.onClick {
                if ("" == qidPath2) {
                    openCamera(RC_PICK_QID_IMAGE2)
                } else {

                    val file = File(qidPath2);
                    val uri = Uri.fromFile(file);
                    yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                    val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                    outputStream=ByteArrayOutputStream()
                    scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    val path=MediaStore.Images.Media.insertImage(
                        context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                    )


                    uploadPhoto(path)
                }
            }
            tvUpload2?.onClick {
                if ("" == qidPath2) {
                    openCamera(RC_PICK_QID_IMAGE2)
                } else {


                    val file = File(qidPath2);
                    val uri = Uri.fromFile(file);
                    yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                    val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                    outputStream=ByteArrayOutputStream()
                    scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    val path=MediaStore.Images.Media.insertImage(
                        context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                    )


                    uploadPhoto(path)
                }
            }
            tvUpload2?.visibility(visible)
        }

        content?.visibility(visible)
    }

    override fun showDataAmount(data: AmountData) {
        loading.visibility=View.GONE
        //showDialog(data.minimumDepositAmount)
    }

    private fun showDialog() {
        val dialog = Dialog(this@ProfileFragment.requireActivity())
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
                this@ProfileFragment,
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
        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences("user_id",
            Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getString("user_id","")

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

                val file = File(qidPath1);
                val uri = Uri.fromFile(file);
                yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                outputStream=ByteArrayOutputStream()
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val path=MediaStore.Images.Media.insertImage(
                    context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                )


                uploadPhoto(path, side = "1")
                val data1=mDatabaseHelper1!!.getItemID(user_id,"1")
                if (data1.count == 0) {

                    AddData1(user_id, "1", path)




                }

            } else if (requestCode == RC_PICK_QID_IMAGE2) {
                qidPath2 = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                setImageFromFilePath(ivQid2, qidPath2)



                val file = File(qidPath2);
                val uri = Uri.fromFile(file);
                yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                outputStream=ByteArrayOutputStream()
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val path=MediaStore.Images.Media.insertImage(
                    context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                )

                uploadPhoto(path, side = "2")
                val data1=mDatabaseHelper1!!.getItemID(user_id,"2")
                if (data1.count == 0) {

                    AddData1(user_id, "2", path)



                    //  Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == RC_PICK_PROFILE_IMAGE) {
                profilePath = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
                setAvatar(ivProfilePhoto, profilePath)

                val file = File(profilePath);
                val uri = Uri.fromFile(file);
                yourBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
                val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
                outputStream=ByteArrayOutputStream()
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val path=MediaStore.Images.Media.insertImage(
                    context?.contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
                )
               // uploadPhoto(path, "upload_profile_photo")

                val sharedPreferences2: SharedPreferences=context!!.getSharedPreferences("path",
                    Context.MODE_PRIVATE)
                val editor1: SharedPreferences.Editor =  sharedPreferences2.edit()
                editor1.putString("path", path)
                editor1.apply()
                editor1.commit()
                val data1=mDatabaseHelper!!.getItemID(user_id)
                if (data1.count == 0) {

                    AddData(user_id, "sonumol", path)



                    // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
                } else {


                    mDatabaseHelper!!.updateName(user_id,"son",path)
                }

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
        ProfilePresenter(this)
        progress = activity!!.getProgressDialog()
        return super.onCreateView(R.layout.fragment_profile, inflater, container)
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
    fun AddData(user_id: String, Name: String?, Path: String?) {


        val insertData=
            mDatabaseHelper!!.addData(user_id, Name, Path)
        if (insertData) {
            val data=mDatabaseHelper!!.data
            if (data.count == 0) {
                // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
            } else {
                while (data.moveToNext()) {
                    val id=data.getString(0)
                    val name=data.getString(1)
                    val quantity=data.getString(2)



//                    Toast.makeText(MApp.applicationContext(),"t"+data.getString(1),Toast.LENGTH_LONG).show();
                }
            }
        }
    }



    fun AddData1(user_id: String, side: String?, Path: String?) {


        val insertData=
            mDatabaseHelper1!!.addData(user_id, side, Path)
        if (insertData) {
            val data=mDatabaseHelper1!!.data
            if (data.count == 0) {
                //   Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
            } else {
                while (data.moveToNext()) {
                    val id=data.getString(0)
                    val name=data.getString(1)
                    val quantity=data.getString(2)



//                    Toast.makeText(MApp.applicationContext(),"t"+data.getString(1),Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
