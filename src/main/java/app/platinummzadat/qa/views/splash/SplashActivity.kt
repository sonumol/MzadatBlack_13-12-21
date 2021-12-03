package app.platinummzadat.qa.views.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.SplashModel
import app.platinummzadat.qa.views.companyregister.RegisterAsCompanyActivity
import app.platinummzadat.qa.views.login.LoginActivity
import app.platinummzadat.qa.views.registration.password.PasswordActivity
import app.platinummzadat.qa.views.root.Profile1.proActivity
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.welcome.WelcomeActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.startActivity
import raj.nishin.wolfpack.wlog
// emulator 19
// phone 18
class SplashActivity : MzActivity(), SplashContract.View {
    override lateinit var presenter: SplashContract.Presenter
    private var itemId = -1
    private var type = ""
    private var mTracker: Tracker?=null
    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashPresenter(this)


        type = intent?.extras?.getString("type") ?: ""
        itemId = (intent?.extras?.getString("item_id")?:"-1").toInt()

        setContentView(R.layout.activity_splash)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            wlog("token: ${it.token}")
            firebaseId = it?.token ?: ""

            val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
//        val sharedIdValue = sharedPreferences.getInt("id_key","")
            val sharedNameValue = sharedPreferences.getString("id_key","")
 //Log.d("ga", firebaseId)
            if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {
                presenter.splash(1)
               // Toast.makeText(applicationContext,""+ firebaseId ,Toast.LENGTH_LONG).show()
            }
            else
            {
                presenter.splash(2)
            }

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

        mTracker!!.setScreenName("Image~" + "SplashActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }
    override fun showSuccess(data: SplashModel) {
//        Toast.makeText(applicationContext,"a"+data.status,Toast.LENGTH_LONG).show()
        val sharedPreference =  getSharedPreferences("prcount", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("pr_count", data.product_count.toString())
        editor.commit()
       // Toast.makeText(applicationContext,"a"+data.status,Toast.LENGTH_LONG).show()
        if (-1 == currentUserId) {
            startActivity<WelcomeActivity>()
        } else {
            if (2 == data.status.toInt()) {
                startActivity<LoginActivity>()
            }
            else if(5 == data.status.toInt()) {
                startActivity<PasswordActivity>()//personal
            }
            else if(3 == data.status.toInt()) {
                startActivity<RegisterAsCompanyActivity>()
            }
            else if(4 == data.status.toInt()) {
                val intent = Intent(this@SplashActivity, PasswordActivity::class.java)

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("toPath","company")
                startActivity(intent)
                this.finish()
            }
            else
            {
//                Toast.makeText(applicationContext,"a"+data.status,Toast.LENGTH_LONG).show()
                startActivity<RootActivity>("type" to type, "item_id" to itemId)
//                Toast.makeText(applicationContext,"a"+type,Toast.LENGTH_LONG).show()
            }
        }
        finish()
    }

    override fun showBlocked() {
        alert(Appcompat,  getString(R.string.account_blocked, ""), getString(R.string.failed)) {
            positiveButton(getString(R.string.exit)) {
                it.dismiss()
                finish()
            }
        }.show()
    }

    override fun showNoInternet() {
        noInternetAlert()
        showApiError()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showApiError() {
        root?.indefiniteSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
//        val sharedIdValue = sharedPreferences.getInt("id_key","")
            val sharedNameValue = sharedPreferences.getString("id_key","")
            //Log.d("ga", firebaseId)
            if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {
                presenter.splash(1)
                // Toast.makeText(applicationContext,""+ firebaseId ,Toast.LENGTH_LONG).show()
            }
            else
            {
                presenter.splash(2)
            }
        }
    }

    override fun sessionTimeOut() {
    }


}
