package de.company.demosender

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@SpringBootApplication
class DemoSenderApplication

fun main(args: Array<String>) {
	runApplication<DemoSenderApplication>(*args)
}
