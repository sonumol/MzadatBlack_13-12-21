package com.platinummzadat.qa.views.root.companyfees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.WinningBidsDetails

import app.platinummzadat.qa.databinding.ItemWinningLinearBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


class WinningBidsAdapter(
     val dataSet: ArrayList<WinningBidsDetails> = ArrayList(),
    private val fnOnClickItem: ((WinningBidsDetails) -> Unit)? = null
) : RecyclerView.Adapter<WinningBidsAdapter.VH>() {


    override fun getItemId(position: Int): Long {
        return dataSet[position].id.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_winning_linear,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    fun insertItems(items: ArrayList<WinningBidsDetails>) {
        val startPos = dataSet.size
        dataSet.addAll(items)
        notifyItemRangeInserted(startPos, items.size)
    }



    inner class VH(val itemBinding: ItemWinningLinearBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bindData(data: WinningBidsDetails) {
            itemBinding.btnBidNow.onClick {
                fnOnClickItem?.invoke(data)
            }
            itemBinding.item=data
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(dataSet[position])
    }


}