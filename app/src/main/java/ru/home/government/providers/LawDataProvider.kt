package ru.home.government.providers

import ru.home.government.model.Committees
import java.text.SimpleDateFormat
import java.util.*

class LawDataProvider {

    companion object {

        private const val SOURCE_DATE_FORMAT = "yyyy-MM-dd"

        private const val INTRODUCED_DATE_FORMAT = "dd MMM yyyy"

        private const val SHORT_DATE_FORMAT = "MMM dd"
    }

    val originDateFormat: SimpleDateFormat
    val introducedDateFormat: SimpleDateFormat
    val shortDateFormat: SimpleDateFormat

    init {
        originDateFormat = SimpleDateFormat(SOURCE_DATE_FORMAT, Locale.getDefault())
        introducedDateFormat = SimpleDateFormat(INTRODUCED_DATE_FORMAT, Locale.getDefault())
        shortDateFormat = SimpleDateFormat(SHORT_DATE_FORMAT, Locale.getDefault())
    }

    fun provideFormattedShortDate(date: String?): String {
        return shortDateFormat.format(originDateFormat.parse(date))
    }

    fun provideFormattedIntroducedDate(date: String?): String {
        return String.format("Внесён: %s",
            if (date.isNullOrEmpty()) "Нет данных" else introducedDateFormat.format(originDateFormat.parse(date))
        )
    }

    fun provideLastEventDate(date: String?): String {
        return String.format("Последнее событие: %s",
            if (date.isNullOrEmpty()) "Нет данных" else introducedDateFormat.format(originDateFormat.parse(date)))
    }

    fun provideFormattedResolution(resolution: String?): String {
        return String.format("Решение: %s", if (resolution.isNullOrEmpty()) "Нет данных" else resolution)
    }

    fun provideResponsibleCommittee(committees: Committees?): String {
        return if (committees == null || committees.responsible == null) "Нет данных" else committees.responsible.name
    }

}