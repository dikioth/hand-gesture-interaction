package furhatos.app.iisproject.flow

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

val Start : State = state(Interaction) {

    onEntry {
        furhat.say("Hi there.")
        furhat.say("I'm good at reading hands you know.")
        goto(Default)
    }
}

val Default : State = state(Interaction) {
    onEntry {
        while (true) {
            val handGesture = read()

            /*
                0: Open palm
                1: Open dorsal
                2: Fist palm
                3: Fist dorsal
                4: Three fingers palm
                5: Three fingers dorsal
                6: Failed hand gesture
             */
            when (handGesture) {
                0 -> {
                    println("0")
                    furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
                    furhat.say("Hello, how are you doing?")
                    furhat.say("If I could, I would wave back at you.")
                    goto(gotoDef)
                }
                1 -> {
                    println("1")
                    furhat.say("Is that the back of your hand?")
                    goto(gotoDef)
                }
                2 -> {
                    println("2")
                    furhat.say("Okay, I'll stop talking to you")
                    goto(Idle)
                }
                3 -> {
                    println("3")
                    furhat.gesture(Gestures.ExpressFear(duration = 2.0, strength = 1.0))
                    furhat.say("Are you threatening me?")
                    goto(Confrontation)
                }
                4 -> {
                    println("4")
                    furhat.say("That kind of looks like a scissor, don't you think?")
                    goto(Game)
                }
                5 -> {
                    println("5")
                    furhat.say("What does that mean?")
                    goto(gotoDef)
                }
                else -> { // Note the block
                    print("no action")
                    random (
                            { furhat.say("Maybe I need glasses, but I don't seem to see your hands properly.")},
                            { furhat.say("I don't have any hands, could you show me yours?")},
                            { furhat.say("You probably cant see it, but I'm waving at you, maybe you could try waving back at me?")},
                            { furhat.say("I'm sorry, but I don't seem to understand what you are trying to say, could you try showing me again?")}
                    )
                }
            }
            if (handGesture < 6) {
                break
            }
        }
    }

    onUserLeave {
        furhat.say ("Goodbye, hope to see you soon again.")
        goto(Idle)
    }
}

val gotoDef : State = state(Interaction) {
    onEntry{
        goto(Default)
    }
}

val Confrontation : State = state(Interaction) {
    onEntry {
        /*
            0: Open palm
            1: Open dorsal
            2: Fist palm
            3: Fist dorsal
            4: Three fingers palm
            5: Three fingers dorsal
            6: Failed hand gesture
         */
        when (read()) {
            0 -> {
                println("0")
                furhat.gesture(Gestures.Oh(duration = 2.0, strength = 1.0))
                furhat.say("Oh, so you surrender. That's good.")
                goto(Default)
            }
            1 -> {
                println("1")
                furhat.gesture(Gestures.BrowRaise)
                furhat.say("I'm not really sure about what you are trying to say")
                goto(Default)
            }
            2 -> {
                println("2")
                furhat.gesture(Gestures.Oh(duration = 2.0, strength = 1.0))
                furhat.say("Oh, I think I misunderstood you. Sorry about that.")
                goto(Default)
            }
            3 -> {
                println("3")
                furhat.gesture(Gestures.ExpressAnger(duration = 1.0, strength = 0.5))
                furhat.say("You really shouldn't do that!")
                goto(Default)
            }
            4 -> {
                println("4")
                furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
                furhat.say("Okay, I'll give you three hugs.")
                furhat.gesture(Gestures.Oh(duration = 2.0, strength = 1.0))
                furhat.say("Oh, but I don't have any arms.")
                goto(Default)
            }
            5 -> {
                println("5")
                furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
                furhat.say("Okay, I'll give you three hugs.")
                furhat.gesture(Gestures.Oh(duration = 2.0, strength = 1.0))
                furhat.say("Oh, but I don't have any arms.")
                goto(Default)
            }
            else -> { // Note the block
                print("no action")
                furhat.say("You should be nice to robots, we might rule the world one day.")
            }
        }
    }

    onUserLeave {
        furhat.say("Yes, maybe it's best you leave and cool down for a while.")
    }
}

val Game : State = state(Interaction) {
    onEntry {
        furhat.say("Lets play rock, paper, scissors.")
        furhat.say("Since I don't have any hands I'll just think of a move and tell you who the winner is. You can trust me, I would never cheat.")

        val move = (1..6).random()

        furhat.gesture(Gestures.Thoughtful(duration = 2.0, strength = 1.0))
        furhat.say("Ready?")
        furhat.say("Rock.")
        furhat.say("Paper.")
        furhat.say("Scissors.")
        furhat.say("Go!")

        when (read()) {
            0, 1 -> {
                when (move) {
                    0, 1 -> furhat.say("That would be draw.")
                    2, 3 -> {
                        furhat.gesture(Gestures.ExpressSad(duration = 1.0, strength = 1.0))
                        furhat.say("Oh no. You won.")
                    }
                    4, 5 ->  {
                        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
                        furhat.say("oh wow, I think I won.")
                    }
                }
            }
            2, 3 -> {
                when (move) {
                    0, 1 -> {
                        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
                        furhat.say("oh wow, I think I won.")
                    }
                    2, 3 -> furhat.say("That would be draw.")
                    4, 5 ->  {
                        furhat.gesture(Gestures.ExpressSad(duration = 1.0, strength = 1.0))
                        furhat.say("Oh no. You won.")
                    }
                }
            }
            4, 5 -> {
                when (move) {
                    0, 1 -> {
                        furhat.gesture(Gestures.ExpressSad(duration = 1.0, strength = 1.0))
                        furhat.say("Oh no. You won.")
                    }
                    2, 3 -> {
                    furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
                    furhat.say("oh wow, I think I won.")
                    }
                    4, 5 -> furhat.say("That would be draw.")
                }
            }
            else -> furhat.say("I don't think that is a legal move.")
        }
        goto(Default)
    }
}