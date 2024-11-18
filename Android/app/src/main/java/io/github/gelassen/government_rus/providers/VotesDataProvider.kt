package io.github.gelassen.government_rus.providers

import android.content.res.Resources
import io.github.gelassen.government_rus.R
import io.github.gelassen.government_rus.model.dto.Deputy
import io.github.gelassen.government_rus.model.dto.Law

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