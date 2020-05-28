package furhatos.app.iisproject.flow

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

val Start : State = state(Interaction) {
    onEntry {
        furhat.say("Hi there.")
        furhat.say("I'm good at reading hands you know.")
        furhat.say("If you need any help, show me the back of your hand.")
        goto(Default)
    }

    onUserLeave {
        furhat.say ("Goodbye, hope to see you soon again.")
        goto(Idle)
    }
}

// Main interaction loop.
val Default : State = state(Interaction) {
    onEntry {
            /*
                0: Open palm
                1: Open dorsal
                2: Fist palm
                3: Fist dorsal
                4: Three fingers palm
                5: Three fingers dorsal
                6: Failed hand gesture
                7: New gesture
             */
        delay(500)
        when (read()) {
            0 -> {
                println("0")
                furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
                furhat.say("Hello, how are you doing?")
                furhat.say("If I could I would wave back at you.")
                goto(gotoDef)
            }
            1 -> {
                println("1")
                furhat.say("Is that the back of your hand?")
                delay(500)
                furhat.say("Wave if you want to know some of the gestures that I understand.")
                if (read() == 0) {
                    goto(Help)
                }
                else {
                    furhat.say("Okay, ask again if you need help.")
                    goto(gotoDef)
                }
            }
            2 -> {
                println("2")
                furhat.say("Okay, I'll stop talking to you.")
                goto(Idle)
            }
            3 -> {
                println("3")
                furhat.gesture(Gestures.ExpressFear(duration = 1.0, strength = 1.0))
                furhat.say("Are you threatening me?")
                goto(Confrontation)
            }
            4 -> {
                println("4")
                furhat.say("That kind of looks like a pair of scissors, don't you think?")
                goto(Game)

            }
            5 -> {
                println("5")
                furhat.say("Oh, did you want to play a game?")
                if (choice()) {
                    goto(Game)
                }
                else {
                    furhat.say("Another time then.")
                    goto(gotoDef)
                }
            }
            else -> goto(gotoDef)
        }
    }

    onUserLeave {
        furhat.say ("Goodbye, hope to see you soon again.")
        goto(Idle)
    }
}


// This is a hack to loop back around to the Default option, should probably be changed to something better.
val gotoDef : State = state(Interaction) {
    onEntry{
        goto(Default)
    }
}


// function returning false if gesture is a closed fist, otherwise true
fun choice(): Boolean {
    val gesture = read()
    var choice = false

    if (gesture != 2 or 3) {
        choice = true
    }

    return choice
}
