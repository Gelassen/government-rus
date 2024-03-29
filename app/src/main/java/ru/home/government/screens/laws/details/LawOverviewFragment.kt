package ru.home.government.screens.laws.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.AppApplication
import ru.home.government.databinding.FragmentLawOverviewBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.dto.Deputy
import ru.home.government.model.dto.GovResponse
import ru.home.government.model.dto.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.providers.LawDataProvider
import ru.home.government.providers.VotesDataProvider
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

    private lateinit var binding: FragmentLawOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLawOverviewBinding.inflate(layoutInflater, container, false)
        binding.lawDataProvider = LawDataProvider(container!!.context.applicationContext)
        binding.votesProvider = VotesDataProvider()
        binding.lawData = Law()
        binding.deputyData = Deputy()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        binding.resources = resources // requires fragment attached to instantiated activity
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        
        val billsViewModel: BillsViewModel by viewModels { viewModelFactory }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                billsViewModel.uiState.collectLatest {
                    if (it.errors.isNotEmpty()) {
                        showError(
                            view = binding.lawLastEvent,
                            text = it.errors.first(),
                            onDismiss = { billsViewModel.removeShownError() }
                        )
                    }
                    onNewData(it.billsByNumber)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val lawNumber = requireArguments().get(LawOverviewFragment.EXTRA_LAW_CODE).toString()
                billsViewModel.getLawByNumber(lawNumber)
            }
        }
    }

    private fun onNewData(response: GovResponse) {
        if (response.laws == null || response.laws.size == 0)  return

        binding.lawData = response.laws.get(0)
        if ((binding.lawData as Law).isDeputiesAvailable) processDeputies(binding.lawData as Law)
        binding.executePendingBindings()
    }

    private fun processDeputies(item: Law) {
        if (!item.isDeputiesAvailable) return

        binding.deputyData = item.subject.deputies.get(0)
        binding.voteDeputiesCounter.setOnClickListener { _ ->
            DeputiesOnLawActivity.launch(
                requireActivity() as AppCompatActivity,
                ArrayList(item.subject.deputies.subList(1, item.subject.deputies.size))
            )
        }
    }
}