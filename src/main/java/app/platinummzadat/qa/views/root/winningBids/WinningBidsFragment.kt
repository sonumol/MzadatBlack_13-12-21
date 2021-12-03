package com.platinummzadat.qa.views.root.companyfees


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R

import app.platinummzadat.qa.data.models.WinningBidsDetails
import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_winning_bids.*
import raj.nishin.wolfpack.*


class WinningBidsFragment : MzFragment(),WinningBidsContract.View {

    lateinit var adapter: WinningBidsAdapter
    override lateinit var presenter: WinningBidsContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = prepareAdapter()
        presenter.winningBids()
    }
    override fun showData(data:ArrayList<WinningBidsDetails>) {
        if(data!=null){
            rvFees?.layoutManager(LinearLayoutManager(activity))
            rvFees?.itemOffset(8)
            adapter = prepareAdapter(adapter.dataSet)
            adapter.insertItems(data)
            rvFees?.adapter = adapter
            llData?.visibility(visible)
            if(data.isEmpty()){
                noData?.visibility(visible)
                llData?.visibility(gone)
            }
        }else{
            noData?.visibility(visible)
            llData?.visibility(gone)
        }

    }
    private fun prepareAdapter(dataSet: ArrayList<WinningBidsDetails> = ArrayList() ) =
            WinningBidsAdapter(dataSet, fnOnClickItem = {
                fragmentListener?.onSelectWinningBidItem(it)
            }).apply { setHasStableIds(true) }


    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        noData?.visibility(gone)
        llData?.visibility(gone)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showEmptyData() {
        noData?.visibility(visible)
    }
    override fun showApiError() {
        fragmentListener?.onError {
            presenter.winningBids()
        }
        showEmptyData()
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        WinningBidsPresenter(this)
        return inflater.inflate(R.layout.fragment_winning_bids, container, false)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.my_winning_bids))
    }
}
