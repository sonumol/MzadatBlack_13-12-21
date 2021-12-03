package app.platinummzadat.qa.views.welcome

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.R
import app.platinummzadat.qa.appLanguage
import app.platinummzadat.qa.mToken
import app.platinummzadat.qa.views.login.LoginActivity
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.root.home_firstpage.Home_first_Activity
import app.platinummzadat.qa.views.splash.SplashActivity
import kotlinx.android.synthetic.main.content_welcome.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.currentLocalTimeInMillis

class WelcomeActivity : MzActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
//        startActivity<LoginActivity>()
//        finish()tvSkip
        clCreateAccount.onClick {
            startActivity<LoginActivity>()
            finish()
        }

//        tvSkip.onClick {
//            mToken =""
//            startActivity<RootActivity>()
//            finish()
//        }
        tvSkip.onClick {
            mToken =""
            startActivity<RootActivity>()
            finish()
        }
        tvSwitch.onClick {
            alert(Appcompat, getString(R.string.language_change_message), getString(R.string.change_language)) {
                positiveButton(getString(R.string.restart2)) {
                    appLanguage = if ("en" == appLanguage) "ar" else "en"
                    startActivity<SplashActivity>()
                    finish()
//                        recreate()
                }
                negativeButton(getString(R.string.cancel2)) {
                    it.dismiss()
                }
            }.show()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/
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
