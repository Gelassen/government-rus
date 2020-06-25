package ru.home.government.providers

import android.content.res.Resources
import ru.home.government.R
import ru.home.government.model.Deputy

class VotesDataProvider {

    fun providesVotesFor(res: Resources, votes: Int): String {
        return String.format(res.getString(R.string.votes_support), votes)
    }

    fun providesVotesAgainst(res: Resources, votes: Int): String {
        return String.format(res.getString(R.string.votes_against), votes)
    }

    fun providesVotesAbstain(res: Resources, votes: Int): String {
        return String.format(res.getString(R.string.votes_abstain), votes)
    }

    fun providesVotedDeputiesCounter(res: Resources, deputies: List<Deputy>): String {
        if (deputies.size > 1) {
            return String.format(res.getString(R.string.votes_deputies_counter), deputies.size - 1)
        }
        return ""
    }
}