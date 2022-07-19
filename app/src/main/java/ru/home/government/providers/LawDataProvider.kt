package ru.home.government.providers

import android.content.Context
import ru.home.government.R
import ru.home.government.model.dto.*
import java.text.SimpleDateFormat
import java.util.*

class LawDataProvider(val context: Context) {

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

    fun dateAsLong(date: String): Long {
        return originDateFormat.parse(date)?.time ?: 0L
    }

    fun provideFormattedShortDate(date: String): String {
        return originDateFormat.parse(date)?.let { shortDateFormat.format(it) }
            ?: context.getString(R.string.placeholder_no_data)
    }

    fun provideFormattedIntroducedDate(date: String?): String {
        return String.format("${context.getString(R.string.title_introduced)} %s",
            if (date.isNullOrEmpty()) {
                context.getString(R.string.placeholder_no_data)
            } else {
                originDateFormat.parse(date)?.let { introducedDateFormat.format(it) }
                    ?: context.getString(R.string.placeholder_no_data)
            }
        )
    }

    fun provideLastEventDate(lastEvent: LastEvent?): String {
        val textFormat = "${context.getString(R.string.title_update)} %s"
        if (lastEvent == null) {
            return String.format(textFormat, context.getString(R.string.placeholder_no_data))
        } else {
            val date: String? = lastEvent.date
            return String.format("${context.getString(R.string.title_update)} %s",
                if (date.isNullOrEmpty()) {
                    context.getString(R.string.placeholder_no_data)
                } else {
                    originDateFormat.parse(date)?.let { introducedDateFormat.format(it) }
                        ?: context.getString(R.string.placeholder_no_data)
                })
        }

    }

    fun provideLastEventData(lastEvent: LastEvent?): String {
        if (lastEvent == null) {
            return context.getString(R.string.placeholder_no_data)
        } else {
            return provideLastEventData(lastEvent.stage, lastEvent.phase)
        }
    }

    fun provideLastEventData(stage: Stage?, phase: Phase?): String {
        val noDataPlaceholder = context.getString(R.string.placeholder_no_data)
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
        return String.format("${context.getString(R.string.title_resolution)} %s",
            if (resolution.isNullOrEmpty()) context.getString(R.string.law_no_data) else resolution)
    }

    fun provideResponsibleCommittee(committees: Committees?): String {
        return if (committees == null || committees.responsible == null) {
            context.getString(R.string.placeholder_no_data)
        } else {
            committees.responsible.name
        }
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