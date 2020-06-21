package ru.home.government.screens.votes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_laws.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.LawsAdapter
import ru.home.government.screens.laws.details.DetailsActivity
import ru.home.government.util.observeBy

class TrackerFragment: BaseFragment(), TrackerAdapter.ClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_votes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = TrackerAdapter(this)

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_divider)!!)
        list.addItemDecoration(dividerItemDecoration)

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)
        billsViewModel.getTrackedLaws()
            .observeBy(
                this,
                onNext = {
                        it ->
                    Log.d(App.TAG, "Data arrived: " + it.laws.size)
                    visibleProgress(false)
                    (list.adapter as TrackerAdapter).update(it.laws)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        billsViewModel.fetchTrackedLaws()
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item.number, item.url)
    }

}