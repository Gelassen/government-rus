package ru.home.government.screens.laws

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_laws.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.details.DetailsActivity

class LawsFragment: BaseFragment(), LawsAdapter.ClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_laws, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = LawsAdapter()
        (list.adapter as LawsAdapter).listener = this

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_divider)!!)
        list.addItemDecoration(dividerItemDecoration)

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)
        lifecycleScope.launch {
            billsViewModel.getLaws().collectLatest {
                it ->
                Log.d("TAG", "Data arrived: " + it)
                (list.adapter as LawsAdapter).submitData(it)
            }
        }
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item.number, item.url)
    }
}