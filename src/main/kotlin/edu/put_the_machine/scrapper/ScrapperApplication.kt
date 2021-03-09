package edu.put_the_machine.scrapper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class ScrapperApplication

fun main(args: Array<String>) {
    runApplication<ScrapperApplication>(*args)
}
