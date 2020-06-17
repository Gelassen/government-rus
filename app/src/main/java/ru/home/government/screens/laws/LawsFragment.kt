package ru.home.government.screens.laws

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_laws.*
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.details.DetailsActivity
import ru.home.government.util.observeBy

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

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)
        billsViewModel.subscribeOnLaws()
            .observeBy(
                this,
                onNext = {
                        it ->
                            Log.d("TAG", "Data arrived: " + it)
                            onNewData(it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        billsViewModel.fetchLaws()
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item.number, item.url)
    }

    private fun onNewData(data: GovResponse) {
        (list.adapter as LawsAdapter).update(data.laws)
    }

}