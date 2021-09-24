package ru.home.government.choir

import androidx.room.util.StringUtil
import ru.home.government.model.Law

/**
 * After several weeks of development and a year of maintenance there are few
 * issues has been discovered:
 * - some API, e.g. search by filter, does not work as expected
 * - API development team does not respond on emails
 * - in existing API a new bugs might appear, e.g. uncorrect URL in API response
 *
 * Within this constraints the most fit solution seems is fine grained fixes in
 * server response before immediate usage marked as choir. As we do not know
 * how long bug would remain and how much data and API methods it would affect.
 *
 * @date September 2021
 * */
class ServerDataTransformer {

    fun replaceLawUrlWithExplanatoryNote(law: Law): Law {
        law.url = replaceLawUrlWithExplanatoryNote(law.url)

        return law
    }

    fun replaceLawUrlWithExplanatoryNote(url: String): String {
        var result = url
        val uncorrectUrlRoot = "http://sozd.parlament.gov.ru"
        val correctUrlRoot = "http://sozd.duma.gov.ru"
        if (url.contains(uncorrectUrlRoot)) {
            result = url.replaceRange(0, uncorrectUrlRoot.length, correctUrlRoot)
        }

        return result
    }
}