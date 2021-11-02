package ru.home.government.screens.laws.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.home.government.R
import ru.home.government.databinding.ViewItemLawOverviewBinding
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider
import ru.home.government.screens.laws.main.LawsAdapter

class FilteredLawsAdapter :
    PagingDataAdapter<Law, LawsAdapter.ViewHolder>(
        LAW_COMPARATOR
    ) {

    lateinit var listener: LawsAdapter.ClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LawsAdapter.ViewHolder {
        val binding = ViewItemLawOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lawProvider = LawDataProvider()
        return LawsAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LawsAdapter.ViewHolder,
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

    companion object {
        private val LAW_COMPARATOR = object : DiffUtil.ItemCallback<Law>() {
            override fun areItemsTheSame(oldItem: Law, newItem: Law): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Law, newItem: Law): Boolean =
                oldItem == newItem
        }
    }
}