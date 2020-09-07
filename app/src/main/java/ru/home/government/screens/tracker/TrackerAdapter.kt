package ru.home.government.screens.tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.R
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

    private val provider = LawDataProvider()

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_law_overview, parent, false)
        return LawsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LawsAdapter.ViewHolder,
        position: Int
    ) {
        val item = model[position]
        holder.date.text = provider.provideFormattedShortDate(item.lastEvent.date)
        holder.code.text = item.number
        holder.title.text = item.name
        holder.resolution.text = provider.provideFormattedResolution(
            item.lastEvent.solution as String?
        )
        holder.title.text = item.name
        holder.favIcon.drawable.level = 1
        holder.favIcon.visibility = View.VISIBLE
        holder.itemView.setOnClickListener { listener.onItemClick(item) }
    }

    override fun getItemCount(): Int {
        return model.size
    }

}