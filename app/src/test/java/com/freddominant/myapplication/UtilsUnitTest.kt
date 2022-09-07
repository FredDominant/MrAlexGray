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

    @Test
    fun `formatDate method should format date properly`() {
        var date = "2012-10-06T16:37:39Z"
        var formattedDate = Utils.formatDate(date)
        Assert.assertTrue(formattedDate.isNotEmpty())
        Assert.assertEquals(formattedDate, "Saturday, October 2012")

        date = "2013-01-12T13:39:30Z"
        formattedDate = Utils.formatDate(date)
        Assert.assertTrue(formattedDate.isNotEmpty())
        Assert.assertEquals(formattedDate, "Saturday, January 2013")

        formattedDate = Utils.formatDate(null)
        Assert.assertTrue(formattedDate.isEmpty())

        date = ""
        formattedDate = Utils.formatDate(date)
        Assert.assertTrue(formattedDate.isEmpty())
    }
}