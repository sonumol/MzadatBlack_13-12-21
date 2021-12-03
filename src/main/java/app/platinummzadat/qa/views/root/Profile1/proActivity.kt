package app.platinummzadat.qa.views.root.Profile1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.AuctionItemModel
import app.platinummzadat.qa.data.models.DashboardItemModel
import app.platinummzadat.qa.data.models.WinningBidsDetails
import app.platinummzadat.qa.views.root.MzFragmentListener
import app.platinummzadat.qa.views.root.RootContract
import app.platinummzadat.qa.views.root.depositamount.DepositAmountFragment
import app.platinummzadat.qa.views.root.drawer.MzNav
import kotlinx.android.synthetic.main.activity_root.*
import raj.nishin.wolfpack.replaceFragment

class proActivity : MzActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pro)
        if("profile"==intent?.extras?.getString("type")) {
//            dashboardFragment.refresh=true
            showProfile()
        }

    }

    private fun showProfile() {
        replaceFragment(ProfileFragment1(), R.id.container, true)    }





}