package app.platinummzadat.qa.views.root.Buyingauction1


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.*
import app.platinummzadat.qa.data.models.AuctionItemModel
import app.platinummzadat.qa.views.root.Buyingauction.AUCTION_ADAPTER_GRID
import app.platinummzadat.qa.views.root.Buyingauction.AUCTION_ADAPTER_LIST
import app.platinummzadat.qa.views.root.Buyingauction.BUYAuctionAdapter
import app.platinummzadat.qa.views.root.auctions.BUYAuctionsContract
import app.platinummzadat.qa.views.root.auctions.BUYAuctionsPresenter
import com.fxn.stash.Stash
import kotlinx.android.synthetic.main.fragment_auctions.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*

private const val ARG_SHOW_MODE = "show_mode"
private const val ARG_CAT_ID = "cat_id"
private const val ARG_CAT_NAME = "cat_name"
private const val ARG_SEARCH_QUERY = "search_query"
const val AUCTION_MODE_ALL = 1
const val AUCTION_MODE_MY_BIDS = 2
const val AUCTION_MODE_WISHING_BIDS = 3
const val AUCTION_MODE_SEARCH = 4
const val FILTER_NONE = 0
const val FILTER_PRICE = 1
const val FILTER_ENDING = 2
const val FILTER_BIDS = 4


private const val REFRESH_DELAY: Long = 3000

class BUYAuctionsFragment : MzFragment(), BUYAuctionsContract.View {

    override lateinit var presenter: BUYAuctionsContract.Presenter
    private var displayMode = AUCTION_ADAPTER_GRID
    private var silentLoad = false
    private var categoryId = -1
    private var filter = FILTER_NONE
    private var bidAmount: Double = (-1).toDouble()
    private var bidItemId = -1
    private var categoryName = ""
    private var searchQuery = ""
    private var showMode = AUCTION_MODE_ALL

    private var offset = 0
    private val limit = 15
    private var isLoading = false
    private var hasMoreData = true
    private var visibleItemCount = -1
    private var totalItemCount = -1
    private var pastVisibleItems = -1

    private var changingDisplayMode = false
    private var itemIndex = 0

    private var requestTime = currentLocalTimeInMillis
    private var isRefreshing = false
    private var isActive = false
    private lateinit var buyproduct: String

    lateinit var adapter: BUYAuctionAdapter
    private var type = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentListener?.setTitle(categoryName)
        adapter = prepareAdapter()
        silentLoad = false
        offset = 0
        itemIndex = 0
        hasMoreData = true


        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences("kotlinsharedpreference",
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

        cvFilters?.visibility(if (AUCTION_MODE_ALL == showMode) visible else gone)
        when (showMode) {
            AUCTION_MODE_SEARCH -> {
                presenter.searchAuctions1(searchQuery, requestTime, false)

            }
            AUCTION_MODE_MY_BIDS -> {
                presenter.fetchMyBids(requestTime, false)
            }
            AUCTION_MODE_WISHING_BIDS -> {
                var followAuctionList = Stash.getStringSet("favoriteProductIdsList")
                var  auctionid=followAuctionList.toString().replace("[","").replace("]","")

                presenter.fetchWishingBids(auctionid,requestTime, false, AppSignatureHelper(this@BUYAuctionsFragment.requireContext()).appSignatures[0])
            }
            else -> {
                presenter.fetchAuctions1(categoryId, filter, offset, limit, requestTime, false)
            }

        }
    }


    override fun showData(dataSet: ArrayList<AuctionItemModel>) {
//Toast.makeText(context,"aa"+dataSet,Toast.LENGTH_LONG).show()
        Log.d("TAGasd", dataSet.toString())
        bidAmount = (-1).toDouble()
        bidItemId = -1
        rvAuctions?.itemOffset(8)
        adapter = prepareAdapter(adapter.dataSet)
        rvAuctions?.adapter = adapter

        if (changingDisplayMode) {
            changingDisplayMode = false
        } else {
            hasMoreData = dataSet.size == limit
            if (0 == offset) {

                adapter.resetItems(dataSet)
            } else {
                adapter.insertItems(dataSet)
            }
        }
        if (AUCTION_ADAPTER_LIST == displayMode) {
            rvAuctions?.layoutManager(LinearLayoutManager(activity))
            ivDisplayMode?.setImageResource(R.drawable.ic_view_grid)
            rvAuctions?.let {
                (it.layoutManager as LinearLayoutManager).scrollToPosition(itemIndex)
            }
        } else {
            rvAuctions?.layoutManager(GridLayoutManager(activity, 2))
            ivDisplayMode?.setImageResource(R.drawable.ic_view_list)
            rvAuctions?.let {
                (it.layoutManager as GridLayoutManager).scrollToPosition(itemIndex)
            }
        }
        ivDisplayMode?.onClick {
            displayMode = if (AUCTION_ADAPTER_LIST == displayMode) {
                rvAuctions?.let {
                    itemIndex = (it.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                }
                AUCTION_ADAPTER_GRID
            } else {
                rvAuctions?.let {
                    itemIndex = (it.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
                }
                AUCTION_ADAPTER_LIST
            }
            changingDisplayMode = true
            showData(adapter.dataSet)
        }
        rvAuctions?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && hasMoreData) {
                    visibleItemCount = rvAuctions?.layoutManager?.childCount ?: 0
                    totalItemCount = rvAuctions?.layoutManager?.itemCount ?: 0
                    rvAuctions?.let {
                        pastVisibleItems = if (AUCTION_ADAPTER_LIST == displayMode) {
                            (it.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        } else {
                            (it.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                        }
                    }
                    if (((visibleItemCount + pastVisibleItems) >= totalItemCount) && !isLoading) {
                        offset += limit
                        itemIndex = offset - 1
                        presenter.fetchAuctions1(categoryId, filter, offset, limit, requestTime, false)
                    }
                }
            }

        })

        tvPrice?.onClick {
            filter = FILTER_PRICE
            onClickFilter(tvPrice)
        }

        tvEnding?.onClick {
            filter = FILTER_ENDING
            onClickFilter(tvEnding)
        }

        tvBid?.onClick {
            filter = FILTER_BIDS
            onClickFilter(tvBid)
        }


        content?.visibility(visible)
    }

    private fun prepareAdapter(dataSet: ArrayList<AuctionItemModel> = ArrayList()) =
        BUYAuctionAdapter(this@BUYAuctionsFragment.requireContext(),dataSet, displayMode, fnOnClickItem = {
            fragmentListener?.onSelectAuctionItem(it)
        }, fnOnClickBidNow = { auctionItem ->
            fragmentListener?.onSelectAuctionItem(auctionItem)
            /*if (-1 != currentUserId) {
                if (auctionItem.bidStatus.enabled) {
                    activity?.bidAlert(auctionItem.price, auctionItem.increment) { userBidAmount ->
                        val progress = ProgressDialog(activity).apply {
                            setMessage(getString(R.string.checking_bid_status))
                            setCancelable(false)
                        }
                        progress.show()
                        Handler().postDelayed({
                            progress.dismiss()
                            val updatedItem = adapter.dataSet.find { it.id == auctionItem.id }
                            if (updatedItem == null || (updatedItem?.price ?: 0.toDouble()) + (updatedItem?.increment ?: 0.toDouble()) <= userBidAmount
                            ) {
                                bidAmount = userBidAmount
                                bidItemId = auctionItem.id
                                presenter.placeBid(bidItemId, bidAmount)
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
                        positiveButton(getString(R.string.ok)) { it.dismiss() }
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
            }*/

        }).apply { setHasStableIds(true) }


    private fun onClickFilter(tvFilter: TextView) {
        silentLoad = false
        offset = 0
        itemIndex = 0
        val newState = !tvFilter.isSelected
        tvPrice.isSelected = false
        tvBid.isSelected = false
        tvEnding.isSelected = false
        tvFilter.isSelected = newState
        if (!newState) {
            filter = FILTER_NONE
        }
       // arrayOf(Toast.makeText(context,""+filter ,Toast.LENGTH_LONG))
        presenter.fetchAuctions1(categoryId, filter, offset, limit, requestTime, false)
    }


    override fun showEmptyData() {
        noData?.visibility(visible)
    }

    override fun showErrorPlacingBid() {
        fragmentListener?.onError {
            presenter.placeBid(bidItemId, bidAmount,type)
        }
    }

    override fun showBidPlaced() {
        toast(getString(R.string.bid_placed))
        refreshPage()
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        isLoading = true
        if (silentLoad) {
            if (!isRefreshing) {
                pbarSilent?.visibility(visible)
            }
        } else {
            loading?.visibility(visible)
            content?.visibility(gone)
            silentLoad = true
        }
        noData?.visibility(gone)
    }

    override fun hideLoading() {
        isLoading = false
        loading?.visibility(gone)
        pbarSilent?.visibility(invisible)
    }

    override fun showApiError() {
//        Toast.makeText(context,"ssss",Toast.LENGTH_LONG).show()
    }

    override fun sessionTimeOut() {
    }

    override fun refreshData(dataSet: ArrayList<AuctionItemModel>, requestTime: Long) {
        isRefreshing = false
        if (requestTime != this.requestTime)
            return
        adapter.refresh(dataSet)

    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onResume() {
        super.onResume()
        isActive = true
        refreshHandler.postDelayed(refresh, REFRESH_DELAY)
    }

    private val refresh: Runnable = object : Runnable {
        override fun run() {
            if (!isRefreshing && isActive) {
                refreshPage()
                refreshHandler.postDelayed(this, REFRESH_DELAY)
            }
        }
    }

    private fun refreshPage() {
        requestTime = currentLocalTimeInMillis
        isRefreshing = true
        when (showMode) {
            AUCTION_MODE_SEARCH -> {
                presenter.searchAuctions1(searchQuery, requestTime, true)
            }
            AUCTION_MODE_MY_BIDS -> {
                presenter.fetchMyBids(requestTime, true)
            }
            AUCTION_MODE_WISHING_BIDS -> {
                var followAuctionList = Stash.getStringSet("favoriteProductIdsList")
                var  auctionid=followAuctionList.toString().replace("[","").replace("]","")

                presenter.fetchWishingBids(auctionid,requestTime, true,AppSignatureHelper(this@BUYAuctionsFragment.requireContext()).appSignatures[0])
            }
            else -> {
                presenter.fetchAuctions1(categoryId, filter, 0, offset + limit, requestTime, true)
            }
        }
    }

    private val refreshHandler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            categoryId = it.getInt(ARG_CAT_ID)
            categoryName = it.getString(ARG_CAT_NAME) ?: getString(R.string.app_name)
            showMode = it.getInt(ARG_SHOW_MODE, AUCTION_MODE_ALL)
            searchQuery = it.getString(ARG_SEARCH_QUERY).toString()
        }
        // Toast.makeText(context,"g"+categoryId,Toast.LENGTH_LONG).show()
        BUYAuctionsPresenter(this)
        return super.onCreateView(R.layout.buyi_fragmentauction, inflater, container)
    }


    companion object {

        fun newInstance(
            categoryId: Int,
            categoryName: String,
            showMode: Int = AUCTION_MODE_ALL,
            searchQuery: String = ""
        ) =
            BUYAuctionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CAT_ID, categoryId)
                    putString(ARG_CAT_NAME, categoryName)
                    putInt(ARG_SHOW_MODE, showMode)
                    putString(ARG_SEARCH_QUERY, searchQuery)
                }
            }
    }
}
