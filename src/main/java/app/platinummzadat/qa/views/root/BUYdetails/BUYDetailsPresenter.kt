package app.platinummzadat.qa.views.root.BUYdetails

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 16:12 on Wednesday 03 April 2019
 */
class BUYDetailsPresenter(private val view: BUYDetailsContract.View) : BUYDetailsContract.Presenter {
    init {
        view.presenter = this
    }

    override fun submitFeedback(itemId: Int, feedback: String) {
        MApp.MzRepo.submitFeedback(currentUserId, itemId, feedback) { status, error ->
            when {
                status -> {
                    view.showFeedbackSubmitted()
                }
                else -> {
                    view.showFeedbackSubmissionFailed()
                }
            }
        }
    }

    override fun refreshAuction(itemId: Int) {
        view.refreshing()
        MApp.MzRepo.fetchDetails1(currentUserId, itemId) { status, data, error ->
            when {
                null == data -> {
                    view.refreshFailed()
                }
                status -> {
                    view.refreshData(data)
                }
                else -> {
                    view.refreshFailed()
                }
            }
        }

    }

    override fun addToWishList(auctionId: Int) {
        MApp.MzRepo.addToWishList(currentUserId, auctionId) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showErrorAddingToWishList()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showErrorAddingToWishList()
                }
                status -> {
                    view.showAddedToWishList(data)
                }
                else -> {
                    view.showErrorAddingToWishList()
                }
            }
        }
    }

    override fun placeBid(auctionId: Int, amount: Double) {
        MApp.MzRepo.placeproduct(currentUserId, auctionId, amount) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showErrorPlacingBid()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                error == ERROR.BID_EXPIRED -> {
                    view.showBidExpired()
                }

                null == data -> {
                    view.showErrorPlacingBid()
                }
                status -> {
                    view.showBidPlaced(data)
                }
                else -> {
                    view.showErrorPlacingBid()
                }
            }
        }
    }

    override fun fetchData(itemId: Int) {
       // view.showLoading()
        MApp.MzRepo.fetchDetails1(currentUserId, itemId) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showEmptyData()
                }
                status -> {
                    view.showData(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }

    }

}