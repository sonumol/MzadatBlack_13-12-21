package app.platinummzadat.qa.views.root.faq

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.FAQData
import app.platinummzadat.qa.databinding.ItemCompanyFeesBinding
import app.platinummzadat.qa.databinding.ItemFaqBinding
import kotlinx.android.synthetic.main.item_faq.view.*


class FAQAdapter(
        private val dataSet:  ArrayList<FAQData> = ArrayList()
) : RecyclerView.Adapter<FAQAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_faq,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(dataSet[position]) {
            holder.itemBinding.item = this
            val pos=position+1
            holder.itemView.tv_question.setText(pos.toString()+") "+dataSet[position].title)
        }

    }

    inner class VH(val itemBinding: ItemFaqBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}