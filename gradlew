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
                    }
                }
                Observable.just(result)
            }
    }

    fun saveCourseViewItem(data: CourseViewItem): Observable<CourseViewItem> {
        val payload = CoursePayload(data.uid, data.title, data.categoryUid)
        return api.create(payload)
            .flatMap {  it ->
                lateinit var response: CourseViewItem
                if (it.code != 200) {
                    response = CourseViewItem(-1, "", -1)
                } else {
                    val item = it.status.payload[0]
                    response = CourseViewItem(item.uid, item.title, item.categoryUid)
                }
                Observable.just(response)
            }
    }

    fun getSourcesForCourse(categoryUid: String, courseUid: String): Observable<List<CourseWithMetadataAndComments>> {
        return api.getAllSourcesForCourse(categoryUid, courseUid)
            .flatMap { it ->
                val result = mutableListOf<CourseWithMetadataAndCommentsEntity>()
                if (it.code != 200) {
                    // no op
                } else {
                    for (item in it.status.payload) {
                        val obj = CourseWithMetadataAndCommentsEntity(
                            CourseSourceEntity(item.uid, item.title, item.source, item.courseUid, item.users),
                            ItemMetadataEntity(LikesEntity()) // TODO add likes support and process them (replace stub)
                        )
                        result.add(obj)
                    }
                }
                Observable.just(result)
            }
            .flatMap { it ->
                Observable.just(CourseWithMetaConverter().convert(it))
            }
    }

    fun getSourcesWithMetaForCourse(categoryUid: String, courseUid: String): Observable<List<CourseWithMetadataAndComments>> {
        return api.getAllSourcesWithMetaForCourse(categoryUid, courseUid)
            .flatMap { it ->
                lateinit var response: List<CourseWithMetadataAndComments>
                if (it.code != 200) {
                    response = mutableListOf()
                } else {
                    for (item in it.status.payload) {
                        item.metadata.likes.courseUid = item.source.uid
                    }
                    response = it.status.payload
                }
                Observable.just(response)
            }
    }

    fun saveSourceForCourse(source: CourseSourceItem): Observable<CourseWithMetadataAndComments> {
        return api.create(source)
            .flatMap { it ->
                lateinit var response: CourseWithMetadataAndComments
                if (it.code != 200) {

                } else {

                }
                Observable.just(response)
                val item = it.status.payload[0]
//                val obj = CourseWithMetadataAndCommentsEntity(
//                    CourseSourceEntity(item.uid, item.title, item.source, item.courseUid, item.users),
//                    ItemMe