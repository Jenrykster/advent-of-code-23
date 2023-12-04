package com.jenryk.daytwo

import com.jenryk.utils.readFromInputs

enum class CubeColor(val color: String) {
    RED("red"),
    GREEN("green"),
    BLUE("blue")
}
data class GameCubes (var red: Int = 12, var green: Int = 13, var blue: Int = 14) {
    fun power(): Int = this.red * this.green * this.blue
}

data class GameSet (val red: Int = 0, val green: Int = 0, val blue: Int = 0)
data class Game(val id: Int,val sets: List<GameSet> = listOf(),  val red: Int, val green: Int, val blue: Int)

fun main() {
    val dayTwo = DayTwo()
    println("The answer for part one is: ${dayTwo.solvePuzzle()}")
    println("The answer for part two is: ${dayTwo.solvePuzzlePartTwo()}")
}

class DayTwo(private val cubes: GameCubes = GameCubes()) {
    fun solvePuzzle(): Int {
        val input = readFromInputs("daytwo.txt")

        return getSumOfPossibleGameIds(input)
    }

    fun solvePuzzlePartTwo(): Int {
        val input = readFromInputs("daytwo.txt")

        return getSumOfMinimumSetsPower(input)
    }

    private fun parseGameId(line: String): Int {
        val match =  """Game (\d+):""".toRegex().find(line)

        return match?.groupValues?.get(1)?.toInt() ?: 0
    }

    private fun parseGameSets(line: String): List<GameSet> {
        val setsStrings = line.split(":")[1].split(";");

        return setsStrings.map {
            GameSet(
                red = getEntriesOfColor(it, CubeColor.RED),
                blue = getEntriesOfColor(it, CubeColor.BLUE),
                green = getEntriesOfColor(it, CubeColor.GREEN)
            )
        }
    }

    fun getMinimumColorSet(line: String): GameCubes {
        val sets = parseGameSets(line);
        return sets.fold(GameCubes(
            red = 0,
            green = 0,
            blue = 0
        )) {
            acc, gameSet ->
            if(gameSet.blue > acc.blue ) {
                acc.blue = gameSet.blue
            }
            if(gameSet.red > acc.red) {
                acc.red = gameSet.red
            }
            if(gameSet.green > acc.green) {
                acc.green = gameSet.green
            }
            acc
        }
    }

    private fun getEntriesOfColor(line: String, cubeColor: CubeColor): Int {
        val matches = """(\d+) ${cubeColor.color}""".toRegex().findAll(line)

       return  matches.fold(0) {
            acc, matchResult ->  acc + matchResult.groupValues[1].toInt()
        }
    }

    private fun isSetPossible(set: GameSet): Boolean {
        val isRedPossible = set.red <= this.cubes.red
        val isGreenPossible = set.green <= this.cubes.green
        val isBluePossible = set.blue <= this.cubes.blue

        return isRedPossible && isGreenPossible && isBluePossible
    }

    fun isGamePossible(game: Game): Boolean {
        return game.sets.all {
            isSetPossible(it)
        }
    }

    fun parseGameEntry(line: String): Game {
        val gameId = parseGameId(line)
        val gameSets = parseGameSets(line)

        return Game(
            id = gameId,
            sets = gameSets,
            red = getEntriesOfColor(line, CubeColor.RED),
            blue = getEntriesOfColor(line, CubeColor.BLUE),
            green = getEntriesOfColor(line, CubeColor.GREEN)
        )
    }

    fun getSumOfPossibleGameIds(input: String): Int {
        return input.split("\n").fold(0) {
                acc, line ->
            val game = parseGameEntry(line)
            if(isGamePossible(game)) {
                acc + game.id
            }else{
                acc
            }
        }
    }

    fun getSumOfMinimumSetsPower(input: String): Int {
        return input.split("\n").fold(0) {
                acc, line ->
            acc + getMinimumColorSet(line).power()
        }
    }
}