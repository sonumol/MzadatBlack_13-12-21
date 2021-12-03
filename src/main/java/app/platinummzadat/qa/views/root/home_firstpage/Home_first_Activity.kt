package app.platinummzadat.qa.views.root.home_firstpage

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import app.platinummzadat.RootActivity1
import app.platinummzadat.qa.*
import app.platinummzadat.qa.views.companyregister.RegisterAsCompanyActivity
import app.platinummzadat.qa.views.registration.profilephoto.ProfilePhotoRegistrationActivity
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import kotlinx.android.synthetic.main.activity_home_first.*
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.activity_password.clConfirm
import kotlinx.android.synthetic.main.activity_password.etEmail
import kotlinx.android.synthetic.main.activity_password.root
import kotlinx.android.synthetic.main.activity_register_as_company.*
import kotlinx.android.synthetic.main.appbar_root.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Home_first_Activity : MzActivity(){

    private lateinit var progress: ProgressDialog
    private val sharedPrefFile = "kotlinsharedpreference"
    private var mTracker: Tracker?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_first)
        etauctions.text = resources.getString(R.string.home_icons1)
        etdirect_purchase.text = resources.getString(R.string.home_icons2)
        auctionss.onClick {
            val intent = Intent(this@Home_first_Activity, RootActivity::class.java)
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("Buy","2")
            editor.commit()
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
           // this.finish()
        }
        directpurchase1.onClick {
            val intent = Intent(this@Home_first_Activity, RootActivity1::class.java)
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("Buy","2")
            editor.commit()
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
           // this.finish()
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

        mTracker!!.setScreenName("Image~" + "PasswordActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }






    var backPressTime = 0L
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (0 == supportFragmentManager.backStackEntryCount)
                alert(Appcompat, getString(R.string.sure_exit_now), getString(R.string.exit)) {
                    positiveButton(getString(R.string.exit)) {

//
//                        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm:a ")
//                        val currentDateAndTime: String = simpleDateFormat.format(Date())
                        val date=Date()
                        val df: DateFormat=
                            SimpleDateFormat("dd MMM yyyy, hh:mm:a" , Locale.ENGLISH)//
                        df.setTimeZone(TimeZone.getTimeZone("Asia/Qatar"))
                        // )

                        val sharedPreferences: SharedPreferences = getSharedPreferences(
                            sharedPrefFile,
                            Context.MODE_PRIVATE
                        )
                        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("date", df.format(date))

                        editor.apply()
                        editor.commit()
//                        val simpleDateFormat1 = SimpleDateFormat("hh:mm:a ")
//                        val currentDateAndTime1: String = simpleDateFormat1.format(Date())
//                        if(currentDateAndTime1=="12:00:AM ")
//                        {
//                            presenter.updateLastActive()
//                        }

                        System.exit(0)
                        // it.dismiss()


                    }
                    negativeButton(getString(R.string.cancel)) {
                        it.dismiss()
                    }
                }.show()




            else
                super.onBackPressed()
        }
    }

}
