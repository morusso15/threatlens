package com.threatlens.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThreatlensApiApplication

fun main(args: Array<String>) {
    runApplication<ThreatlensApiApplication>(*args)
}