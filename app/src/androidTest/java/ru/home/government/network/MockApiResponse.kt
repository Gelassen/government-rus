package ru.home.government.network

import android.content.Context
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import ru.home.government.model.GovResponse
import ru.home.government.model.Vote
import ru.home.government.model.VotesResponse
import java.util.*

class MockApiResponse(context: Context) {

    val deputiesApi: DeputiesApi
    val billsApi: BillsApi
    val billSpecificApi: BillSpecificApi
    val billSpecificVotesApi : BillSpecificVotesApi
    val billsSearchApi: BillsSearchApi

    init {
        deputiesApi = DeputiesApi(context)
        billsApi = BillsApi(context)
        billSpecificApi = BillSpecificApi(context)
        billSpecificVotesApi = BillSpecificVotesApi(context)
        billsSearchApi = BillsSearchApi(context)
    }

    // TODO remove context from methods parameters when context is passed over class constructor

    open class BaseApi(context: Context) {
        protected var res: MockResponse = getDefault(context)

        fun getResponse(): MockResponse {
            return res
        }

        /**
         * Child class must to override this method to must have their default stub responses
         * */
        protected open fun getDefault(context: Context) : MockResponse {
            return MockResponse()
                .setResponseCode(404)
                .setBody("{ msg: `Did you forget to override `BaseApi.getDefault()` }")
        }

        protected fun getMessage(context: Context, jsonName: String): String {
            return context.assets.open(jsonName).bufferedReader().use { it.readText() }
        }
    }

    class DeputiesApi(context: Context) : BaseApi(context) {

        override fun getDefault(context: Context): MockResponse {
            return MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_deputies.json"))
        }

        fun setOkDeputiesResponse(context: Context) {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_deputies.json"))
        }

        fun setServerErrorResponse() {
            res = MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        }

        fun setOkWithNoDeputiesResponse(appContext: Context) {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(appContext, "mocks/mock_api_deputies_empty.json"))
        }
    }

    class BillsApi(val context: Context) : BaseApi(context) {

        private var secondPageResponse: MockResponse = getDefault(context)

        fun get2ndPageResponse(): MockResponse {
            return secondPageResponse;
        }

        override fun getDefault(context: Context): MockResponse {
            return MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_laws_page_1.json"))
        }

        fun getBillsCollection(context: Context): GovResponse {
            val json = getMessage(context, "mocks/mock_api_laws_page_1.json")
            return Gson().fromJson(json, GovResponse::class.java)
        }

        fun setOkBillsResponse(context: Context) {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_laws_page_1.json"))
        }

        fun set2ndPageOkBillsResponse(context: Context) {
            secondPageResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_laws_page_2.json"))
        }

        fun setServerErrorResponse() {
            res = MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        }

        fun setOkWithNoBillsResponse(context: Context) {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_laws_empty.json"))
        }

    }

    class BillSpecificApi(val context: Context): BaseApi(context) {

        private val billsApi: BillsApi = BillsApi(context)

        override fun getDefault(context: Context): MockResponse {
            val govResponse = BillsApi(context).getBillsCollection(context)
            govResponse.laws = Collections.singletonList(govResponse.laws.get(0))
            val json = Gson().toJson(govResponse)
            return MockResponse()
                .setResponseCode(200)
                .setBody(json)
        }

        fun setBill149922_8Response() {
            val govResponse = billsApi.getBillsCollection(context)
            govResponse.laws = Collections.singletonList(govResponse.laws.get(0))
            val json = Gson().toJson(govResponse)
            res = MockResponse()
                .setResponseCode(200)
                .setBody(json)
        }

        fun setBillWithDeputiesResponse() {
            val govResponse = billsApi.getBillsCollection(context)
            govResponse.laws = Collections.singletonList(govResponse.laws.get(3))
            val json = Gson().toJson(govResponse)
            res = MockResponse()
                .setResponseCode(200)
                .setBody(json)
        }

        fun setBill149922_8ServerErrorResponse() {
            res = MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        }
        // TODO set empty response
    }

    class BillSpecificVotesApi(val context: Context): BaseApi(context) {

        override fun getDefault(context: Context): MockResponse {
            return MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_law_votes_empty.json"))
        }

        fun setBillEmptyVotesResponse() {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_law_votes_empty.json"))
        }

        fun setBillVotesFullResponse() {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_law_votes_full.json"))
        }

        fun setBillVotesFull2ndResponse() {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_law_votes_full_2nd.json"))
        }

        fun setBillVotesServerErrorResponse() {
            res = MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        }

        fun getVotesFull(): Vote {
            val json = getMessage(context, "mocks/mock_api_law_votes_full.json")
            val votesResponse = Gson().fromJson(json, VotesResponse::class.java)
            return votesResponse.votes.first()
        }

        fun getVotesFull2nd(): Vote {
            val json = getMessage(context, "mocks/mock_api_law_votes_full_2nd.json")
            val votesResponse = Gson().fromJson(json, VotesResponse::class.java)
            return votesResponse.votes.first()
        }

        fun getVotesEmpty(): Vote {
            val json = getMessage(context, "mocks/mock_api_law_votes_empty.json")
            val votesResponse = Gson().fromJson(json, VotesResponse::class.java)
            return votesResponse.votes.first()
        }
    }

    class BillsSearchApi(val context: Context): BaseApi(context) {

        private var secondPageResponse: MockResponse = getDefault(context)

        fun get2ndPageResponse(): MockResponse {
            return secondPageResponse
        }

        override fun getDefault(context: Context): MockResponse {
            return MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_search_positive_page_1.json"))
        }

        fun setBillsSearchPositive1stPageResponse() {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_search_positive_page_1.json"))
        }

        fun setBillsSearchPositive2stPageResponse() {
            secondPageResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_search_positive_page_2.json"))
        }

        fun setBillsSearchEmptyResponse() {
            res = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_search_empty.json"))
        }

        fun setServerErrorResponse() {
            res = MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        }

    }
}