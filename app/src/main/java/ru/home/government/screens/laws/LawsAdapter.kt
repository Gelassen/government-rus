package ru.home.government.screens.laws

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.R
import ru.home.government.model.Law
import java.text.SimpleDateFormat
import java.util.*

class LawsAdapter :
    PagingDataAdapter<Law, LawsAdapter.ViewHolder>(LAW_COMPARATOR) {

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
        val item = getItem(position)/*model.get(position)*/
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(item!!.lastEvent.date)
        holder.date.text = SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
        holder.code.text = item!!.number
        holder.title.text = item!!.name
        holder.resolution.text = if (item.lastEvent.solution == null) "" else item.lastEvent.solution as String

        holder.itemView.setOnClickListener { it ->
                if (this.listener == null) return@setOnClickListener

                listener.onItemClick(item)
            }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val date: TextView
        val code: TextView
        val title: TextView
        val resolution: TextView

        init {
            date = itemView.findViewById(R.id.lawDate)
            code = itemView.findViewById(R.id.lawCode)
            title = itemView.findViewById(R.id.lawTitle)
            resolution = itemView.findViewById(R.id.lawResolution)
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