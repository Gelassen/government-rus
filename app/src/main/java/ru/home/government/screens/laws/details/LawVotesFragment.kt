package ru.home.government.screens.laws.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.android.synthetic.main.fragment_law_votes.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentLawVotesBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.VotesResponse
import ru.home.government.providers.VotesDataProvider
import ru.home.government.repository.Response
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import java.lang.StringBuilder
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

        val billsViewModel: BillsViewModel by viewModels() { viewModelFactory }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                billsViewModel.votesResponse.collect { it ->
                    processResponse(it)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val lawNumber = requireArguments().get(EXTRA_LAW_CODE).toString()
                billsViewModel.getVotesByLawV2(lawNumber)
            }
        }
    }

    private fun processResponse(it: Response<VotesResponse>) {
        when (it) {
            is Response.Data -> {
                if (!it.data.isDataAvailable) {
                    binding.votesResponse = VotesResponse()
                    binding.executePendingBindings()
                } else {
                    val votesResponse = it.data
                    binding.votesResponse = votesResponse
                    binding.executePendingBindings()
                    voteDetails.setOnClickListener { it ->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(
                                String.format(
                                        resources.getString(R.string.url_vote),
                                        votesResponse.votes.get(0).id
                                )
                        )
                        startActivity(intent)
                    }
                }


            }
            is Response.Error.Message -> {
                val error = StringBuilder()
                    .append(getString(R.string.unknown_error) + ". " + it.msg)
                    .append(". ")
                    .append(it.msg)
                    .toString()
                showError(error)
            }
            is Response.Error.Exception -> {
                Log.e(App.TAG, getString(R.string.unknown_error), it.error)
                showError(getString(R.string.unknown_error))
            }
        }
    }

}