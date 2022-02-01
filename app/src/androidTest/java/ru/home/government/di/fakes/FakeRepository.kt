package ru.home.government.di.fakes

import android.content.Context
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.model.VotesResponse
import ru.home.government.network.IApi
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import ru.home.government.repository.pagination.BillsPagingSource
import ru.home.government.stubs.Stubs
import java.util.*
import kotlin.collections.ArrayList

class FakeRepository(context: Context, api: IApi, billsPagingSource: BillsPagingSource)
    : GovernmentRepository(context, api, billsPagingSource) {

    override fun getDeputies(): Flow<Response<List<Deputy>>> {
        return flow {
            emit(prepareCorrectResponse())
        }
    }

    override fun getIntroducedLaws(): Flow<PagingData<Law>> {
        return super.getIntroducedLaws()
    }

    private lateinit var lawByNumberResponse: Response<GovResponse>

    fun setPositiveLawByNumberResponse() {
        this.lawByNumberResponse = Response.Data(
            Stubs.ApiResponse.getPositiveSingleLawServerResponse()
        )
    }

    fun setPositiveAnotherLawByNumberResponse() {
        this.lawByNumberResponse = Response.Data(
                Stubs.ApiResponse.getAnotherPositiveSingleLawResponse()
        )
    }

    fun setPositiveButIncompleteLawByNumberResponse() {
        this.lawByNumberResponse = Response.Data(
            Stubs.ApiResponse.getPositiveButIncompleteSingleLawServerResponse()
        )
    }

    fun setPositiveWithDeputiesLawByNumberResponse() {
        this.lawByNumberResponse = Response.Data(
            Stubs.ApiResponse.getPositiveSingleLawWithDeputiesServerResponse()
        )
    }

    override fun getLawByNumber(billNumber: String): Flow<Response<GovResponse>> {
        return flow {
            emit(lawByNumberResponse)
        }
    }

    private lateinit var votesResponse: Response<VotesResponse>

    fun setPositiveWithPayloadVotesByLawResponse() {
        this.votesResponse = Response.Data(
            Stubs.ApiResponse.getPositiveWithPayloadVotesBylawResponse()
        )
    }

    fun setPositiveWithPayloadAnotherVotesByLawResponse() {
        this.votesResponse = Response.Data(
                Stubs.ApiResponse.getAnotherPositiveWithPayloadVotesByLawResponse()
        )
    }

    override fun getVotesByLaw(number: String): Flow<Response<VotesResponse>> {
        return flow {
            emit(votesResponse)
        }
    }

    private fun prepareCorrectResponse(): Response.Data<List<Deputy>> {
        var payload = ArrayList<Deputy>()
        payload.add(Deputy("1", "Charles de Gaulle ", "Head", false, Collections.emptyList() ))
        payload.add(Deputy("2", "Joseph Stalin","Head", false, Collections.emptyList()))
        payload.add(Deputy("3", "Vladimir Putin", "Head", true, Collections.emptyList()))
        return Response.Data<List<Deputy>>(payload)
    }

}