package ru.home.collaborativeeducation.network

import io.reactivex.Observable
import ru.home.collaborativeeducation.model.*
import ru.home.collaborativeeducation.model.converter.CourseWithMetaConverter
import ru.home.collaborativeeducation.model.database.CourseSourceEntity
import ru.home.collaborativeeducation.network.model.ApiResponse
import ru.home.collaborativeeducation.network.model.CoursePayload
import ru.home.collaborativeeducation.storage.model.CourseWithMetadataAndCommentsEntity
import ru.home.collaborativeeducation.storage.model.ItemMetadataEntity
import ru.home.collaborativeeducation.storage.model.LikesEntity

class NetworkRepository(val api: Api) {

    fun getCategoryViewItems(): Observable<List<CategoryViewItem>> {
        return api.getAllCategories
            .flatMap { it ->
                val result = mutableListOf<CategoryViewItem>()
                if (it.code != 200) {
                    // no op
                } else {
                    for (item in it.status.payload) {
                        val obj = CategoryViewItem(item.uid, item.title)
                        result.add(obj)
                    }
                }
                Observable.just(result)
            }
    }

    fun saveCategoryViewItem(data: CategoryViewItem): Observable<CategoryViewItem> {
        return api.create(data)
            .flatMap {  it ->
                lateinit var response: CategoryViewItem
                if (it.code != 200) {
                    response = CategoryViewItem(-1, "")
                } else {
                    val item = it.status.payload[0]
                    response = CategoryViewItem(item.uid, item.title)
                }
                Observable.just(response)
            }
    }

    fun getCoursesForCategory(categoryUid: String): Observable<List<CourseViewItem>> {
        return api.getAllCoursesForCategory(categoryUid)
            .flatMap { it ->
                val result = mutableListOf<CourseViewItem>()
                if (it.code != 200) {
                    // no op
                } else {
                    for (item in it.status.payload) {
                        val obj = CourseViewItem(item.uid, item.title, item.categoryUid)
                        result.add(obj)