package app.platinummzadat.qa.views.root.details.slider

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import app.platinummzadat.qa.R
import raj.nishin.wolfpack.WolfImageView

class FullScreenSliderAdapter(
    val dataSet: ArrayList<String>,
    val fnOnClickClose: (() -> Unit)? = null
) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = container.inflateLayout(R.layout.vp_item_photo)
        val ivPhoto = view.findViewById<WolfImageView>(R.id.ivPhoto)

        Picasso.get().load(dataSet[position]).into(ivPhoto, object : Callback {
         
            override fun onSuccess() {
                Handler().postDelayed({ ivPhoto.setZoom(1.0f) }, 50)
            }

            override fun onError(e: java.lang.Exception?) {

            }
        })
        container.addView(view, 0)
        return view
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj
    override fun getCount() = dataSet.size
    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) = container.removeView(obj as View)
    override fun getItemPosition(obj: Any) = POSITION_NONE

}

fun ViewGroup.inflateLayout(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}