package ru.home.government.screens.laws.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineDispatcher
import ru.home.government.R
import ru.home.government.databinding.ViewItemLawOverviewBinding
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider

class LawsAdapter(
    diffCallback: DiffUtil.ItemCallback<Law> = LawComparator(),
    mainDispatcher: CoroutineDispatcher,
    workerDispatcher: CoroutineDispatcher
) : PagingDataAdapter<Law, LawsAdapter.ViewHolder>(
    diffCallback,
    mainDispatcher,
    workerDispatcher
) {

    constructor(mainDispatcher: CoroutineDispatcher, workerDispatcher: CoroutineDispatcher)
            : this(LawComparator(), mainDispatcher, workerDispatcher) {
    }

    lateinit var listener: ClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ViewItemLawOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lawProvider = LawDataProvider(parent.context.applicationContext)
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
    class ViewHolder(internal val binding: ViewItemLawOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val favIcon: ImageView

        init {
            favIcon = itemView.findViewById(R.id.lawFavSign)
        }
    }

    interface ClickListener {
        fun onItemClick(item: Law)
    }

    class LawComparator: DiffUtil.ItemCallback<Law>() {
        override fun areItemsTheSame(oldItem: Law, newItem: Law): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Law, newItem: Law): Boolean =
            oldItem == newItem

    }

}