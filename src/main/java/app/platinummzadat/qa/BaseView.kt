package app.platinummzadat.qa

import raj.nishin.wolfpack.wlog

interface BaseView<T> {
    var presenter: T
    fun showNoInternet()
    fun showLoading()
    fun hideLoading()
    fun showApiError()
    fun hideApiError(){}
    fun showEmptyData() {}
    fun sessionTimeOut()
    fun testMessage(message: String) {
        wlog(message)
    }
}