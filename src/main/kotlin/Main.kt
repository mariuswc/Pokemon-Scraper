package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebScraperApplication

fun main(args: Array<String>) {
    runApplication<WebScraperApplication>(*args)
}