package app.platinummzadat.qa.views.root.auctions

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 16:56 on Tuesday 02 April 2019
 */
class AuctionsPresenter(private val view: AuctionsContract.View) : AuctionsContract.Presenter {
    init {
        view.presenter = this
    }

    override fun placeBid(auctionId: Int, amount: Double,type: Int) {
        MApp.MzRepo.placeBid(currentUserId, auctionId, amount,type) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showErrorPlacingBid()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showErrorPlacingBid()
                }
                status -> {
                    view.showBidPlaced()
                }
                else -> {
                    view.showErrorPlacingBid()
                }

            }
        }
    }

    override fun fetchWishingBids( wishlistid:String,requestTime: Long, refresh: Boolean,appHash:String) {
        view.showLoading()
        MzRepo.wishingBids(wishlistid,appHash) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    if (!refresh)
                        view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    if (!refresh)
                        view.showNoInternet()
                }
                data.isEmpty() -> {
                    if (!refresh)
                        view.showEmptyData()
                }
                status -> {
                    if (refresh) view.refreshData(data, requestTime) else view.showData(data)
                }
                else -> {
                    if (!refresh)
                        view.showApiError()
                }
            }
        }
    }

//    override fun fetchWinningBids(requestTime: Long, refresh: Boolean) {
//        view.showLoading()
//        MzRepo.winningBids(currentUserId) { status, data, error ->
//            view.hideLoading()
//            when {
//                error == ERROR.API_ERROR -> {
//                    if (!refresh)
//                        view.showApiError()
//                }
//                error == ERROR.NO_INTERNET -> {
//                    if (!refresh)
//                        view.showNoInternet()
//                }
//                data.isEmpty() -> {
//                    if (!refresh)
//                        view.showEmptyData()
//                }
//                status -> {
//                    if (refresh) view.refreshData(data, requestTime) else view.showData(data)
//                }
//                else -> {
//                    if (!refresh)
//                        view.showApiError()
//                }
//            }
//        }
//    }

    override fun fetchMyBids(requestTime: Long, refresh: Boolean) {
        view.showLoading()
        MzRepo.myBids(currentUserId) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    if (!refresh)
                        view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    if (!refresh)
                        view.showNoInternet()
                }
                data.isEmpty() -> {
                    if (!refresh)
                        view.showEmptyData()
                }
                status -> {
                    if (refresh) view.refreshData(data, requestTime) else view.showData(data)
                }
                else -> {
                    if (!refresh)
                        view.showApiError()
                }
            }
        }
    }

    override fun searchAuctions(searchQuery: String, requestTime: Long, refresh: Boolean) {
        view.showLoading()
        MzRepo.searchAuctions(currentUserId, searchQuery) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    if (!refresh)
                        view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    if (!refresh)
                        view.showNoInternet()
                }
                data.isEmpty() -> {
                    if (!refresh)
                        view.showEmptyData()
                }
                status -> {
                    if (refresh) view.refreshData(data, requestTime) else view.showData(data)
                }
                else -> {
                    if (!refresh)
                        view.showApiError()
                }
            }
        }
    }

    override fun fetchAuctions(
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        requestTime: Long,
        type: Int,
        refresh: Boolean
    ) {
        view.showLoading()
        MzRepo.fetchAuctions(currentUserId, categoryId, filter, offset, limit,type) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    if (!refresh)
                        view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    if (!refresh)
                        view.showNoInternet()
                }
                data.isEmpty() -> {
                    if (!refresh)
                        view.showEmptyData()
                }
                status -> {
                    if (refresh) view.refreshData(data, requestTime) else view.showData(data)
                }
                else -> {
                    if (!refresh)
                        view.showApiError()
                }
            }
        }
    }


}