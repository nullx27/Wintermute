package tech.grimm.wintermute

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan(
    *[
        "tech.grimm.wintermute.components",
        "tech.grimm.wintermute.interactions",
        "tech.grimm.wintermute.handlers",
        "tech.grimm.wintermute.services"
    ]
)
class WintermuteApplication

fun main(args: Array<String>) {
    runApplication<WintermuteApplication>(*args)
}