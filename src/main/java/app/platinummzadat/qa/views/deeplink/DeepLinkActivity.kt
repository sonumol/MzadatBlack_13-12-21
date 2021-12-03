package app.platinummzadat.qa.views.deeplink

import android.os.Bundle
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.views.root.RootActivity
import org.jetbrains.anko.startActivity
import raj.nishin.wolfpack.wlog

class DeepLinkActivity : MzActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action: String? = intent?.action
        val data: String = intent?.data?.toString() ?: ""
        wlog("DEEP LINK ACTION $action")
        wlog("DEEP LINK DATA $data")
        val itemId = try {
            data.substring(data.lastIndexOf("/")+1).toInt()
        } catch (e: Exception) {
            -1
        }
        val item = try {
            data.substring(data.lastIndexOf("/home")+1).toString()
        } catch (e: Exception) {
            -1
        }
        startActivity<RootActivity>(
            "type" to "redirect",
            "type1" to item,
            "item_id" to itemId
        )
        finish()
    }

}
