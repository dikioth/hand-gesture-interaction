package furhatos.app.iisproject.flow

import java.io.File


/* Function for retrieving hand gesture input from python script.
   returns an int 0-6

   0: Open palm
   1: Open dorsal
   2: Fist palm
   3: Fist dorsal
   4: Three fingers palm
   5: Three fingers dorsal
   6: Failed hand gesture
   7: No gesture
 */
fun read(): Int {
    /* Create file to tell python script to write input to file.
       File has two fields.
       First field is 0 or 1 for checking if file should be read or not
       Second field is 0-6, defining input.
     */
    val fileName = "test.txt"
    File(fileName).writeText("1\n0")
    val file = File(fileName)

    Thread.sleep(500L)

    var timeout = 0
    while (true) {
        val input = File(fileName).readLines()

        // If file has been written to, return input from file.
        if (input.first() != "1") {
            file.deleteRecursively()
            return input.elementAt(1).toInt()

        } else {
            Thread.sleep(500L)

            // Timeout after testing x amount of times and return faulty option.
            timeout += 1
            if (timeout == 10) {
                file.deleteRecursively()
                return 7
            }
        }
    }
}


