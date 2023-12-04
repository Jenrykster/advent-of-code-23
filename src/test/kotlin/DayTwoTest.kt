import com.jenryk.daytwo.DayTwo
import com.jenryk.daytwo.Game
import com.jenryk.daytwo.GameCubes
import com.jenryk.daytwo.GameSet
import kotlin.test.Test
import kotlin.test.assertEquals

class DayTwoTest {
    private val dayTwo = DayTwo()

    private val possibleGame = Game(
        id = 1,
        green = 4,
        blue = 9,
        red = 5,
        sets = listOf(GameSet(
            blue = 3,
            red = 4,
        ), GameSet(
            red = 1,
            green = 2,
            blue = 6
        ), GameSet(
            green = 2
        )
        )
    )

    val  impossibleGame = Game(
        id = 3,
        green = 26,
        blue = 11,
        red = 15,
        sets = listOf(
            GameSet(
            green = 8,
            blue = 6,
            red = 20
        ), GameSet(
            blue = 5,
            red = 4,
            green = 13
        ), GameSet(green = 5,
            red = 1
        )
        )
    )

    @Test
    fun `parses game entry correctly`() {
        val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"

        assertEquals(possibleGame, dayTwo.parseGameEntry(input))
    }

    @Test
    fun `validate possible game`() {
        assertEquals(true, dayTwo.isGamePossible(possibleGame))
    }

    @Test
    fun `validate impossible game`() {
        assertEquals(false, dayTwo.isGamePossible(impossibleGame))
    }

    @Test
    fun `sum of possible games`() {
        val input = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()
        val expected = 8

        assertEquals(expected, dayTwo.getSumOfPossibleGameIds(input))
    }

    @Test
    fun `the solution one is correct`() {
        val expected = 2105;

        assertEquals(expected, dayTwo.solvePuzzle())
    }

    @Test
    fun `gets minimum amount of cubes to make a game possible`() {
        val expected = GameCubes(
            red = 4,
            green = 2,
            blue = 6
        )
        val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val got =dayTwo.getMinimumColorSet(input)
        assertEquals(expected, got)
        assertEquals(48, got.power())
    }

    @Test
    fun `the solution two is correct`() {
        val expected = 72422;

        assertEquals(expected, dayTwo.solvePuzzlePartTwo())
    }
}