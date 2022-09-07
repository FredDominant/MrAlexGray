package com.freddominant.myapplication.utils

import com.freddominant.myapplication.R
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
}