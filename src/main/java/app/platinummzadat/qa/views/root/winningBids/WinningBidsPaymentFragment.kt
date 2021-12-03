package com.platinummzadat.qa.views.root.companyfees


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import app.platinummzadat.qa.MzFragment
import app.platinummzadat.qa.R

import app.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_winning_bid_payment.pbar
import kotlinx.android.synthetic.main.fragment_winning_bid_payment.webview

import raj.nishin.wolfpack.gone
import raj.nishin.wolfpack.visibility
import raj.nishin.wolfpack.visible
import raj.nishin.wolfpack.wlog


class WinningBidsPaymentFragment : MzFragment(),WinningBidsPaymentContract.View {
    lateinit var adapter: WinningBidsAdapter
    var bidId:Int = 0
    override fun showData(data: String) {
        if(data!=null){
            with(webview.settings){
                javaScriptEnabled = true
                domStorageEnabled = true
//            loadWithOverviewMode = true

            }
            webview.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    try{
                        pbar.visibility = if (newProgress < 100) {
                            pbar.progress = newProgress
                            View.VISIBLE
                        } else {
                            view?.let {
                                wlog("Completed url :${it.url}")
//                            if (it.url.toLowerCase().contains("/home/success_confirm")) {
//                                activity.setResult(SUCCESS)
//                                finish()
//                            } else if (it.url.toLowerCase().contains("/home/failed_confirm")) {
//                                activity.setResult(FAILED)
//                                finish()
//                            }
                            }
                            View.GONE
                        }
                    }catch (e:Exception){

                    }

                    super.onProgressChanged(view, newProgress)
                }
            }
            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webview.loadUrl(data)
            webview.viewTreeObserver.addOnScrollChangedListener {
                //            swipeRefresh.isEnabled = webview.scrollY == 0
            }
        }
    }

    override lateinit var presenter: WinningBidsPaymentContract.Presenter
    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        pbar?.visibility(visible)
    }

    override fun hideLoading() {
        pbar?.visibility(gone)
    }

    override fun showEmptyData() {
       // noData?.visibility(visible)
    }
    override fun showApiError() {
        fragmentListener?.onError {
            presenter.winningBidsPayment(bidId)
        }
    }

    override fun sessionTimeOut() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.winningBidsPayment(bidId)
    }
    companion object {
        fun newInstance(id: String) = WinningBidsPaymentFragment().apply {
            arguments = Bundle().apply {
                putString("id", id)
                bidId=id.toInt()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        WinningBidsPaymentPresenter(this)
        return inflater.inflate(R.layout.fragment_winning_bid_payment, container, false)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(R.string.title_activity_payment))
    }
}
