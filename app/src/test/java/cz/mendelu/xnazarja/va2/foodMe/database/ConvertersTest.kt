package cz.mendelu.xnazarja.va2.foodMe.database

import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.Point
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.Sources
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setUp() {
        converters = Converters()
    }

    @Test
    fun `from Point, correct`() {
        val result = converters.fromPoint(Point(lat = 16.2548, lon = 28.284568))
        assertEquals(result, "16.2548|28.284568")
    }

    @Test
    fun `to Point, correct`() {
        val result = converters.toPoint("16.2548|28.284568")
        assertEquals(result, Point(lat = 16.2548, lon = 28.284568))
    }

    @Test
    fun `from Sources, correct`() {
        val result = converters.fromSources(Sources(listOf("abc", "def", "ghi"),"12sd"))
        assertEquals(result, "abc,def,ghi|12sd")
    }

    @Test
    fun `to Sources, correct`(){
        val result = converters.toSources("abc,def,ghi|12sd")
        assertEquals(result, Sources(listOf("abc", "def", "ghi"),"12sd"))
    }

}