import com.jenryk.dayone.DayOne
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.expect


internal class DayOneTest {
    private val dayOne: DayOne = DayOne()

    @Test
    fun `gets correct line numbers`() {
        val input = "pqr3stu8vwx"
        val expected: List<Char> = listOf('3', '8');
        assertContentEquals(expected, dayOne.getLineNumbers(input))
    }

    @Test
    fun `calibration values are calculated correctly`() {
        val input = listOf('1', '2', '3');
        val expected = 13
        assertEquals(expected, dayOne.getCalibrationValue(input))
    }

    @Test
    fun `spelled numbers are parsed correctly`() {
        val input = "oneight2three"
        val expected = listOf('1', '8', '2', '3')

        assertEquals(expected, dayOne.getSpelledNumbers(input))
    }

    @Test
    fun `sum of calibration values`() {
        val input = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
        """.trimIndent()
        val expected = 142

        assertEquals(expected, dayOne.getTotalCalibration(input))
    }

    @Test
    fun `puzzle is correct`() {
        val expected = 54078
        val got = dayOne.solvePuzzle()

        assertEquals(expected, got);
    }
}