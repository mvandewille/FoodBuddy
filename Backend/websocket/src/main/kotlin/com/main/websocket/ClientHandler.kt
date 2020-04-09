package com.main.websocket

import java.io.*
import java.net.Socket
import java.util.*


class ClientHandler (private var s: Socket, private var num: Int, private var clients: MutableList<Socket>, private var messages: MutableList<String>): Runnable {
    var name: String? = null
    override fun run() {
        var inV: Scanner
        var outV: PrintWriter

        try {
            inV = Scanner(BufferedInputStream(s.getInputStream()))
            outV = PrintWriter(BufferedOutputStream(s.getOutputStream()))
            var authResponse = "0"
            var auth = false

            while(!auth) {
                if(inV.hasNextLine()) {
                    authResponse = inV.nextLine()
                    name = authResponse
                }
                broadcast("Welcome $name to the chat!", clients)
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

    private fun broadcastMsg(tokens: List<String>, clients: MutableList<Socket>) {
        var outV: PrintWriter
        messages.add(tokens[0] + ": " + tokens[1])
        saveMsg(tokens[0] + ": " + tokens[1])
        for(index in 0 until clients.size) {
            if(index == num)
                continue
            outV = PrintWriter(BufferedOutputStream(clients[index].getOutputStream()))
            outV.println(tokens[0] + ": " + tokens[1])
            outV.flush()
        }
        println(tokens[0] + ": " + tokens[1])
    }

    private fun broadcast(msg: String, clients: MutableList<Socket>) {
        var outV: PrintWriter
        messages.add(msg)
        saveMsg(msg)
        for(index in 0 until clients.size) {
            if(index == num)
                continue
            outV = PrintWriter(BufferedOutputStream(clients[index].getOutputStream()))
            outV.println(msg)
            outV.flush()
        }
        println(msg)
    }

    private fun catchUp(client: PrintWriter) {
        messages.forEach {
            client.println(it)
            client.flush()
        }
    }

    private fun saveMsg(msg: String) {
        //var fileName = "/Users/max/Documents/Random_Code/docker/websockets/msgLogs.txt"
        var fileName = "/home/maw1/docker/logs/msg_log.txt"
        val file = File(fileName)
        file.appendText(msg)
    }
}