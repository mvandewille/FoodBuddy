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
                    if(authResponse.contains("name;"))
                        name = authResponse
                    else
                        continue
                }
                broadcast(Message("server", "Welcome $name!"), clients)
                catchUp(outV)
                outV.println("you are connected")
                outV.flush()
                auth = true
            }
            var response: String
            while(inV.hasNextLine()) {
                response = inV.nextLine()
                broadcast(Message(response.split(";")), clients)
            }

        } catch (e: IOException) {
            e.printStackTrace()
            println("Err")
        }
        System.out.flush()
    }

    private fun broadcast(msg: Message, clients: MutableList<Socket>) {
        var outV: PrintWriter
        messages.add(msg.format())
        saveMsg(msg.format())
        for(index in 0 until clients.size) {
            if(index == num)
                continue
            outV = PrintWriter(BufferedOutputStream(clients[index].getOutputStream()))
            outV.println(msg.format())
            outV.flush()
        }
        msg.print()
    }

    private fun catchUp(client: PrintWriter) {
        messages.forEach {
            client.println(it)
            client.flush()
        }
    }

    private fun saveMsg(msg: String) {
        //var fileName = "/Users/max/Documents/Random_Code/docker/websockets/msgLogs.txt"
        var fileName = "/log.txt"
        val file = File(fileName)
        file.appendText(msg + "\n")
    }
}