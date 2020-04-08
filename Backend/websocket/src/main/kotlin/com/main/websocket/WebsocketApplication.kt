package com.main.websocket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.main.websocket.ClientHandler
import java.util.Scanner;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import kotlin.system.exitProcess

@SpringBootApplication
class WebsocketApplication

fun main(args: Array<String>) {
	//runApplication<WebsocketApplication>(*args)

	val code = "test"

	var serverSocket: ServerSocket? = null
	var clients = mutableListOf<Socket>()
	var clientNum = 0
	try{
		serverSocket = ServerSocket(4444)
		println(serverSocket)
	} catch (e: IOException) {
		println("Can't listen on port: 4444")
		exitProcess(-1)
	}

	while(true) {
		var clientSocket: Socket? = null

		try {
			clientSocket = serverSocket.accept()
			clients.add(clientSocket)

			var t: Thread = Thread(ClientHandler(clientSocket, clientNum, code, clients))
			t.start()
			clientNum++
		} catch (e: IOException) {
			println("Accept failed: 4444")
			exitProcess(-1)
		}
	}
}
