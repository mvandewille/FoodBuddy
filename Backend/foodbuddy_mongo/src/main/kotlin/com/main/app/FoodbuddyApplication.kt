package com.main.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.main.app.websocket.ClientHandler
import com.main.app.websocket.Message
import java.net.*
import java.io.*
import kotlin.system.exitProcess

@SpringBootApplication
class FoodbuddyApplication

fun main(args: Array<String>) {
	runApplication<FoodbuddyApplication>(*args)
	println("WebSocket server starting...")
	var serverSocket: ServerSocket? = null
	var clients = mutableListOf<Socket>()
	var messages = mutableListOf<String>()

	//var fileName = "/Users/max/Documents/Random_Code/docker/websockets/msgLogs.txt"
	var fileName = "/log.txt"
	File(fileName).forEachLine {
		messages.add(it)
		Message(it.split(";")).print()
	}
	var clientNum = 0
	try{
		serverSocket = ServerSocket(8080)
	} catch (e: IOException) {
		println("Can't listen on port: 8080")
		exitProcess(-1)
	}

	while(true) {
		var clientSocket: Socket? = null

		try {
			clientSocket = serverSocket.accept()
			clients.add(clientSocket)

			var t = Thread(ClientHandler(clientSocket, clientNum, clients, messages))
			t.start()
			clientNum++
		} catch (e: IOException) {
			println("Accept failed: 8080")
			exitProcess(-1)
		}
	}
}
