package ru.home.government.screens.laws.details

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentLawVotesBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.dto.VotesResponse
import ru.home.government.providers.VotesDataProvider
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import javax.inject.Inject


class LawVotesFragment: BaseFragment() {

    companion object {

        private const val EXTRA_LAW_CODE = "EXTRA_LAW_CODE"

        fun instance(code: String) : LawVotesFragment {
            val fragment = LawVotesFragment()
            val args = Bundle()
            args.putString(EXTRA_LAW_CODE, code)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: FragmentLawVotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLawVotesBinding.inflate(inflater, container, false)
        binding.dataProvider = VotesDataProvider()
        binding.votesResponse = VotesResponse()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        binding.resources = resources
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        val billsViewModel: BillsViewModel by viewModels { viewModelFactory }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                billsViewModel.uiState.collectLatest { it ->
                    if (it.errors.isNotEmpty()) {
                        showError(
                            view = binding.voteDetails,
                            text = it.errors.first(),
                            onDismiss = { billsViewModel.removeShownError() }
                        )
                    }
                    processResponse(it.votesByLaw)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val lawNumber = requireArguments().get(EXTRA_LAW_CODE).toString()
                billsViewModel.getVotesByLaw(lawNumber)
            }
        }
    }

    private fun processResponse(votesByLaw: VotesResponse) {
        if (!votesByLaw.isDataAvailable) {
            binding.votesResponse = VotesResponse()
            binding.executePendingBindings()
        } else {
            binding.votesResponse = votesByLaw
            binding.executePendingBindings()
            binding.voteDetails.setOnClickListener { _ ->
                val url = String.format(
                    resources.getString(R.string.url_vote),
                    votesByLaw.votes.first().id
                )
                openWebPage(Uri.parse(url))
            }
        }
    }

    private fun openWebPage(uri: Uri) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(context!!.getColor(R.color.colorPrimaryDark))
                    .build()
            )
            .build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }

}