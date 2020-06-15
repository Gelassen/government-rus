package ru.home.government.screens.laws

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

class LawsFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_laws, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)
        billsViewModel.boundResource
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
        billsViewModel.fetch()
    }
}