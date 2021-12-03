package app.platinummzadat.qa.views.root.details.slider

import android.app.Activity
import android.os.Bundle
import app.platinummzadat.qa.R
import kotlinx.android.synthetic.main.activity_slider.*


class SliderActivity : Activity() {
    var urls: ArrayList<String> = ArrayList()
    var defaultPos: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)
        urls = intent?.extras?.getStringArrayList("urls")
            ?: ArrayList()
        defaultPos = intent?.extras?.getInt("pos") ?: 0
        vpPhotos.adapter = FullScreenSliderAdapter(urls)
        vpPhotos.currentItem = defaultPos
    }

}
