package com.jenryk.daythree

import com.jenryk.utils.readFromInputs
import kotlin.math.floor

fun main() {
    val dayThree = DayThree()

    println("The solution for part one is ${dayThree.solvePuzzle()}")
    println("The solution for part two is ${dayThree.solvePuzzlePartTwo()}")
}

class DayThree {
    private val input =  readFromInputs("daythree.txt")
    private val map  = Map(input)
    fun solvePuzzle(): Int {
        return map.getValidNumbersSum()
    }

    fun solvePuzzlePartTwo(): Int {
       return map.getGearRatioSum()
    }
}

data class Position(val line: Int, val column: Int) {
    fun isValid(mapInfo: MapInfo? = null): Boolean {
        val isPositive = this.line >= 0 && this.column >= 0
        if (mapInfo != null) {
            return isPositive && this.line <= mapInfo.maxLines && this.column <= mapInfo.maxColumns
        }
        return isPositive
    }
}

data class MapNumber(val value: Int, val digitPositions: List<Position>, val allAdjacentPositions: List<Position>) {
    val adjacentPositions = allAdjacentPositions.toSet().filter { !digitPositions.contains(it) }
}

data class Symbol(val symbol: String, val position: Position, val adjacentPositions: List<Position>) {
    private val adjacentNumbers: MutableList<MapNumber> = mutableListOf()

    fun addAdjacentNumber(number: MapNumber) {
        adjacentNumbers.add(number)
    }

    fun getGearRatio(): Int {
        if (symbol == "*" && adjacentNumbers.size == 2) {
            return adjacentNumbers.first().value * adjacentNumbers.last().value
        }
        return 0
    }
}

data class MapInfo(val maxLines: Int, val maxColumns: Int)

data class Map(val text: String) {
    private val info: MapInfo = getMapInfo()
    private val symbols: List<Symbol> = getMapSymbols()
    private val numbers: List<MapNumber> = getNumbers()
    private val validNumbers: List<MapNumber> = getValidNumbers()

    fun getGearRatioSum(): Int {
        return symbols.fold(0) { acc, symbol ->
            val gearRatio = symbol.getGearRatio()
            acc + gearRatio
        }
    }

    private fun getAdjacentPositions(position: Position): List<Position> {
        val adjacentPositions: MutableList<Position> = mutableListOf()

        val topLeft = Position(position.line - 1, position.column - 1)
        adjacentPositions.add(topLeft)
        val top = Position(position.line - 1, position.column)
        adjacentPositions.add(top)
        val topRight = Position(position.line - 1, position.column + 1)
        adjacentPositions.add(topRight)

        val left = Position(position.line, position.column - 1)
        adjacentPositions.add(left)
        val right = Position(position.line, position.column + 1)
        adjacentPositions.add(right)

        val bottomLeft = Position(position.line + 1, position.column - 1)
        adjacentPositions.add(bottomLeft)
        val bottom = Position(position.line + 1, position.column)
        adjacentPositions.add(bottom)
        val bottomRight = Position(position.line + 1, position.column + 1)
        adjacentPositions.add(bottomRight)

        return adjacentPositions.filter { it.isValid(this.info) }
    }

    fun getValidNumbersSum(): Int {
        return validNumbers.fold(0) { acc, number -> acc + number.value }
    }

    private fun getValidNumbers(): List<MapNumber> {
        val validNumbers = mutableListOf<MapNumber>()
        for (number in numbers) {
            val adjacentSymbols = mutableListOf<Symbol>()
            number.adjacentPositions.forEach {
                val symbolInAdjacentPosition = symbols.find { symbol -> symbol.position == it }
                if (symbolInAdjacentPosition != null) {
                    symbolInAdjacentPosition.addAdjacentNumber(number)
                    adjacentSymbols.add(symbolInAdjacentPosition)
                }
            }

            if (adjacentSymbols.size > 0) {
                validNumbers.add(number)
            }
        }
        return validNumbers
    }

    private fun getNumbers(): List<MapNumber> {
        val numberMatches = """\d+""".toRegex().findAll(this.text.replace("\n", ""))

        return numberMatches.map { matchResult ->
            val digitPositions = matchResult.range.map {
                indexToPosition(it)
            }
            val adjacentPositions = digitPositions.map { getAdjacentPositions(it) }
            MapNumber(
                value = matchResult.value.toInt(),
                digitPositions = digitPositions,
                allAdjacentPositions = adjacentPositions.flatten()
            )
        }.toList()
    }

    private fun getMapSymbols(): List<Symbol> {
        val symbolMatches = """[^.\d\n]""".toRegex().findAll(this.text.replace("\n", ""))
        return symbolMatches.map { matchResult ->
            val symbolPosition = indexToPosition(matchResult.range.first)
            Symbol(
                symbol = matchResult.value,
                position = symbolPosition,
                adjacentPositions = getAdjacentPositions(symbolPosition)
            )
        }.toList()
    }


    private fun getMapInfo(): MapInfo {
        val lines = this.text.split("\n")

        return MapInfo(
            maxLines = lines.size,
            maxColumns = lines[0].length
        )
    }

    private fun indexToPosition(index: Int): Position {
        return Position(floor((index / info.maxLines).toDouble()).toInt(), index % info.maxColumns)
    }
}
