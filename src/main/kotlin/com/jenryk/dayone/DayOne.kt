package com.jenryk.dayone

import com.jenryk.utils.readFromInputs

fun main() {
    val puzzleAnswer = DayOne().solvePuzzle()
    println("The answer is: $puzzleAnswer")
}

class DayOne {
    private val one = Regex("one")
    private val two = Regex("two")
    private val three = Regex("three")
    private val four = Regex("four")
    private val five = Regex("five")
    private val six = Regex("six")
    private val seven = Regex("seven")
    private val eight = Regex("eight")
    private val nine = Regex("nine")

    fun solvePuzzle(): Int {
        val input = readFromInputs("dayone.txt")
        return getTotalCalibration(input)
    }

    fun getTotalCalibration(input: String): Int {
        return input.split("\n").fold(0) {
             acc, line ->
                val lineNumbers = getSpelledNumbers(line)
                acc + getCalibrationValue(lineNumbers)
        }
    }

    fun getLineNumbers(line: String): List<Char>{
        return line.toCharArray().filter {
            it.isDigit()
        }
    }


    fun getCalibrationValue(lineChars: List<Char>): Int {
        val first = lineChars.first()
        val last = lineChars.last()

        return "${first}${last}".toInt()
    }

    fun getSpelledNumbers(line: String): List<Char> {
        val numbers: MutableList<Char> = mutableListOf()
        var buffer = ""
        for( char in line) {
            buffer += char

            if(char.isDigit()){
                numbers.add(char)
                buffer = ""
            }else{
                val parsedDigit = when {
                    one.containsMatchIn(buffer) -> '1'
                    two.containsMatchIn(buffer) -> '2'
                    three.containsMatchIn(buffer) -> '3'
                    four.containsMatchIn(buffer) -> '4'
                    five.containsMatchIn(buffer) -> '5'
                    six.containsMatchIn(buffer) -> '6'
                    seven.containsMatchIn(buffer) -> '7'
                    eight.containsMatchIn(buffer) -> '8'
                    nine.containsMatchIn(buffer) -> '9'
                    else -> 0
                }
                if(parsedDigit is Char) {
                    numbers.add(parsedDigit)
                    buffer = buffer.last().toString()
                }
            }
        }
        return numbers
    }
}