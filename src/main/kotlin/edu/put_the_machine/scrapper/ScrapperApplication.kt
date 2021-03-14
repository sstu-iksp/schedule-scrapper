package edu.put_the_machine.scrapper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
class ScrapperApplication

fun main(args: Array<String>) {
    runApplication<ScrapperApplication>(*args)
}
