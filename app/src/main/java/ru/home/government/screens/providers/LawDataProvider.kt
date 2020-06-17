package ru.home.government.screens.providers

import java.text.SimpleDateFormat
import java.util.*

class LawDataProvider {

    companion object {

        private const val SOURCE_DATE_FORMAT = "yyyy-MM-dd"

        private const val INTRODUCED_DATE_FORMAT = "dd MMM yyyy"
    }

    val originDateFormat: SimpleDateFormat
    val introducedDateFormat: SimpleDateFormat

    init {
        originDateFormat = SimpleDateFormat(SOURCE_DATE_FORMAT, Locale.getDefault())
        introducedDateFormat = SimpleDateFormat(INTRODUCED_DATE_FORMAT, Locale.getDefault())
    }

    fun provideFormattedIntroducedDate(date: String): String {
        return String.format("Внесён: %s",
            introducedDateFormat.format(originDateFormat.parse(date)))
    }

    fun provideFormattedResolution(resolution: String?): String {
        return String.format("Решение: %s", if (resolution.isNullOrEmpty()) "Нет данных" else resolution)
    }

    fun provideResponsibleCommittee(responsible: String?): String {
        return if (responsible.isNullOrEmpty()) "Нет данных" else responsible
    }

}