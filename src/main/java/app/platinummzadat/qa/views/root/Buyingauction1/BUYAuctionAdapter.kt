package app.platinummzadat.qa.views.root.Buyingauction1

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.AuctionItemModel
import app.platinummzadat.qa.databinding.ItemAuctionGridBinding
import app.platinummzadat.qa.databinding.ItemAuctionLinearBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_details.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.getTimeStamp

const val AUCTION_ADAPTER_LIST = 1
const val AUCTION_ADAPTER_GRID = 2

class BUYAuctionAdapter(

    val context:Context,
    val dataSet: ArrayList<AuctionItemModel> = ArrayList(),
    private val type: Int = AUCTION_ADAPTER_LIST,
    private val fnOnClickItem: ((AuctionItemModel) -> Unit)? = null,
    private val fnOnClickBidNow: ((AuctionItemModel) -> Unit)? = null
) : RecyclerView.Adapter<BUYAuctionAdapter.VH>() {
    override fun getItemId(position: Int): Long {
        return dataSet[position].price.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun refresh(newData: ArrayList<AuctionItemModel>) {
        for (i in 0 until dataSet.size) {
            val newItem = newData.find { it.id == dataSet[i].id }
            if (null != newItem) {
                dataSet.removeAt(i)
                dataSet.add(i, newItem)
            }
        }
        notifyDataSetChanged()
    }

    fun insertItems(items: ArrayList<AuctionItemModel>) {
        val startPos = dataSet.size
        dataSet.addAll(items)
        notifyItemRangeInserted(startPos, items.size)
    }

    fun resetItems(items: ArrayList<AuctionItemModel>) {
        dataSet.removeAll(dataSet)
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                if (AUCTION_ADAPTER_LIST == type) R.layout.item_auction_linear else R.layout.item_auction_grid,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.bindData(dataSet[position])

    }


    inner class VH(val itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var timer: CountDownTimer? = null
        fun bindData(data: AuctionItemModel) {
            val requestOptions = RequestOptions().error(R.drawable.bg_dark)
            //val imageView = findViewById(R.id.iv_click_me) as ImageView

            if (AUCTION_ADAPTER_LIST == type) {

                with(itemBinding as ItemAuctionLinearBinding) {
                    root.onClick {
                        fnOnClickItem?.invoke(data)
                    }
                    Glide.with(context)
                        .load(data.imgUrl)
                        .apply(requestOptions)
                        .into(ivItem)

//                    tvTimeLeft.text = data.button_name
                    btnBidNow.text =  data.button_name
                    btnBidNow.isEnabled=false
                    btnBidNow.isClickable=false
                    l1.visibility=View.GONE
                    btnBidNow.onClick {
                        fnOnClickBidNow?.invoke(data)
                    }
//
                    item = data

                }
            } else {

                with(itemBinding as ItemAuctionGridBinding) {
                    root.onClick {
                        fnOnClickItem?.invoke(data)
                    }
                    Glide.with(context)
                        .load(data.imgUrl)
                        .apply(requestOptions)
                        .into(ivItem)


//                    tvTimeLeft.text = data.button_name
                    btnBidNow.text =  data.button_name
                    btnBidNow.isEnabled=false
                    btnBidNow.isClickable=false
                    l1.visibility=View.GONE
                    btnBidNow.onClick {
                        fnOnClickBidNow?.invoke(data)
                    }
//
                    item = data
                }
            }
        }

    }
}