package ru.home.government.di.fakes

import android.content.Context
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.home.government.model.Deputy
import ru.home.government.model.Law
import ru.home.government.network.IApi
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import ru.home.government.repository.pagination.BillsPagingSource
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class FakeRepository(context: Context, api: IApi, billsPagingSource: BillsPagingSource)
    : GovernmentRepository(context, api, billsPagingSource) {

    override fun getDeputiesV2(): Flow<Response<List<Deputy>>> {
        return flow {
            emit(prepareCorrectResponse())
        }
    }

    override fun getIntroducedLawsV2(): Flow<PagingData<Law>> {
        return super.getIntroducedLawsV2()
    }

    private fun prepareCorrectResponse(): Response.Data<List<Deputy>> {
        var payload = ArrayList<Deputy>()
        payload.add(Deputy("1", "Charles de Gaulle ", "Head", false, Collections.emptyList() ))
        payload.add(Deputy("2", "Joseph Stalin","Head", false, Collections.emptyList()))
        payload.add(Deputy("3", "Vladimir Putin", "Head", true, Collections.emptyList()))
        return Response.Data<List<Deputy>>(payload)
    }
}