package app.platinummzadat.qa.views.root.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.NavDrawerModel
import app.platinummzadat.qa.databinding.ItemNavDrawerBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


class DrawerAdapter(
    private val dataSet: ArrayList<NavDrawerModel> = ArrayList(),
    private val fnOnClickItem: ((NavDrawerModel) -> Unit)? = null
) : RecyclerView.Adapter<DrawerAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_nav_drawer,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(dataSet[position]) {
            holder.itemBinding.item = this
            holder.itemBinding.root.onClick {
                fnOnClickItem?.invoke(this@with)
            }

        }

    }

    inner class VH(val itemBinding: ItemNavDrawerBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}