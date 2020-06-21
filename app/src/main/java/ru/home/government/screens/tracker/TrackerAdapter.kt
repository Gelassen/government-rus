package ru.home.government.screens.tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.R
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider
import ru.home.government.screens.laws.LawsAdapter
import java.util.*

class TrackerAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<LawsAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemClick(item: Law)
    }

    private val model: MutableList<Law> = ArrayList()
    fun update(model: List<Law>?) {
        this.model.clear()
        this.model.addAll(model!!)
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
        val provider = LawDataProvider()
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