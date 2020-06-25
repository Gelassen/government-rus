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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_law_overview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        val provider = LawDataProvider()
        holder.date.text = provider.provideFormattedShortDate(item!!.lastEvent.date)
        holder.code.text = item!!.number
        holder.title.text = item!!.name
        holder.resolution.text = LawDataProvider().provideFormattedResolution(item.lastEvent.solution as String?)

        holder.itemView.setOnClickListener { it ->
                if (this.listener == null) return@setOnClickListener

                listener.onItemClick(item)
            }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
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