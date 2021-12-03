package app.platinummzadat.qa.views.root

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.KeyValueModel
import app.platinummzadat.qa.databinding.ItemSpecsBinding


class SpecificationsAdapter(
    private val dataSet: ArrayList<KeyValueModel> = ArrayList(),
    private val fnOnClickItem: ((KeyValueModel) -> Unit)? = null
) : RecyclerView.Adapter<SpecificationsAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_specs,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(dataSet[position]) {
            holder.itemBinding.item = this


        }

    }

    inner class VH(val itemBinding: ItemSpecsBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}