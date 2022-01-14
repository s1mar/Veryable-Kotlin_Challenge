package com.veryable.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.veryable.android.data.AccountModel
import com.veryable.android.databinding.ListItemBinding

class PayoutAdapter(
    private var listOfAccount: ArrayList<AccountModel> =ArrayList(),
    var callback: AccountAdapterCallback
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class AdapterViewHolder(private var itemViews: ListItemBinding):RecyclerView.ViewHolder(itemViews.root){
        fun onBind(data:AccountModel){
            itemViews.tvSavingAccount.text = data.account_name
            itemViews.tvBank.text = data.account_type
            itemViews.tvBankType.text = data.desc
            if(data.account_type=="bank"){
                itemViews.imgAccType.setImageResource(R.drawable.account)
            }else{
                itemViews.imgAccType.setImageResource(R.drawable.card)
            }
            itemViews.root.setOnClickListener {
                callback.onAccountClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is AdapterViewHolder){
                holder.onBind(listOfAccount[position])
            }
    }

    override fun getItemCount(): Int {
        return listOfAccount.size ?: 0
    }
}