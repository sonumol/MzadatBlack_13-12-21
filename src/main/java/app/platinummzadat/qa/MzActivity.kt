package app.platinummzadat.qa

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import java.util.*


open class MzActivity : AppCompatActivity() {
    override fun onPause() {
        super.onPause()

    }
    override fun onResume() {
        super.onResume()
        val locale = Locale(appLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }
}
