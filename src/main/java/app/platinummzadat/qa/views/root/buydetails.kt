package app.platinummzadat.qa.views.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.DetailsModel
import app.platinummzadat.qa.views.root.details.DetailsContract

class buydetails : MzActivity(), DetailsContract.View {
    override fun refreshing() {
        TODO("Not yet implemented")
    }

    override fun refreshFailed() {
        TODO("Not yet implemented")
    }

    override fun refreshData(data: DetailsModel) {
        TODO("Not yet implemented")
    }

    override fun showData(data: DetailsModel) {
        TODO("Not yet implemented")
    }

    override fun showBidPlaced(data: DetailsModel) {
        TODO("Not yet implemented")
    }

    override fun showBidExpired() {
        TODO("Not yet implemented")
    }

    override fun showErrorPlacingBid() {
        TODO("Not yet implemented")
    }

    override fun showAddedToWishList(data: String) {
        TODO("Not yet implemented")
    }

    override fun showErrorAddingToWishList() {
        TODO("Not yet implemented")
    }

    override fun showFeedbackSubmitted() {
        TODO("Not yet implemented")
    }

    override fun showFeedbackSubmissionFailed() {
        TODO("Not yet implemented")
    }

    override fun viewdata(data: DetailsModel) {
        TODO("Not yet implemented")
    }

    override lateinit var presenter: DetailsContract.Presenter
    override fun showNoInternet() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showApiError() {
        TODO("Not yet implemented")
    }

    override fun sessionTimeOut() {
        TODO("Not yet implemented")
    }

    private var isRefreshing = false
    private var isActive = false
    private var timer: CountDownTimer? = null
    private lateinit var auctionItem: DetailsModel
    lateinit var followAuctionList:Any
    private var itemId = -1
    private var bidAmount: Double = (-1).toDouble()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buydetails)
    }
}