package ru.home.government.providers

import android.content.res.Resources
import ru.home.government.R
import ru.home.government.model.dto.Deputy
import ru.home.government.model.dto.Law

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

    fun providesVotedDeputiesCounterSafe(res: Resources, law: Law): String {
        if (law.isDeputiesAvailable) {
            return providesVotedDeputiesCounter(res, law.subject.deputies)
        } else {
            return ""
        }
    }

    fun providesVotedDeputiesCounter(res: Resources, deputies: List<Deputy>): String {
        if (deputies.size > 1) {
            return String.format(res.getString(R.string.votes_deputies_counter), deputies.size - 1)
        }
        return ""
    }
}