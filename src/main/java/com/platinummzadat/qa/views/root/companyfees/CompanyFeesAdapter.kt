package com.platinummzadat.qa.views.root.companyfees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.CompanyFeesData
import com.platinummzadat.qa.data.models.CompanyFeesDetailsModel
import com.platinummzadat.qa.databinding.ItemCompanyFeesBinding


class CompanyFeesAdapter(
        private val dataSet:  ArrayList<CompanyFeesData> = ArrayList(),
        private val fnOnClickItem: ((CompanyFeesDetailsModel) -> Unit)? = null
) : RecyclerView.Adapter<CompanyFeesAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_company_fees,
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

    inner class VH(val itemBinding: ItemCompanyFeesBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}