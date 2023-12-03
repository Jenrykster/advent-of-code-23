package com.jenryk.utils

import java.io.File
import java.net.URL

fun readFromInputs(path: String): String {
    val bufferedReader = File("./src/main/resources/inputs/${path}").bufferedReader()
    return bufferedReader.use {
        it.readText()
    }
}