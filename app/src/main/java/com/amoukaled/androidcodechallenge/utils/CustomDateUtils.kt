package com.amoukaled.androidcodechallenge.utils

import java.text.SimpleDateFormat
import java.util.*

object CustomDateUtils {

    /**
     * Gets the current time in milliseconds.
     */
    fun now(): Long {
        return Calendar.getInstance().timeInMillis
    }

    /**
     * Converts [timeInMillis] to dd/MM/yyy HH:mm date String.
     */
    fun convertTimestampToFormattedDate(timeInMillis: Long): String? {
        val dateFormat = SimpleDateFormat("dd/MM/yyy HH:mm", Locale.US)
        return dateFormat.format(Date(timeInMillis))
    }

    /**
     * Converts the [millis] to a time string representation.
     */
    fun getTimeFromTimestamp(millis: Long): String {
        val hours = ((millis / (1000 * 60 * 60)) % 24)
        val minutes = ((millis / (1000 * 60)) % 60)
        val seconds = ((millis / 1000) % 60)

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}