package tech.grimm.wintermute

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan(
    "tech.grimm.wintermute.components",
    "tech.grimm.wintermute.interactions",
    "tech.grimm.wintermute.handlers",
    "tech.grimm.wintermute.services"
)
class WintermuteApplication {
    /**
     * Heartbeat to keep the Application running and responsive
     */
    @Scheduled(fixedRate = 5000)
    fun heartbeat() {}
}

fun main(args: Array<String>) {
    runApplication<WintermuteApplication>(*args)
}