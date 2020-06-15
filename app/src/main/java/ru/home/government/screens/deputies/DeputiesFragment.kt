package ru.home.government.screens.deputies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
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

        val viewModel = ViewModelProviders.of(this).get(DeputiesViewModel::class.java)
        viewModel.init(activity!!.application as AppApplication)
        viewModel.deputiesBoundResource
            .asLiveData()
            .observeBy(
                this,
                onNext = {
                    // TODO complete me
                        it -> Log.d("TAG", "Data arrived: " + it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        viewModel.fetchDeputies()
    }

}