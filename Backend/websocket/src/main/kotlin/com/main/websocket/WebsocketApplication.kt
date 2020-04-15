package com.main.websocket

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class WebsocketApplication

fun main(args: Array<String>) {
    SpringApplication.run(WebsocketApplication::class.java, *args)
}