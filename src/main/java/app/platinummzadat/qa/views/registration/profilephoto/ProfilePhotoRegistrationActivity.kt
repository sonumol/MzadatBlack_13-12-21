package app.platinummzadat.qa.views.registration.profilephoto

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.fxn.utility.PermUtil
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.ProfileModel
import app.platinummzadat.qa.networking.DatabaseHelper
import app.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import kotlinx.android.synthetic.main.activity_profile_photo_registration.*
import net.idik.lib.cipher.so.CipherClient
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*
import raj.nishin.wolfrequest.ERROR
import java.io.ByteArrayOutputStream
import java.io.File


class ProfilePhotoRegistrationActivity : MzActivity(), ProfilePhotoContract.View {
    override lateinit var presenter: ProfilePhotoContract.Presenter
    private var path = ""
    private lateinit var progress: ProgressDialog
    private var mTracker: Tracker?=null
    private lateinit var yourBitmap: Bitmap
    var outputStream: ByteArrayOutputStream?=null
    var mDatabaseHelper: DatabaseHelper?=null
    private var user_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProfilePhotoPresenter(this)
        setContentView(R.layout.activity_profile_photo_registration)
        mDatabaseHelper=DatabaseHelper(this)
        presenter.fetchProfile()
        clProfilePhoto.onClick {
            openCamera()
        }
        clUpload.onClick {
            uploadPhoto()
        }
        tvSkip.onClick {
            showSuccess()

        }
        llTac.onClick {
           /* val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                this@ProfilePhotoRegistrationActivity,
                Uri.parse(TOS_URL)
            )*/
            startActivity<TermsOfServiceActivity>()
        }
        progress = getProgressDialog()

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
        mTracker!!.setScreenName("Image~" + "ProfilePhotoRegistrationActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }
    override fun showData(data: ProfileModel) {

        user_id=data.user_id





        val data1=mDatabaseHelper!!.getItemID(data.user_id)
        if (data1.count == 0) {

           //  Toast.makeText(MApp.applicationContext(), "no elements"+user_id, Toast.LENGTH_SHORT).show();
        } else {
            while (data1.moveToNext()) {

                val path=data1.getString(3)
                profilePhotoUrl = path
                ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle)
            }
        }

//Toast.makeText(applicationContext,""+data.user_id,Toast.LENGTH_LONG).show()


    }

    private fun uploadPhoto() {
        if ("" != path) {
            uploadPhoto(path)
        } else {
            openCamera()
            toast(getString(R.string.select_photo))
        }
    }

    override fun showSuccess() {
        startActivity<ProceedMessage>()
        finish()
    }

    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        toast(getString(R.string.uploading))
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            uploadPhoto()
        }
    }

    override fun sessionTimeOut() {
    }


    private fun openCamera() {
        Pix.start(
            this@ProfilePhotoRegistrationActivity,
            Options.init()
                .setRequestCode(RC_PICK_PROFILE_IMAGE)
                .setCount(1)
                .setImageQuality(ImageQuality.REGULAR)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RC_PICK_PROFILE_IMAGE) {
            path = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)[0]
            setAvatar(ivProfilePhoto, path)
            clPlaceHolder visibility invisible
            ivProfilePhoto visibility visible
        } else {
            path = ""
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    toast(getString(R.string.approve_permissions_to_open_image_picker))
                }
                return
            }
        }
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


    private fun uploadPhoto(filePath: String) {
        showLoading()
        val sharedPreferences: SharedPreferences= this.getSharedPreferences("user_id",
            Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getString("user_id","")
        val file = File(filePath);
        val uri = Uri.fromFile(file);
        yourBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val nh=(yourBitmap.height * (1000.0 / yourBitmap.width)).toInt()
        val scaled=Bitmap.createScaledBitmap(yourBitmap, 1000, nh, true)
        outputStream=ByteArrayOutputStream()
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val path=MediaStore.Images.Media.insertImage(
            contentResolver, scaled, "IMG_" + System.currentTimeMillis(), null
        )
        val sharedPreferences2: SharedPreferences=this.getSharedPreferences("path",
            Context.MODE_PRIVATE)
        val editor1: SharedPreferences.Editor =  sharedPreferences2.edit()
        editor1.putString("path", path)
        editor1.apply()
        editor1.commit()
        val data1=mDatabaseHelper!!.getItemID(user_id)
        if (data1.count == 0) {

            AddData(user_id, "sonumol", path)



            // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
        }
        hideLoading()
        showSuccess()
//        uploadFile(
//            path, CipherClient.serverApi() + "upload_profile_photo"
//        ) { status, error ->
//
//            when {
//                error == ERROR.API_ERROR -> {
//                    showApiError()
//                }
//                status -> {
//                   showSuccess()
//                }
//                else -> {
//                    showApiError()
//                }
//            }
//        }
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

}
