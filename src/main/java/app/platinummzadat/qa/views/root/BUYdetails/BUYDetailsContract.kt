package app.platinummzadat.qa.views.root.BUYdetails

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.DetailsModel

interface BUYDetailsContract {
    interface View : BaseView<Presenter> {
        fun refreshing()
        fun refreshFailed()
        fun refreshData(data: DetailsModel)
        fun showData(data: DetailsModel)
        fun showBidPlaced(data: DetailsModel)
        fun showBidExpired()
        fun showErrorPlacingBid()
        fun showAddedToWishList(data: String)
        fun showErrorAddingToWishList()
        fun showFeedbackSubmitted()
        fun showFeedbackSubmissionFailed()
    }

    interface Presenter : BasePresenter {
        fun fetchData(itemId: Int)
        fun placeBid(
            auctionId: Int,
            amount: Double
        )

        fun addToWishList(
            auctionId: Int
        )

        fun refreshAuction(itemId: Int)
        fun submitFeedback(itemId: Int, feedback: String)
    }
}