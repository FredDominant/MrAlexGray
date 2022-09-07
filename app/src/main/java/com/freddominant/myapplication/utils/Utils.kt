package com.freddominant.myapplication.utils

import com.freddominant.myapplication.R
import org.threeten.bp.DateTimeException
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*
import kotlin.random.Random

object Utils {
    fun getRandomImageResource(): Int {
        val images = arrayListOf(
            R.drawable.github_three,
            R.drawable.github_eight,
            R.drawable.github_nine,
            R.drawable.github_ten,
            R.drawable.github_eleven,
        )
        val randomPosition = Random.nextInt(0, images.size)
        return images[randomPosition]
    }

    fun formatDate(date: String?): String {
        return if (date.isNullOrEmpty()) {
            ""
        } else {
            return try {
                val zonedDate = ZonedDateTime.parse(date).toLocalDate()
                "${zonedDate.dayOfWeek.name.capitalize()}, " +
                        "${zonedDate.month.name.capitalize()} " +
                        "${zonedDate.year}"
            } catch (error: DateTimeException) {
                val localDate = LocalDate.parse(date)
                "${localDate.dayOfWeek.name.capitalize()}, " +
                        "${localDate.month.name.capitalize()} " +
                        "${localDate.year}"
            }
        }
    }

    private fun String.capitalize(): String {
        return this.lowercase(Locale.getDefault())
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
    }
}