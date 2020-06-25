package ru.home.government.providers

import ru.home.government.model.*
import java.text.SimpleDateFormat
import java.util.*

class LawDataProvider {

    companion object {

        private const val SOURCE_DATE_FORMAT = "yyyy-MM-dd"

        private const val INTRODUCED_DATE_FORMAT = "dd MMM yyyy"

        private const val SHORT_DATE_FORMAT = "dd MMM"
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

    fun provideLastEventDate(lastEvent: LastEvent?): String {
        val textFormat = "Обновление: %s"
        if (lastEvent == null) {
            return String.format(textFormat, "Нет данных")
        } else {
            val date: String? = lastEvent.date
            return String.format("Обновление: %s",
                if (date.isNullOrEmpty()) "Нет данных" else introducedDateFormat.format(originDateFormat.parse(date)))
        }

    }

    fun provideLastEventData(lastEvent: LastEvent?): String {
        if (lastEvent == null) {
            return "Нет данных"
        } else {
            return provideLastEventData(lastEvent.stage, lastEvent.phase)
        }
    }

    fun provideLastEventData(stage: Stage?, phase: Phase?): String {
        val noDataPlaceholder = "Нет данных"
        if (stage == null && phase == null) {
            return noDataPlaceholder
        } else if (stage != null && phase == null) {
            return String.format("%s", stage.name)
        } else if (stage == null && phase != null) {
            return String.format("%s", phase.name)
        } else {
            return String.format("%s \n\n%s", stage!!.name, phase!!.name)
        }
    }

    fun provideFormattedResolution(resolution: String?): String {
        return String.format("Решение: %s", if (resolution.isNullOrEmpty()) "Нет данных" else resolution)
    }

    fun provideResponsibleCommittee(committees: Committees?): String {
        return if (committees == null || committees.responsible == null) "Нет данных" else committees.responsible.name
    }

    fun provideFractions(deputy: Deputy): String {
        if (deputy.factions == null) return ""
        val builder = StringBuilder()
        for (item in deputy.factions!!) {
            builder.append(item.name)
            builder.append("\n")
        }
        return builder.toString()
    }

}