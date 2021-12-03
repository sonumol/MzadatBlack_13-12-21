package app.platinummzadat.qa.views.root.details

import android.content.Context
import android.content.SharedPreferences
import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR
import raj.nishin.wolfrequest.WolfRequest.Companion.context

/**
 * Created by WOLF
 * at 16:12 on Wednesday 03 April 2019
 */
class DetailsPresenter(private val view: DetailsContract.View) : DetailsContract.Presenter {
    init {
        view.presenter = this
    }
    private val sharedPrefFile = "kotlinsharedpreference"

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

    override fun refreshAuction(itemId: Int,type: Int) {
        view.refreshing()


            MApp.MzRepo.fetchDetails(currentUserId, itemId, type) { status, data, error ->
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

    override fun fetchData(itemId: Int,type: Int) {
        view.showLoading()
            MApp.MzRepo.fetchDetails(currentUserId, itemId,type) { status, data, error ->
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
    override fun fetchData1(itemId: Int,type: Int) {
        MApp.MzRepo.fetchDetails(currentUserId, itemId,type) { status, data, error ->
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
                    view.viewdata(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }

    }
}