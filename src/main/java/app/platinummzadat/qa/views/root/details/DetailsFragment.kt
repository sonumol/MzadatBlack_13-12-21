package app.platinummzadat.qa.views.root.details


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import com.fxn.stash.Stash
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.DetailsModel
import app.platinummzadat.qa.views.login.LoginActivity
import app.platinummzadat.qa.views.root.bidAlert
import app.platinummzadat.qa.views.root.depositamount.DepositAmountFragment
import app.platinummzadat.qa.views.root.details.slider.SliderActivity
import app.platinummzadat.qa.views.root.feedbackAlert
import app.platinummzadat.qa.views.root.specsAlert
import app.platinummzadat.qa.views.splash.SplashActivity
import com.synnapps.carouselview.ViewListener
import kotlinx.android.synthetic.main.fragment_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.share
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import java.text.NumberFormat


private const val ARG_AUCTION_ITEM_ID = "auction_item_id"
private const val REFRESH_DELAY: Long = 3000

class DetailsFragment : MzFragment(), DetailsContract.View {
    override lateinit var presenter: DetailsContract.Presenter
    private var isRefreshing = false
    private var isActive = false
    private var timer: CountDownTimer? = null
    private lateinit var auctionItem: DetailsModel
    lateinit var followAuctionList:Any
    private var itemId = -1
    private var bidAmount: Double = (-1).toDouble()
    private var type = 0
    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("id_key","")
        if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {

            type=1
//            Toast.makeText(MApp.applicationContext(),""+ type ,Toast.LENGTH_LONG).show()

        }
        else{
            type=2
//            Toast.makeText(MApp.applicationContext(),""+ type ,Toast.LENGTH_LONG).show()

        }
        presenter.fetchData(itemId,type)
        presenter.fetchData1(itemId,type)

    }

    private var feedback = ""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun showData(data: DetailsModel) {
        Log.d("ka",data.toString())
        if(data.user_blocked!=null){
            if(data.user_blocked!!){
                activity!!.alert(Appcompat, resources.getString(R.string.account_blocked), getString(R.string.failed)) {
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
                bidAmount = (-1).toDouble()
                auctionItem = data
                if(!auctionItem.judge_live_link.equals("")){
                    btnDn1.visibility=View.VISIBLE
                    btnDn1.setText(auctionItem.judge_live_button_text)
                    containerTopButtons.visibility=View.VISIBLE
                }else{
                    btnDn1.visibility=View.GONE
                }
                if(!auctionItem.brochure_link.equals("")){
                    btnDn2.visibility=View.VISIBLE
                    btnDn2.setText(auctionItem.brochure_button_text)
                    containerTopButtons.visibility=View.VISIBLE
                }else{
                    btnDn2.visibility=View.GONE
                }
                btnDn2.setOnClickListener {
                    loadUrlIntent(auctionItem.brochure_link.toString())
                }
                btnDn1.setOnClickListener {
                    loadUrlIntent(auctionItem.judge_live_link.toString())
                }
                tv_label_depositamt.text= getString(R.string.deposit_amount_required, NumberFormat.getNumberInstance().format(auctionItem.deposit_amount))
                tvPrice?.text = getString(R.string.currency_format, NumberFormat.getNumberInstance().format(auctionItem.price))
                fragmentListener?.setTitle(auctionItem.referenceNumber)
                if (null != sliderItem) {
                    if (sliderItem.pageCount != data.images.size) {
                        sliderItem?.setViewListener(object : ViewListener {
                            override fun setViewForPosition(position: Int): View {
                                val sliderView: ImageView = layoutInflater.inflate(R.layout.slider_image, null) as ImageView
                                sliderView.loadFromUrl(data.images[position])
                                sliderView?.onClick {
                                    activity?.startActivity<SliderActivity>("urls" to data.images, "pos" to position)
                                }
                                return sliderView
                            }
                        })
                        sliderItem?.pageCount = data.images.size
                    }
                }
                tvName?.text = data.name
                tvMinimumIncrement?.text =
                        getString(R.string.min_increment_format, NumberFormat.getNumberInstance().format(auctionItem.increment))

                tvInspectionReportTitle?.text = data.inspectionReportTitle
                tvCourt?.text = getString(R.string.court_format, auctionItem.company, auctionItem.location)
                tvTotalBidsBadge?.text = data.totalBidsBadge

                //changes mba
              //  addOrEditLocalFollow()

                var followAuctionList = Stash.getStringSet("favoriteProductIdsList")
                if(followAuctionList!=null){
                    followAuctionList.forEach {
                        if(data.id.toString().equals(it)){
                            tvFollowAuction?.text = resources.getString(R.string.following_auction)
                            ivFollowAuction?.setImageDrawable(resources.getDrawable(R.drawable.ic_item_view_unfollow_auction))
                        }
                    }
                }
                llFollowAuction?.onClick {
                    if (-1 != currentUserId) {
                        presenter.addToWishList(itemId)
                        tvFollowAuction?.text = resources.getString(R.string.following_auction)
                        ivFollowAuction?.setImageDrawable(resources.getDrawable(R.drawable.ic_item_view_unfollow_auction))
                        var isExist=false
                        if(followAuctionList!=null){
                            followAuctionList.forEach {
                                if(it == data.id.toString()){
                                    //followAuctionList.remove(it)
                                    isExist=true
                                    tvFollowAuction?.text = resources.getString(R.string.follow_this_auction)
                                    ivFollowAuction?.setImageDrawable(resources.getDrawable(R.drawable.ic_item_view_follow_auction))

                                  return@forEach
                                }
                            }
                           if(isExist){
                               followAuctionList.remove(data.id.toString())
                           }else{
                               followAuctionList.add(data.id.toString())
                               tvFollowAuction?.text = resources.getString(R.string.following_auction)
                               ivFollowAuction?.setImageDrawable(resources.getDrawable(R.drawable.ic_item_view_unfollow_auction))
                           }
                            Stash.put("favoriteProductIdsList", followAuctionList)
                        }



                    } else {
                        fragmentListener?.onErrorWithAutoHideMessage(
                                getString(R.string.please_login_to_continue),
                                getString(app.platinummzadat.qa.R.string.login)
                        ) {
                            with(activity!!) {
                                startActivity<LoginActivity>()
                                finish()
                            }

                        }
                    }
                }


                btnBidNow?.onClick {
                    if (-1 != currentUserId) {
//                if (true) {
                        if (auctionItem.bidStatus.enabled) {
                            activity?.bidAlert(auctionItem.price, auctionItem.increment) { userBidAmount ->
                                val progress = ProgressDialog(activity).apply {
                                    setMessage(getString(R.string.checking_bid_status))
                                    setCancelable(false)
                                }
                                progress.show()
                                Handler().postDelayed({
                                    progress.dismiss()
                                    if (auctionItem.price + auctionItem.increment <= userBidAmount) {
                                        bidAmount = userBidAmount
                                        presenter.placeBid(itemId, bidAmount,type)
                                    } else {
                                        activity?.alert(
                                                Appcompat,
                                                getString(R.string.someone_placed_another_bid),
                                                getString(R.string.can_not_place_bid)
                                        ) {
                                            positiveButton(getString(R.string.close)) {
                                                it.dismiss()
                                            }
                                        }?.show()
                                    }
                                }, REFRESH_DELAY)
                            }
                        } else {
                            activity?.alert(
                                    Appcompat,
                                    auctionItem.bidStatus.reason,
                                    getString(R.string.can_not_place_bid)
                            ) {
                                positiveButton(getString(R.string.add_deposit_amount)) { fragmentListener?.onSelectDepositAmount(DepositAmountFragment()) }
                                negativeButton(getString(R.string.ok)) {it.dismiss()}
                            }?.show()
                        }

                    } else {
                        fragmentListener?.onErrorWithAutoHideMessage(
                                getString(R.string.please_login_to_continue),
                                getString(R.string.login)
                        ) {
                            with(activity!!) {
                                startActivity<LoginActivity>()
                                finish()
                            }

                        }
                    }
                }
//                Toast.makeText(MApp.applicationContext(),"d"+data.prev_user_country,Toast.LENGTH_LONG).show()
  if(data.prev_user_country=="0")
  {
      lastbideer.visibility=View.GONE
      country_code_picker.visibility=View.GONE
  }

                if(data.price_label=="0")
                {
                    selling.setText(data.price_label_name)
                    selling.visibility=View.GONE
                    lastbideer.visibility=View.GONE
                    country_code_picker.visibility=View.GONE

                }

                else if(data.price_label=="1")
                {
                    selling.setText(data.price_label_name)
                    selling.background.setTint(context!!.resources.getColor(R.color.red))
//
//
                    country_code_picker.visibility=View.VISIBLE

                }
                else if(data.price_label=="2")
                {
                    selling.setText(data.price_label_name)
                    selling.background.setTint(context!!.resources.getColor(R.color.green))
                    country_code_picker.visibility=View.VISIBLE

                }
                else if(data.price_label=="3")
                {
                    selling.setText(data.price_label_name)
                    selling.background.setTint(context!!.resources.getColor(R.color.colorYellow))
                    country_code_picker.visibility=View.VISIBLE



                }
              //  Toast.makeText(MApp.applicationContext(),"d"+data.price_label_name+data.price_label+data.prev_user_country+data.prev_user_msg,Toast.LENGTH_LONG).show()
                btnBidNow?.setText(data.button_name)
                tvEndTime?.text = getString(R.string.end_time_format, data.endingDetails)
                if (data.auction_status==1) {

                    btnBidNow?.isEnabled = true
                } else if(data.auction_status==2){
                    tvEndTime?.text = getString(R.string.start_time_format, data.upcomingStartingDetails)
                    btnBidNow?.isEnabled = false
                    btnBidNow.setTextColor(resources.getColor(R.color.solid_black))

                }else if (data.auction_status==3){
                    btnBidNow?.isEnabled = false
                    btnBidNow.setTextColor(resources.getColor(R.color.solid_black))
                }else{
                    btnBidNow?.isEnabled = false
                    btnBidNow.setTextColor(resources.getColor(R.color.solid_black))
                }

                llFeedback?.onClick {
                    activity?.feedbackAlert {
                        feedback = it
                        toast(getString(R.string.submitting_feedback))
                        presenter.submitFeedback(itemId, feedback)
                    }
                }
                llTerms?.onClick {
                    if (data.terms.isNotEmpty()) {
                        activity?.alert(Appcompat, data.terms, getString(app.platinummzadat.qa.R.string.terms_and_conditions)) {
                            positiveButton(getString(R.string.ok)) { it.dismiss() }
                        }?.show()
                    } else {
                        toast(getString(R.string.no_data_available))
                    }
                }
                llShareBid?.onClick {
                   // Log.d("ad",data.webUrl)
                    share(getString(R.string.share_bid_content, data.name, data.webUrl))
                }
                if (data.inspectionLat.isNotEmpty() && data.inspectionLng.isNotEmpty()) {
                    llInspectionLocation?.onClick {
                        startActivity(
                                Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?q=loc:${data.inspectionLat},${data.inspectionLng}")
                                )
                        )
                    }
                    cvInspectionLocation?.visibility(visible)
                } else {
                    cvInspectionLocation?.visibility(gone)// toast (getString(com.weblanza.mzadat.R.string.location_not_available))
                }
                if (data.inspectionReport.isEmpty()) {
                    cvInspectionReport?.visibility(gone)
//                toast(getString(R.string.no_data_available))
                } else {
                    cvInspectionReport?.visibility(visible)
                    llInspectionReport?.onClick {
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(
                                activity,
                                Uri.parse(data.inspectionReport)
                        )
                    }
                }
                if (0 != data.specifications.size) {
                    cvSpecs?.visibility(visible)
                    llSpecifications?.onClick {
                        activity?.specsAlert(data.specifications)
                    }
                } else {
                    cvSpecs?.visibility(gone)
//            toast(getString(R.string.specs_unavailable))
                }
                startTimer()
                clDescription?.visibility(if (data.description.isNotEmpty()) visible else gone)
                val desc= Html.fromHtml(data.description)
                if(desc.length>200){
                    tvDescription?.text =
                            if (data.description.length > 200) {
                                tvMore?.visibility(visible)
                                desc.substring(0, 200) + "..."
                            } else {
                                tvMore?.visibility(gone)
                                desc
                            }
                }else{
                    tvDescription?.text =desc
                    tvMore?.visibility(gone)
                }


                clDescription?.onClick {
                    showDescription(desc.toString())
                }
                tvDescription?.onClick {
                    showDescription(desc.toString())
                }
                tvMore?.onClick {
                    showDescription(desc.toString())
                }

                btnBidNow?.visibility(if (data.previousBid) gone else visible)
                tvReason?.visibility(if (data.previousBid) visible else gone)
                if (null != data.companyLogo) {
                    if (data.companyLogo.isEmpty()) {
                        ivProvidedBy?.visibility(gone)
                    } else {
                        ivProvidedBy?.loadFromUrl(data.companyLogo)
                        ivProvidedBy?.visibility(visible)
                    }
                }
                content?.visibility(visible)
            }
        }

    }

    fun addOrEditLocalFollow(){


        /*followAuctionList.forEach {
            Log.d("favoriteProductIdsList",it)
        }*/


    }
    override fun showBidExpired() {
        activity?.alert(Appcompat, getString(R.string.this_auction_is_expired), getString(R.string.can_not_place_bid)){
            positiveButton(getString(R.string.ok)){
                it.dismiss()
            }
        }?.show()
    }
    private fun showDescription(description: String) {
        activity?.alert(Appcompat, description) {
            positiveButton(getString(R.string.close)) { it.dismiss() }
        }?.show()
    }

    override fun showFeedbackSubmissionFailed() {
        if ("" != feedback) {
            fragmentListener?.onError {
                presenter.submitFeedback(itemId, feedback)
            }
        }
    }

    override fun viewdata(data: DetailsModel) {
       // Toast.makeText(MApp.applicationContext(),"d"+data.prev_user_country,Toast.LENGTH_LONG).show()
        if(data.price_label=="0")
        {

            lastbideer.visibility=View.GONE
            country_code_picker.visibility=View.GONE

        }

        else if(data.price_label=="1")
        {

            lastbideer.setText(data.prev_user_msg)
            country_code_picker.visibility=View.VISIBLE
            country_code_picker.setCountryForNameCode(data.prev_user_country)



        }
        else if(data.price_label=="2")
        {

            lastbideer.setText(data.prev_user_msg)
            country_code_picker.visibility=View.VISIBLE
            country_code_picker.setCountryForNameCode(data.prev_user_country)

//                    Toast.makeText(MApp.applicationContext(),"d"+data.prev_user_country,Toast.LENGTH_LONG).show()

        }
        else if(data.price_label=="3")
        {

            lastbideer.setText(data.prev_user_msg)
            country_code_picker.visibility=View.VISIBLE
            country_code_picker.setCountryForNameCode(data.prev_user_country)



        }
    }

    override fun showFeedbackSubmitted() {
        feedback = ""
        toast(getString(R.string.feedback_submitted))
    }

    override fun refreshData(data: DetailsModel) {
        isRefreshing = false
        showData(data)
    }

    override fun refreshFailed() {
        isRefreshing = false
    }

    override fun refreshing() {
        isRefreshing = true
    }

    override fun showAddedToWishList(data: String) {
       // showData(data)

    }

    override fun showBidPlaced(data: DetailsModel) {
       // showData(data)
        presenter.fetchData(itemId,type)
        toast(getString(R.string.bid_placed))
    }

    override fun showErrorAddingToWishList() {
        fragmentListener?.onError {
            presenter.addToWishList(itemId)
        }
    }

    override fun showErrorPlacingBid() {
        fragmentListener?.onError {
            presenter.placeBid(itemId, bidAmount,type)
        }
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        content?.visibility(gone)
        loading?.visibility(visible)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        itemId = arguments?.getInt(ARG_AUCTION_ITEM_ID) ?: -1
        fragmentListener?.setTitle(getString(R.string.app_name))
        DetailsPresenter(this)
        return super.onCreateView(R.layout.fragment_details, inflater, container)
    }

    companion object {
        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_AUCTION_ITEM_ID, id)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        isActive = false
    }

   fun loadUrlIntent(url: String){
       val uri = Uri.parse(url)
       val intent = Intent(Intent.ACTION_VIEW, uri)
       this@DetailsFragment.requireActivity().startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        isActive = true
        startTimer()
    }

    private val refresh: Runnable = object : Runnable {
        override fun run() {
            if (!isRefreshing && isActive) {
                presenter.refreshAuction(itemId,type)
                refreshHandler.postDelayed(this, REFRESH_DELAY)
            }
        }
    }
    private val refreshHandler = Handler()

    private fun startTimer() {
        if (this::auctionItem.isInitialized) {
            fragmentListener?.setTitle(auctionItem.referenceNumber)
            if (auctionItem.auction_status==1) {
                timer?.cancel()
                tvTimeLeft1.text =resources!!.getString(R.string.expireson)+" "
                timer = object : CountDownTimer(auctionItem.millisUntilExpiry, 1000L) {
                    override fun onFinish() {
                        tvTimeLeft?.text = resources.getString(R.string.expired)
                        btnBidNow.text =resources!!.getString(R.string.expired)
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                       ;
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        tvTimeLeft?.text =
                            getTimeStamp(millisUntilFinished)
                    }
                }
                timer?.start()
            }else if(auctionItem.auction_status==2){
                timer?.cancel()
                btnBidNow.isEnabled=false
                btnBidNow.isClickable=false
                tvTimeLeft1.setText(resources!!.getString(R.string.starton))
                timer = object : CountDownTimer(auctionItem.upComimgBidTimer, 1000L) {
                    override fun onFinish() {
                        timer?.cancel()
                        btnBidNow.isEnabled=true
                        btnBidNow.isClickable=true
                        timer = object : CountDownTimer(auctionItem.millisUntilExpiry, 1000L) {
                            override fun onFinish() {
                                tvTimeLeft?.text = resources.getString(R.string.expired)
                                btnBidNow.text =resources!!.getString(R.string.expired)
                                btnBidNow.isEnabled=false
                                btnBidNow.isClickable=false
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                tvTimeLeft?.text =
                                        getTimeStamp(millisUntilFinished)
                            }
                        }
                        timer?.start()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        tvTimeLeft?.text =
                                getTimeStamp(millisUntilFinished)
                    }
                }
                timer?.start()
            }else if(auctionItem.auction_status==3){
                tvTimeLeft?.text = resources.getString(R.string.expired)
                btnBidNow.text =resources!!.getString(R.string.expired)
                btnBidNow.isEnabled=false
                btnBidNow.isClickable=false
            }else{
                tvTimeLeft?.text = auctionItem.button_name
                btnBidNow.text =auctionItem.button_name
                btnBidNow.isEnabled=false
                btnBidNow.isClickable=false
            }
            refreshHandler.postDelayed(refresh, REFRESH_DELAY)

        }
    }
}
