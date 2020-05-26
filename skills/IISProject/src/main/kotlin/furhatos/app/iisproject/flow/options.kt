package furhatos.app.iisproject.flow

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

// Option for having a short argument with furhat.
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
                furhat.gesture(Gestures.Oh(duration = 1.0, strength = 1.0))
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
                furhat.gesture(Gestures.Oh(duration = 1.0, strength = 1.0))
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
                furhat.gesture(Gestures.Smile(duration = 1.0, strength = 1.0))
                furhat.say("Okay, I'll give you three hugs.")
                delay(500)
                furhat.gesture(Gestures.Oh(duration = 1.0, strength = 1.0))
                furhat.say("Oh, but I don't have any arms.")
                goto(Default)
            }
            5 -> {
                println("5")
                furhat.gesture(Gestures.Smile(duration = 1.0, strength = 1.0))
                furhat.say("Okay, I'll give you three hugs.")
                furhat.gesture(Gestures.Oh(duration = 1.0, strength = 1.0))
                furhat.say("Oh, but I don't have any arms.")
                goto(Default)
            }
            else -> { // Note the block
                println("no action")
                furhat.say("You should be nice to robots, we might rule the world one day.")
                goto(Default)
            }
        }
    }

    onUserLeave {
        furhat.say("Yes, maybe it's best you leave and cool down for a while.")
    }
}


// Option for playing one round of rock, paper, scissors.
val Game : State = state(Interaction) {
    onEntry {
        furhat.gesture(Gestures.BigSmile(duration = 1.0, strength = 1.0))
        furhat.say("Lets play rock paper scissors!")
        furhat.say("Since I don't have any hands, I'll just think of a move and tell you who the winner is. You can trust me, I would never cheat.")

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
                    0, 1 -> furhat.say("That would be a draw.")
                    2, 3 -> {
                        furhat.gesture(Gestures.ExpressSad(duration = 1.0, strength = 1.0))
                        furhat.say("Oh no. You won.")
                    }
                    4, 5 ->  {
                        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
                        furhat.say("Oh wow, I think I won.")
                    }
                }
            }
            2, 3 -> {
                when (move) {
                    0, 1 -> {
                        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
                        furhat.say("Oh wow, I think I won.")
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
            else -> furhat.say("I don't think that is a legal move, lets call it a draw.")
        }
        delay(500)
        goto(Default)
    }
}


// Option for getting help with what hand gestures can be used.
val Help : State = state(Interaction) {
    onEntry {
        furhat.say("I understand mainly three hand gestures.")
        furhat.say("Open palm, fist, and three fingers composed of the thumb, index, and middle finger.")

        furhat.say("Why don't you try showing me a gesture?")
        while (true) {
            val gesture = read()
            when (gesture) {
                0, 1 -> furhat.say("Great, that's a palm.")
                2, 3 -> furhat.say("Good job, that's a fist. I don't really like that gesture to be honest.")
                4, 5 -> furhat.say("That is indeed three fingers. I get an urge to play a game when I see that for some reason.")
                else -> furhat.say("That's not a gesture I recognize, try again.")
            }

            if (gesture < 6) {
                delay(500)
                furhat.say("Remember, I can recognize both front and back of your hand.")
                furhat.say("That's all you need to know.")
                goto(Default)
            }
        }
    }
}
