package app.platinummzadat.qa.views.tosactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import app.platinummzadat.qa.R
import app.platinummzadat.qa.views.root.tac.TermsAndConditionsFragment

import kotlinx.android.synthetic.main.activity_terms_of_service.*
import raj.nishin.wolfpack.replaceFragment

class TermsOfServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(TermsAndConditionsFragment(),R.id.container)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
