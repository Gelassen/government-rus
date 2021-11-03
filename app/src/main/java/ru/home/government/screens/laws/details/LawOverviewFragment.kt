package ru.home.government.screens.laws.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_law_overview.*
import kotlinx.android.synthetic.main.view_item_deputy.view.*
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentLawOverviewBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.providers.LawDataProvider
import ru.home.government.providers.VotesDataProvider
import ru.home.government.util.newObserveBy
import java.util.ArrayList
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: FragmentLawOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLawOverviewBinding.inflate(layoutInflater, container, false)
        binding.lawDataProvider = LawDataProvider()
        binding.votesProvider = VotesDataProvider()
        binding.lawData = Law()
        binding.deputyData = Deputy()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        binding.resources = resources // requires fragment attached to instantiated activity
        binding.executePendingBindings()
        
        val billsViewModel: BillsViewModel by viewModels() { viewModelFactory }
        billsViewModel.subscribeOnLawsByNumber()
            .newObserveBy(
                this,
                onNext = {
                        it ->
                    Log.d("TAG", "Data arrived: " + it)
                    onNewData(it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        billsViewModel.fetchLawByNumber(arguments!!.get(EXTRA_LAW_CODE).toString())
    }

    private fun onNewData(response: GovResponse) {
        if (response.laws == null || response.laws.size == 0) {
            showError(getString(R.string.no_laws_error))
        } else {
            binding.lawData = response.laws.get(0)
            processDeputies(binding.lawData as Law)
        }
    }

    private fun processDeputies(item: Law) {
        if (item.isDeputiesAvailable) {
            binding.deputyData = item.subject.deputies.get(0)
            voteDeputiesCounter.setOnClickListener { it ->
                DeputiesOnLawActivity.launch(
                    requireActivity() as AppCompatActivity,
                    ArrayList(item.subject.deputies.subList(1, item.subject.deputies.size))
                )
            }
        }
        binding.executePendingBindings()
    }
}