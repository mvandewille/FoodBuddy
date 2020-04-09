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

	var serverSocket: ServerSocket? = null
	var clients = mutableListOf<Socket>()
	var messages = mutableListOf<String>()

	var fileName = "/home/maw1/docker/logs/msg_log.txt"
	File(fileName).forEachLine {
		messages.add(it)
	}
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

			var t: Thread = Thread(ClientHandler(clientSocket, clientNum, clients, messages))
			t.start()
			clientNum++
		} catch (e: IOException) {
			println("Accept failed: 4444")
			exitProcess(-1)
		}
	}
}
