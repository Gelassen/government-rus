package ru.home.government.screens.laws.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.R
import ru.home.government.databinding.ViewItemDeputyBinding
import ru.home.government.databinding.ViewItemDeputyBindingImpl
import ru.home.government.databinding.ViewItemLawOverviewBinding
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider

class LawsAdapter :
    PagingDataAdapter<Law, LawsAdapter.ViewHolder>(
        LAW_COMPARATOR
    ) {

    interface ClickListener {
        fun onItemClick(item: Law)
    }

    lateinit var listener: ClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ViewItemLawOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lawProvider = LawDataProvider()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.lawData = item
        holder.itemView.setOnClickListener {
            if (this.listener == null) return@setOnClickListener
            listener.onItemClick(item!!)
        }
        holder.binding.executePendingBindings()
    }

    // TODO remove all fields when all screen will be refactored to binding approach
    class ViewHolder(internal val binding: ViewItemLawOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val date: TextView
        val code: TextView
        val title: TextView
        val resolution: TextView
        val favIcon: ImageView

        init {
            date = itemView.findViewById(R.id.lawDate)
            code = itemView.findViewById(R.id.lawCode)
            title = itemView.findViewById(R.id.lawTitle)
            resolution = itemView.findViewById(R.id.lawResolution)
            favIcon = itemView.findViewById(R.id.lawFavSign)
        }
    }

    companion object {
        private val LAW_COMPARATOR = object : DiffUtil.ItemCallback<Law>() {
            override fun areItemsTheSame(oldItem: Law, newItem: Law): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Law, newItem: Law): Boolean =
                oldItem == newItem
        }
    }
}