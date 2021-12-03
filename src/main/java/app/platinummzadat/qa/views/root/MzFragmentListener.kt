package app.platinummzadat.qa.views.root

import app.platinummzadat.qa.data.models.AuctionItemModel
import app.platinummzadat.qa.data.models.DashboardItemModel
import app.platinummzadat.qa.data.models.WinningBidsDetails
import app.platinummzadat.qa.views.root.depositamount.DepositAmountFragment
import app.platinummzadat.qa.views.root.drawer.MzNav
import app.platinummzadat.qa.views.root.drawer.MzNav1

/**
 * Created by WOLF
 * at 12:55 on Tuesday 02 April 2019
 */
interface MzFragmentListener {
    fun onError(callback: () -> Unit)
    fun onErrorWithMessage(message: String, actionText: String?, callback: () -> Unit)
    fun onErrorWithAutoHideMessage(message: String, actionText: String?, callback: () -> Unit)
    fun refreshProfileInfo()
    fun setTitle(title: String)
    fun onSelectDashboardItem(item: DashboardItemModel)
    fun onSelectDepositAmount(item: DepositAmountFragment)
    fun onSelectAuctionItem(item: AuctionItemModel)
    fun onSelectAuctionItem1(item: AuctionItemModel)
    fun onSelectWinningBidItem(item: WinningBidsDetails)
    fun onSelectNavItem(item: MzNav)
    fun onapprove(approve:Int)
    fun userinternational(international:Int)

}