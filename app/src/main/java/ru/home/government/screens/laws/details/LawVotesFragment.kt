package ru.home.government.screens.laws.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_law_votes.*
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.VotesResponse
import ru.home.government.providers.VotesDataProvider
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.util.newObserveBy


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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_law_votes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)
        billsViewModel.subscribeOnVotesByLaw()
            .newObserveBy(
                this,
                onNext = {
                        it ->
                    Log.d("TAG", "Data arrived: " + it)
                    onVotesData(it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        val lawNumber = arguments!!.get(EXTRA_LAW_CODE).toString()
        billsViewModel.fetchVotesByLaw(lawNumber)
    }

    private fun onVotesData(votesResponse: VotesResponse?) {
        if (votesResponse == null || votesResponse.votes == null || votesResponse.votes.size == 0) {
            votesNoData.visibility = View.VISIBLE
            votesContainer.visibility = View.GONE
            voteDetails.visibility = View.GONE
        } else {
            val dataProvider = VotesDataProvider()
            val vote = votesResponse.votes.get(0)
            voteFor.text = dataProvider.providesVotesFor(resources, vote.forCount)
            voteAgainst.text = dataProvider.providesVotesAgainst(resources, vote.againstCount)
            voteAbstain.text = dataProvider.providesVotesAbstain(resources, vote.abstainCount)

            voteDetails.visibility = View.VISIBLE
            voteDetails.setOnClickListener { it ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(String.format("http://vote.duma.gov.ru/vote/%s", vote.id))
                startActivity(intent)
            }
        }
    }

}