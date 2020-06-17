package ru.home.government.screens.laws.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_law_overview.*
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.GovResponse
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.providers.LawDataProvider
import ru.home.government.util.observeBy

class LawOverviewFragment: BaseFragment() {

    companion object {

        private const val EXTRA_LAW_CODE = "EXTRA_LAW_CODE"

        fun instance(code: String) : LawOverviewFragment {
            val fragment = LawOverviewFragment()
            val args = Bundle()
            args.putString(EXTRA_LAW_CODE, code)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_law_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)
        billsViewModel.subscribeOnLawsByNumber(arguments!!.get(EXTRA_LAW_CODE).toString())
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
        billsViewModel.fetchLawByNumber()
    }

    private fun onNewData(response: GovResponse) {
        val dataProvider = LawDataProvider()

        var item = response.laws.get(0)
        lawTitle.text = item.name

        lawIntroducedDate.text = dataProvider.provideFormattedIntroducedDate(item.introductionDate)
        lawResolution.text = dataProvider.provideFormattedResolution(item.lastEvent.solution as String?)
        lawResponsibleCommittee.text = dataProvider.provideResponsibleCommittee(item.committees.responsible as String?)
    }
}