package app.platinummzadat.qa.views.root.profile.deposit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.R
import app.platinummzadat.qa.views.root.RootActivity
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.wlog


const val FAILED = 1500
const val SUCCESS = 2500

class PaymentActivity : MzActivity(), PaymentContract.View  {
    var amount = ""
    private  val DEVICE_TYPE = 1
    override fun onStart() {
        super.onStart()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        PaymentPresenter(this)
        amount = intent?.extras?.getString("amount") ?: ""
        presenter.doPayment(amount)
        textView7.onClick {
            val intent = Intent(this@PaymentActivity, RootActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@PaymentActivity.finish() // if the activity running has it's own context

        }
        tvHeading.onClick {
            super.onBackPressed()
        }
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
      /*  with(webview.settings){
            javaScriptEnabled = true
            domStorageEnabled = true
//            loadWithOverviewMode = true

        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                pbar.visibility = if (newProgress < 100) {
                    pbar.progress = newProgress
                    VISIBLE
                } else {
                    view?.let {
                        wlog("Completed url :${it.url}")
                        if (it.url.toLowerCase().contains("/home/success_confirm")) {
                            setResult(SUCCESS)
                            finish()
                        } else if (it.url.toLowerCase().contains("/home/failed_confirm")) {
                            setResult(FAILED)
                            finish()
                        }
                    }
                    GONE
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
        webview.loadUrl("${RemoteDataSource.PAYMENT_URL}?user_id=$currentUserId&amount=$amount&lang=$appLanguage&device_type=$DEVICE_TYPE")
        webview.viewTreeObserver.addOnScrollChangedListener {
            //            swipeRefresh.isEnabled = webview.scrollY == 0
        }*/
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

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
                            if (it.url.toLowerCase().contains("/home/success_confirm")) {
                                setResult(SUCCESS)
                                finish()
                            } else if (it.url.toLowerCase().contains("/home/failed_confirm")) {
                                setResult(FAILED)
                                finish()
                            }
                            }
                            View.GONE
                        }
                    }catch (e: Exception){

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
          //  Toast.makeText(applicationContext,"da"+data,Toast.LENGTH_LONG).show()
            webview.viewTreeObserver.addOnScrollChangedListener {
                //            swipeRefresh.isEnabled = webview.scrollY == 0
            }
        }
    }


    override lateinit var presenter:PaymentContract.Presenter

    override fun showNoInternet() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showApiError() {

    }

    override fun sessionTimeOut() {

    }
}
