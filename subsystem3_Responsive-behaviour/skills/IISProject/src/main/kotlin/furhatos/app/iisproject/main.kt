package furhatos.app.iisproject

import furhatos.app.iisproject.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class IisprojectSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
