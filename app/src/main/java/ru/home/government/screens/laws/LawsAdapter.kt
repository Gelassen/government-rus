package ru.home.government.screens.laws

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.App
import ru.home.government.R
import ru.home.government.model.Law
import ru.home.government.screens.laws.details.DetailsActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class LawsAdapter :
    RecyclerView.Adapter<LawsAdapter.ViewHolder>() {
    private val model: MutableList<Law> = ArrayList()

    interface ClickListener {
        fun onItemClick()
    }

    lateinit var listener: ClickListener

    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }

    fun update(model: List<Law>?) {
        Log.d(App.TAG, "Update laws model: " + model.isNullOrEmpty())
        this.model.clear()
        if (model != null) {
            this.model.addAll(model!!)
        }
        notifyDataSetChanged()
    }

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
        val item = model.get(position)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(item.lastEvent.date)
        holder.date.text = SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
        holder.code.text = item.number
        holder.title.text = item.name
        holder.resolution.text = if (item.lastEvent.solution == null) "" else item.lastEvent.solution as String

        holder.itemView.setOnClickListener { it ->
                if (this.listener == null) return@setOnClickListener

                listener.onItemClick()
            }
    }

    override fun getItemCount(): Int {
        return model.size
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
}