package app.platinummzadat.qa

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.view.View
import app.platinummzadat.qa.data.MzadatRepository
import app.platinummzadat.qa.data.remote.RemoteDataSource
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.facebook.stetho.Stetho
import com.fxn.stash.Stash
//import com.google.android.gms.analytics.GoogleAnalytics
//import com.google.android.gms.analytics.Tracker
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import net.gotev.uploadservice.*
import net.gotev.uploadservice.okhttp.OkHttpStack
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import raj.nishin.wolfpack.wlog
import raj.nishin.wolfrequest.ERROR
import raj.nishin.wolfrequest.WolfRequest
import java.util.*


/**
 * Created by WOLF
 * at 20:10 on Wednesday 27 March 2019
 */

//const val KEY_REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE"
const val KEY_USER_ID = "USER_ID"
const val KEY_TEMP_USER_ID = "TEMP_USER_ID"
const val KEY_TEMP_TOKEN_ID = "TEMP_TOKEN_ID"
const val KEY_ACCOUNT_TYPE = "KEY_ACCOUNT_TYPE"
const val KEY_USERNAME = "USERNAME"
const val KEY_FIREBASE_ID = "FIREBASE_ID"
const val KEY_PROFILE_PHOTO = "PROFILE_PHOTO"
const val KEY_MOBILE_NUMBER = "MOBILE_NUMBER"
const val KEY_APP_LANGUAGE = "APP_LANGUAGE"
const val KEY_TRENDING_SEARCH = "TRENDING_SEARCH"
/*
const val REGISTRATION_BEGIN = 1
const val REGISTRATION_PASSWORD = 2
const val REGISTRATION_PROFILE_PHOTO = 3
const val REGISTRATION_COMPLETE = 4*/

class MApp : Application() {
    private var sAnalytics: GoogleAnalytics?=null
    private var sTracker: Tracker?=null
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        sAnalytics = GoogleAnalytics.getInstance(this);


        WolfRequest.init(this)
        Stash.init(this)
        //default follow auction set
        val strings: MutableSet<String> = HashSet()
        strings.add("one")
        Stash.put("favoriteProductIdsList", strings)
        /*
        * class User{
              public Name;
              public Age;
              }

       User user = new User();

       Stash.put("TAG_DATA_OBJECT",user)
       User userNew = Stash.getObject("TAG_DATA_OBJECT", User.class);



       ArrayList<User> userArrayList = new ArrayList<>();
       userArrayList.add(new User("Akshay",12));
       userArrayList.add(new User("Aman",11));

       Stash.put("TAG_DATA_ARRAYLIST",userArrayList);
       ArrayList<User> userArrayListNew = Stash.getArrayList("TAG_DATA_ARRAYLIST", User.class);


        * */
        val locale = Locale(appLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID
        UploadService.HTTP_STACK = OkHttpStack()
      //mba changes
        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.networkInterceptors().add(httpLoggingInterceptor)

        Stetho.initializeWithDefaults(this)
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
            Stetho.defaultInspectorModulesProvider(this)
        )
        // Enable command line interface
        initializerBuilder.enableDumpapp(
            Stetho.defaultDumperPluginsProvider(this)
        )
        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()
        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)
    }

    companion object {
        private var instance: MApp? = null
        fun applicationContext(): MApp {
            return instance as MApp
        }
        val MzRepo: MzadatRepository by lazy { MzadatRepository(RemoteDataSource()) }
        fun logout() {
            currentUserId = -1
            tempUserId = -1
            username = ""
            profilePhotoUrl = ""
            mobileNumber = ""
        }

    }

    @Synchronized
    fun getDefaultTracker(): Tracker? {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker=sAnalytics!!.newTracker(R.xml.global_tracker)
        }
        return sTracker
    }
}

/*

var registrationStep: Int
    get() = Stash.getInt(KEY_REGISTRATION_COMPLETE, REGISTRATION_BEGIN)
    set(value) = Stash.put(KEY_REGISTRATION_COMPLETE, value)
*/

var currentUserId: Int
    get() = Stash.getInt(KEY_USER_ID, -1)
    set(value) = Stash.put(KEY_USER_ID, value)

var tempUserId: Int
    get() = Stash.getInt(KEY_TEMP_USER_ID, -1)
    set(value) = Stash.put(KEY_TEMP_USER_ID, value)

var mToken: String
    get() = Stash.getString(KEY_TEMP_TOKEN_ID)
    set(value) = Stash.put(KEY_TEMP_TOKEN_ID, value)

var accountType: String
    get() = Stash.getString(KEY_ACCOUNT_TYPE)
    set(value) = Stash.put(KEY_ACCOUNT_TYPE, value)

var username: String
    get() = Stash.getString(KEY_USERNAME)
    set(value) = Stash.put(KEY_USERNAME, value)

var firebaseId: String
    get() = Stash.getString(KEY_FIREBASE_ID, "")
    set(value) = Stash.put(KEY_FIREBASE_ID, value)

var profilePhotoUrl: String
    get() = Stash.getString(KEY_PROFILE_PHOTO)
    set(value) = Stash.put(KEY_PROFILE_PHOTO, value)

var mobileNumber: String
    get() = Stash.getString(KEY_MOBILE_NUMBER)
    set(value) = Stash.put(KEY_MOBILE_NUMBER, value)

var appLanguage: String
    get() = Stash.getString(KEY_APP_LANGUAGE, "en")
    set(value) = Stash.put(KEY_APP_LANGUAGE, value)
var trendingSearch: String
    get() = Stash.getString(KEY_TRENDING_SEARCH, "[]")
    set(value) = Stash.put(KEY_TRENDING_SEARCH, value)




fun View.errorShake(callBack: (() -> Unit)?=null) {
    YoYo.with(Techniques.Shake)
        .onEnd {
            callBack?.invoke()
        }
        .playOn(this)
}

fun View.errorWobble(callBack: (() -> Unit)?=null) {
    YoYo.with(Techniques.Wobble)
        .onEnd {
            callBack?.invoke()
        }
        .playOn(this)
}

fun Context.noInternetAlert() {
    alert(
        Appcompat,
        getString(R.string.please_connect_to_a_network),
        getString(R.string.no_internet)
    ) {
        positiveButton(getString(R.string.ok)) { it.dismiss() }
    }.show()

}


fun Activity.getProgressDialog(message: String="Please wait..."): ProgressDialog {
    return ProgressDialog(this).apply {
        setMessage(message)
        setCancelable(false)
    }
}



fun Context.uploadFile(
    path: String,
    url: String,
    vararg params: Pair<String, Any>,
    result: (status: Boolean, error: ERROR) -> Unit
) {
    wlog("File Upload => $url => $path")
    val uploadRequest = MultipartUploadRequest(this, url)
    uploadRequest.addFileToUpload(path, "file")
    params.forEach {
        uploadRequest.addParameter(it.first, it.second.toString())
        wlog("${it.first}=${it.second}")
    }
    uploadRequest.addHeader("Token", mToken)
    uploadRequest.addParameter("device_type", "1")
    uploadRequest.addParameter("lang", appLanguage)
    uploadRequest.setNotificationConfig(UploadNotificationConfig().apply {
        progress.message="Uploading"
        completed.message="Upload Complete"
        setClearOnActionForAllStatuses(true)
        completed.autoClear=true
        progress.autoClear=true
    })

    uploadRequest.setMaxRetries(5)
    uploadRequest.setDelegate(object : UploadStatusDelegate {
        override fun onCancelled(context: Context?, uploadInfo: UploadInfo?) {

        }

        override fun onProgress(context: Context?, uploadInfo: UploadInfo?) {
        }

        override fun onError(
            context: Context?,
            uploadInfo: UploadInfo?,
            serverResponse: ServerResponse?,
            exception: Exception?
        ) {
            result(true, ERROR.API_ERROR)
        }

        override fun onCompleted(
            context: Context?,
            uploadInfo: UploadInfo?,
            serverResponse: ServerResponse?
        ) {
            result(true, ERROR.NONE)
        }
    })
    uploadRequest.startUpload()

}
