package ru.home.government.screens.laws.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.home.government.App
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
import ru.home.government.repository.Response
import java.lang.StringBuilder
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
        
        val billsViewModel: BillsViewModel by viewModels() { viewModelFactory }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                billsViewModel.law.collect { it ->
                    processResponse(it)
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

    private fun processResponse(it: Response<GovResponse>) {
        when (it) {
            is Response.Data -> {
                onNewData(it.data)
            }
            is Response.Error.Message -> {
                val error = StringBuilder()
                    .append(getString(R.string.unknown_error) + ". " + it.msg)
                    .append(". ")
                    .append(it.msg)
                    .toString()
                showError(binding.lawLastEvent, error)
            }
            is Response.Error.Exception -> {
                Log.e(App.TAG, getString(R.string.unknown_error), it.error)
                showError(binding.lawLastEvent, getString(R.string.unknown_error))
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
        binding.voteDeputiesCounter.setOnClickListener { it ->
            DeputiesOnLawActivity.launch(
                requireActivity() as AppCompatActivity,
                ArrayList(item.subject.deputies.subList(1, item.subject.deputies.size))
            )
        }
    }
}