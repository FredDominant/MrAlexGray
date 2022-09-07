package com.freddominant.myapplication

import com.freddominant.myapplication.utils.Utils
import org.junit.Assert
import org.junit.Test

class UtilsUnitTest {
    @Test
    fun `getRandomImageResource() should return correct data`() {
        val randomImage = Utils.getRandomImageResource()
        val randomImages = arrayListOf(
            R.drawable.github_three,
            R.drawable.github_eight,
            R.drawable.github_nine,
            R.drawable.github_ten,
            R.drawable.github_eleven,
        )
        Assert.assertTrue(randomImage != 0)
        Assert.assertTrue(randomImage != -1)
        Assert.assertTrue(randomImages.contains(randomImage))
        Assert.assertTrue(randomImages.contains(Utils.getRandomImageResource()))
    }
}