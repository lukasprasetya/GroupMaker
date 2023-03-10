package com.binar.groupmaker.ui.history.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.groupmaker.data.room.entity.ResultData
import com.binar.groupmaker.databinding.ItemDetailHistoryBinding


class HistoryDetailAdapter(private val itemClick: (ResultData) -> Unit) :
    RecyclerView.Adapter<HistoryDetailAdapter.HistoryDetailViewHolder>() {

    var playersArrList: ArrayList<String>? = null
    private var items: MutableList<ResultData> = mutableListOf()

    fun setItems(items: List<ResultData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDetailViewHolder {
        val binding = ItemDetailHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryDetailViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HistoryDetailViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class HistoryDetailViewHolder(private val binding: ItemDetailHistoryBinding, val itemClick: (ResultData) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ResultData) {
            binding.tvIdMember.text = "TEAM-" + item.teamResult
            binding.tvNameMember.text = item.memberResult


            with(item) {
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}