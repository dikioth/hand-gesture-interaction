package furhatos.app.iisproject.flow

import furhatos.flow.kotlin.*

val Start : State = state(Interaction) {

    onEntry {
        furhat.say("Hi there. I'm good at reading hands you know.")

        when (read()) {
            0 -> {
                println("0")
                furhat.say("Is that a 0?")
            }
            1 -> {
                println("1")
                furhat.say("Is that a 1?")
            }
            2 -> {
                println("2")
                furhat.say("Is that a 2?")
            }
            3 -> {
                println("3")
                furhat.say("Is that a 3?")
            }
            4 -> {
                println("4")
                furhat.say("Is that a 4?")
            }
            5 -> {
                println("5")
                furhat.say("Is that a 5?")
            }
            else -> { // Note the block
                print("no action")
                furhat.say("Sorry, I couldn't understand that, do you mind showing me again?")
            }
        }
    }
}
