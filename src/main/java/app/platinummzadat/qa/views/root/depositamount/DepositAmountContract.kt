package app.platinummzadat.qa.views.root.depositamount

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.AmountData
import app.platinummzadat.qa.data.models.AmountModel
import app.platinummzadat.qa.data.models.DepositModel
import app.platinummzadat.qa.data.models.RefundRequestRes

interface DepositAmountContract {
    interface View : BaseView<Presenter> {
        fun showData(data: DepositModel)
        fun showRefundData(data: RefundRequestRes)
        fun showAmountData(data: AmountData)
    }


    interface Presenter :BasePresenter {
        fun fetchDepositAmount()
        fun getAmount()
        fun getRefundRequest(imei:String)
    }
}