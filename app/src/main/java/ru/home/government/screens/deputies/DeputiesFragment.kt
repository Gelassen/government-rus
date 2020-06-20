package ru.home.government.screens.deputies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.screens.BaseFragment
import ru.home.government.util.observeBy


class DeputiesFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_deputies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = view.findViewById<View>(R.id.progressView)

        val list = view.findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.adapter = DeputiesAdapter()

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_divider)!!)
        list.addItemDecoration(dividerItemDecoration)

        val viewModel = ViewModelProviders.of(this).get(DeputiesViewModel::class.java)
        viewModel.init(activity!!.application as AppApplication)
        viewModel.subscribeOnDeputies()
            .observeBy(
                this,
                onNext = {
                        it ->
//                    Log.d("TAG", "Data arrived: " + it)
                    Log.d("DEPUTIES", "Data arrived: " + it.size)
                    val activeDeputies = it.filter { it -> it.isCurrent!! }
                    visibleProgress(false)
                    (list.adapter as DeputiesAdapter).update(activeDeputies)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        viewModel.fetchDeputies()
    }

}