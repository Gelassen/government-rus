package ru.home.government

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.home.government.model.Deputy
import ru.home.government.network.IApi
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import ru.home.government.repository.pagination.BillsPagingSource
import java.util.*
import kotlin.collections.ArrayList

class FakeRepository(context: Context, api: IApi, billsPagingSource: BillsPagingSource)
    : GovernmentRepository(context, api, billsPagingSource) {

    override fun getDeputies(): Flow<Response<List<Deputy>>> {
        return flow {
            emit(prepareCorrectResponse())
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