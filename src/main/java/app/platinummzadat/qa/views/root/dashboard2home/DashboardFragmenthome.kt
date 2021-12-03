package app.platinummzadat.qa.views.root.dashboard2home


import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import app.platinummzadat.RootActivity1
import app.platinummzadat.RootActivityAUCTIONLINK
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.DashboardModel
import app.platinummzadat.qa.data.models.FavAutionIdsRes
import app.platinummzadat.qa.views.login.LoginActivity
import app.platinummzadat.qa.views.root.RootActivity
import app.platinummzadat.qa.views.root.drawer.MzNav
import app.platinummzadat.qa.views.root.search.SearchActivity
import app.platinummzadat.qa.views.splash.SplashActivity
import com.fxn.stash.Stash
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_dashboard2.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import java.util.*
import kotlin.system.exitProcess


class DashboardFragmenthome : MzFragment(), DashboardContracthome.View {
    override lateinit var presenter: DashboardContracthome.Presenter
    var refresh = false
    private val sharedPrefFile = "kotlinsharedpreference"
    private var date1="jjj"
    private lateinit var buyproduct: String
    lateinit var editor: SharedPreferences.Editor
    private var type = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("id_key","")
        if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {

            type=1

        }
        else{
            type=2


        }


        if (firstLoad) {
            presenter.fetchDashboard(type)
            presenter.fetchFavAutionsIDs()
        }


        llMyProfile?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.MY_PROFILE)
        }
        llMyBids?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.MY_BIDS)
        }
        llWishingBids?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.WISHING_BIDS)
        }
        llSearch?.onClick {
            activity?.startActivityForResult<SearchActivity>(RC_SEARCH)
        }
        tvUploadQid?.onClick {
            refresh = true
            fragmentListener?.onSelectNavItem(MzNav.MY_PROFILE)
        }
//        val sharedPreference =  context!!.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
//        buyproduct = sharedPreference.getString("Buy", "").toString()
    }
    private fun showAppUpdateDialog() {
        val dialog = Dialog(this@DashboardFragmenthome.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.update_available_diaog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val btnPayment = dialog.findViewById(R.id.btnSubmit) as MaterialButton
        btnPayment.setOnClickListener {
            val appPackageName: String = this@DashboardFragmenthome.requireActivity().getPackageName() // getPackageName() from Context or Activity object
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }

        dialog.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(dialogInterface: DialogInterface?) {
                //do whatever you want when dialog is dismissed
                this@DashboardFragmenthome.requireActivity().moveTaskToBack(true);
                exitProcess(-1)
            }
        })

        dialog.show()



    }
    override fun showDashboard(data: DashboardModel) {
        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME
        if(!data.version_name!!.equals(versionCode.toString())){
            showAppUpdateDialog()
        }else{
            if(data.blocked!!){
                activity!!.alert(
                    Appcompat, resources.getString(R.string.account_blocked), getString(
                        R.string.failed
                    )
                ) {
                    isCancelable = false
                    positiveButton(getString(R.string.ok)) {
                        if (-1 != currentUserId) {
                            MApp.logout()
                            toast(getString(R.string.logged_out))
                            activity!!.startActivity<SplashActivity>()
                            activity!!.finish()
                        } else {
                            activity!!.startActivity<LoginActivity>()
                            activity!!.finish()
                        }
                    }
                }.show()
            }else{
                mobileNumber = data.mobileNumber ?: ""
                profilePhotoUrl = data.profilePhoto ?: ""
                username = data.username ?: ""
                fragmentListener?.refreshProfileInfo()
                tvUploadQid?.visibility(if (data.showQidUpload) gone else visible)
                val preferences: SharedPreferences=this.activity!!.getSharedPreferences(
                    "kotlinsharedpreference", Context.MODE_PRIVATE
                )
               // date1 =activity.sharedPreferences.getString("lid", null).toString()
               // val pref=activity!!.getPreferences(Context.MODE_PRIVATE)
                val id=preferences.getString("date", "")

                if(id!="")
                {
                    // tvLastLogin?.text = getString(R.string.last_login_format1)
                    val s=getString(R.string.lastara)
                    //Toast.makeText(activity,""+s,Toast.LENGTH_LONG).show()
//                    val format=SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//                   val s1=  format.id

                    if(s=="آخر تسجيل دخول")
                    {
                        tvLastLogin?.setText(id+" : "+s)
                        tvLastLogin.textAlignment = View.TEXT_ALIGNMENT_TEXT_END

//                        val rtl="id+\" : \"+s\u200F(سلام)"
//                        tvLastLogin?.setText(rtl)
                    }
                    else
                    {
                        tvLastLogin?.setText(s+" : "+id)
                    }
                }
//                val toast = Toast.makeText(activity, ""+data.lastLogin+""+id, Toast.LENGTH_LONG)
//                toast.show()

              //  tvLastLogin?.text = getString(R.string.last_login_format,id)



                tvUsername?.text = getString(R.string.greet_format, data.username)
                tvScroll?.text = data.scrollText
                tvScroll?.visibility(if (data.scrollText.trim().isEmpty()) gone else visible)
//                rvDashboard?.layoutManager(GridLayoutManager(activity, 2))
//                rvDashboard?.nestedScroll(false)
////        rvDashboard?.addItemDecoration(SimpleDividerItemDecoration(activity))

                if (null != activity) {
                    val verticalDecoration = DividerItemDecoration(
                        activity,
                        DividerItemDecoration.HORIZONTAL
                    )
//                    val verticalDivider = activity?.resources!!.getDrawable(R.drawable.grid_vertical_divider)
//                    verticalDecoration.setDrawable(verticalDivider)
//                        // rvDashboard?.addItemDecoration(verticalDecoration)
//
//                    val horizontalDecoration = DividerItemDecoration(
//                        activity,
//                        DividerItemDecoration.VERTICAL
//                    )
//                    val horizontalDivider = activity?.resources!!.getDrawable(R.drawable.grid_horizontal_divider)
//                    horizontalDecoration.setDrawable(horizontalDivider)
                   // rvDashboard?.addItemDecoration(horizontalDecoration)
                }

//                rvDashboard?.adapter = DashboardAdapterhome(data.items) {
//                    fragmentListener?.onSelectDashboardItem(it)
//                }
                ivProfilePhoto?.loadAvatar(
                    profilePhotoUrl,
                    R.drawable.ic_account_circle_primary_dark
                )
                content?.visibility(visible)
                llUserDetails?.visibility(if (-1 == currentUserId) gone else visible)
                tvUploadQid?.visibility(
                    if (-1 == currentUserId) gone else {
                        if (data.showQidUpload) visible else gone
                    }
                )
                tvUploadQid?.text = data.qidMessage ?: getString(R.string.upload_qid_info_dashboard)
                tvScroll?.requestFocus()
            }
        }




    }

    override fun showFavIds(data: FavAutionIdsRes) {
        var followAuctionList = Stash.getStringSet("favoriteProductIdsList")
        followAuctionList.clear()
        if(data.data!=null){
         data.data.forEach {
             followAuctionList.add(it)
             Log.d("favoriteProductIdsList", it)
         }

            Stash.put("favoriteProductIdsList", followAuctionList)
        }



    }

    override fun lastActiveUpdated() {
       // quitApp()
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        content?.visibility(gone)
        error?.visibility(gone)
    }

    override fun hideLoading() {

        loading?.visibility(gone)
//        swipeRefresh.isRefreshing = false
    }

    override fun showApiError() {
        error?.visibility(visible)
        fragmentListener?.onError {
            presenter.fetchDashboard(type)
        }
    }
    fun quitApp() {
      //  finishAffinity()
        System.exit(0)
    }

    override fun sessionTimeOut() {
    }

    override fun onResume() {
        super.onResume()
        if (refresh) {
            refresh = false
            presenter.fetchDashboard(type)
        }
        fragmentListener?.setTitle(getString(R.string.court_mzadat))
        tvScroll?.requestFocus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DashboardPresenterhome(this)
        val v=inflater.inflate(R.layout.fragment_dashboard2, container, false)
        val auction = v.findViewById(R.id.l22) as LinearLayout
        val directpurchase = v.findViewById(R.id.l2) as LinearLayout
        auction.setOnClickListener {
           // Toast.makeText(context,"kkk", Toast.LENGTH_LONG)
            val intent=Intent(context,RootActivityAUCTIONLINK::class.java)
            val preferences: SharedPreferences=context!!.getSharedPreferences("prcount", 0)
            preferences.edit().remove("pr_count").commit()
            startActivity(intent)
        }
        directpurchase.setOnClickListener {
            val intent=Intent(context,RootActivity1::class.java)

            startActivity(intent)
        }
        return v
    }

}
