package app.platinummzadat.qa.views.root.Buyingauction1

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.AuctionItemModel
import app.platinummzadat.qa.data.models.WishingBiData

interface BUYAuctionsContract {
    interface View : BaseView<Presenter> {
        fun showData(dataSet: ArrayList<AuctionItemModel>)
        //   fun showDataWishing(dataSet: ArrayList<WishingBiData>)

        fun refreshData(dataSet: ArrayList<AuctionItemModel>, requestTime: Long)
        //    fun refreshDataWishing(dataSet: ArrayList<WishingBiData>, requestTime: Long)
        fun showBidPlaced()
        fun showErrorPlacingBid()
    }

    interface Presenter : BasePresenter {

        fun fetchAuctions1(categoryId: Int, filter: Int, offset: Int, limit: Int, requestTime: Long, refresh: Boolean)
        fun searchAuctions1(searchQuery: String, requestTime: Long, refresh: Boolean)
        fun fetchMyBids(requestTime: Long, refresh: Boolean)
        fun fetchWishingBids(
            wishlistid:String,requestTime: Long, refresh: Boolean,appHash:String)

        fun placeBid(
            auctionId: Int,
            amount: Double,
            type: Int
        )
    }
}