package furhatos.app.iisproject.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*

val Start : State = state(Interaction) {

    onEntry {
        furhat.say("Hi there. Do you like robots?")

        val action = read()
        println(action == 0)
        if (action == 0) {
            furhat.say("Is that a hand?")
        }
    }

 /*   onResponse<Yes>{
        furhat.say("I like humans.")
    }

    onResponse<No>{
        furhat.say("That's sad.")
    }*/
}
