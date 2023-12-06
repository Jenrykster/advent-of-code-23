import com.jenryk.daythree.DayThree
import com.jenryk.daythree.Map
import kotlin.test.Test
import kotlin.test.assertEquals

class DayThreeTest {
    private val daythree = DayThree()
    private val map = Map(
        """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent()
    )
    // Changed the implementation and now I'm too behind to be able to write more tests for this one :(
    @Test
    fun `part one solution is correct`() {
        val expected = 521601
        assertEquals(expected, daythree.solvePuzzle())
    }

    @Test
    fun `gear ratio is calculated correctly`() {
        val expected = 467835
        assertEquals(expected, map.getGearRatioSum())
    }

    @Test
    fun `part two solution is correct`() {
        val expected = 80694070
        assertEquals(expected, daythree.solvePuzzlePartTwo())
    }
}