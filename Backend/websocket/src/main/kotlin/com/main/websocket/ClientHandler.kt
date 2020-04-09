package com.main.websocket

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.*


class ClientHandler (private var s: Socket, private var num: Int, private var code: String, private var clients: MutableList<Socket>, private var messages: MutableList<String>): Runnable {
    var name: String? = null
    override fun run() {
        var inV: Scanner
        var outV: PrintWriter

        try {
            inV = Scanner(BufferedInputStream(s.getInputStream()))
            outV = PrintWriter(BufferedOutputStream(s.getOutputStream()))
            var authResponse = "0"
            var clientCode = "0"
            var auth = false

            while(!auth) {
                if(inV.hasNextLine()) {
                    authResponse = inV.nextLine()
                    clientCode = authResponse.split(":")[1]
                    name = authResponse.split(":")[0]
                }
                broadcast("Welcome " + name + " to the chat!", clients)
                catchUp(outV)
                outV.println("you are connected. Past messages are shown above")
                outV.flush()
                auth = true
            }
            var response: String
            while(inV.hasNextLine()) {
                response = inV.nextLine()
                broadcastMsg(response.split(":"), clients)
            }

        } catch (e: IOException) {
            e.printStackTrace()
            println("Err")
        }
        System.out.flush()
    }

    fun broadcastMsg(tokens: List<String>, clients: MutableList<Socket>) {
        var outV: PrintWriter
        for(index in 0 until clients.size) {
            if(index == num)
                continue
            messages.add(tokens[0] + ": " + tokens[1])
            outV = PrintWriter(BufferedOutputStream(clients.get(index).getOutputStream()))
            outV.println(tokens[0] + ": " + tokens[1])
            outV.flush()
        }
        println(tokens[0] + ": " + tokens[1])
    }

    fun broadcast(msg: String, clients: MutableList<Socket>) {
        var outV: PrintWriter
        for(index in 0 until clients.size) {
            if(index == num)
                continue
            outV = PrintWriter(BufferedOutputStream(clients.get(index).getOutputStream()))
            outV.println(msg)
            outV.flush()
        }
        println(msg)
    }

    fun catchUp(client: PrintWriter) {
        messages.forEach {
            client.println(it)
            client.flush()
        }
    }
}