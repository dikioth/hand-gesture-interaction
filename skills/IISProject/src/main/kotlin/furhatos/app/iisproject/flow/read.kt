package furhatos.app.iisproject.flow

import java.io.File

fun read(): Int {
    // Create file to tell python function to give input
    val fileName = "test.txt"
    File(fileName).writeText("1\n0")
    val file = File(fileName)

    val input = File(fileName).readLines()

    // File consists of two inputs, first line 0 or other, if 0 file hasn't been updated else file can be read
    // second line is an int 0-5, giving the input for what hand gesture has been given as input
    if (input.first() != "0") {
        file.deleteRecursively()
        return input.elementAt(1).toInt()
    }
    else {
        Thread.sleep(500L)
        read()
    }

    file.deleteRecursively()
    throw IllegalAccessException(".txt has to contain two lines consisting of two Ints")
}
