package app.platinummzadat.qa.views.root.depositamount

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.DepositDetailsModel
import app.platinummzadat.qa.databinding.ItemDepositAmountBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


class DepositAmountAdapter(
    private val dataSet: ArrayList<DepositDetailsModel> = ArrayList(),
    private val fnOnClickItem: ((DepositDetailsModel) -> Unit)? = null
) : RecyclerView.Adapter<DepositAmountAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_deposit_amount,
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

    inner class VH(val itemBinding: ItemDepositAmountBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}