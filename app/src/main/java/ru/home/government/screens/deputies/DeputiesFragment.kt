package ru.home.government.screens.deputies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_deputies.*
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

        val list = view.findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = DeputiesAdapter()

        val viewModel = ViewModelProviders.of(this).get(DeputiesViewModel::class.java)
        viewModel.init(activity!!.application as AppApplication)
        viewModel.subscribeOnDeputies()
            .observeBy(
                this,
                onNext = {
                        it ->
                    Log.d("TAG", "Data arrived: " + it)
                    (list.adapter as DeputiesAdapter).update(it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        viewModel.fetchDeputies()
    }

}