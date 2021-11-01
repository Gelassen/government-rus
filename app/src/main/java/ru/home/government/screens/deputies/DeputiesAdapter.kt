package ru.home.government.screens.deputies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.R
import ru.home.government.databinding.ViewItemDeputyBinding
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
        val binding = ViewItemDeputyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.dataProvider = LawDataProvider()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = model[position]
        holder.binding.deputy = item
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return model.size
    }

    inner class ViewHolder(internal val binding: ViewItemDeputyBinding)
        : RecyclerView.ViewHolder(binding.root)

}