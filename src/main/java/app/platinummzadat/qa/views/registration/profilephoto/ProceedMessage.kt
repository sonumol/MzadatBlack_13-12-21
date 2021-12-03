package app.platinummzadat.qa.views.registration.profilephoto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.R
import app.platinummzadat.qa.views.root.Profile1.ProfileFragment1
import app.platinummzadat.qa.views.root.Profile1.proActivity
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.root.companyfees.CompanyFeesFragment
import app.platinummzadat.qa.views.root.home_firstpage.Home_first_Activity
import app.platinummzadat.qa.views.root.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_proceed_message.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import raj.nishin.wolfpack.replaceFragment


class ProceedMessage : MzActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceed_parent)
        btnProceed.onClick {
            // showSuccess()
            val intent=Intent(this@ProceedMessage, RootActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("type", "profile")
            startActivity(intent)
        }
        val sharedPreferences: SharedPreferences= this.getSharedPreferences("countrycode",
            Context.MODE_PRIVATE)
         var countryCode =sharedPreferences.getString("countryCode","")
 if(countryCode.equals("974"))
 {
     textView3.setText(getString(R.string.before_proceed_message))
 }
        else{
     textView3.setText(getString(R.string.before_proceed_message1))
 }

    }


}