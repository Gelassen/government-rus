package ru.home.government.screens.tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.databinding.ViewItemLawOverviewBinding
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider
import ru.home.government.screens.laws.main.LawsAdapter
import java.util.*

class TrackerAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<LawsAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemClick(item: Law)
    }

    private val model: MutableList<Law> = ArrayList()

    lateinit var provider: LawDataProvider

    fun reset() {
        this.model.clear()
        notifyDataSetChanged()
    }

    fun update(model: List<Law>?) {
        /*this.model.clear()*/ // TODO revert back after figure out issue of smooth run several suspend functions in the same coroutine scope
        this.model.addAll(model!!)
        this.model.sortByDescending { it -> provider.dateAsLong(it.lastEvent.date) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LawsAdapter.ViewHolder {
        val binding = ViewItemLawOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        provider = LawDataProvider(parent.context.applicationContext)
        binding.lawProvider = provider
        return LawsAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LawsAdapter.ViewHolder,
        position: Int
    ) {
        val item = model[position]
        holder.binding.lawData = item
        holder.binding.lawFavSign.drawable.level = 1
        holder.binding.lawFavSign.visibility = View.VISIBLE
        holder.itemView.setOnClickListener { listener.onItemClick(item) }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return model.size
    }

}