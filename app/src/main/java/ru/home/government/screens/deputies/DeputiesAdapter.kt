package ru.home.government.screens.deputies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.providers.LawDataProvider
import java.util.*

class DeputiesAdapter :
    RecyclerView.Adapter<DeputiesAdapter.ViewHolder>() {

    private val model: MutableList<Deputy> = ArrayList()

    fun update(model: List<Deputy>?) {
        this.model.clear()
        this.model.addAll(model!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_deputy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = model[position]
        holder.name.text = item.name
        holder.position.setText(item.position)
        holder.fraction.text =  LawDataProvider().provideFractions(item)
    }

    override fun getItemCount(): Int {
        return model.size
    }

    private fun getFractions(deputy: Deputy): String {
        if (deputy.factions == null) return ""
        val builder = StringBuilder()
        for (item in deputy.factions!!) {
            builder.append(item.name)
            builder.append("\n")
        }
        return builder.toString()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val position: TextView
        val fraction: TextView

        init {
            name = view.findViewById(R.id.name)
            position = view.findViewById(R.id.position)
            fraction = view.findViewById(R.id.fraction)
        }
    }
}