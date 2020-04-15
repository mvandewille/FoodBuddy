package com.main.app.websocket

import com.main.app.json.ResponseJ
import com.main.app.model.Message
import com.main.app.model.Status
import com.main.app.repository.MessageRepository
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.lang.StringBuilder

@Controller
@ServerEndpoint(value = "/chat/{username}")
class ChatSocket {

    lateinit var msgRepo: MessageRepository

    @Autowired
    fun setMessageRepository(repo: MessageRepository) {
        msgRepo = repo
    }

    private val sessionUsernameMap: MutableMap<Session, String> = mutableMapOf<Session, String>()
    private val usernameSessionMap: MutableMap<String, Session> = mutableMapOf<String, Session>()

    private val logger: Logger = LoggerFactory.getLogger(ChatSocket::class.java)

    @OnOpen
    fun onOpen(session: Session, @PathParam("username") username: String) {
        logger.info("Entered into Open")

        sessionUsernameMap.put(session, username)
        usernameSessionMap.put(username, session)


    }

    @OnMessage
    fun onMessage(session: Session, message: String) {
        logger.info("Entered into Message: Got Message: " + message)
        val username = sessionUsernameMap[session] ?: error("username is Null!")

        if(message.startsWith("@")) {
            val destUsername = message.split(" ")[0].substring(1)

            sendMessageToParticularUser(destUsername, "[DM] $username: $message")
            sendMessageToParticularUser(username, "[DM] $username: $message")
        }
        else {
            broadcast("$username: $message")
        }

        msgRepo.save(Message(getNewId(), username, message))
    }

    @OnClose
    fun onClose(session: Session) {
        logger.info("Entered on Close")

        val username = sessionUsernameMap[session] ?: error("username is Null!")
        sessionUsernameMap.remove(session)
        usernameSessionMap.remove(username)

        broadcast("$username disconnected")
    }

    @OnError
    fun onError(session: Session, throwable: Throwable) {
        logger.info("Entered on Error")
        throwable.printStackTrace()
    }

    private fun sendMessageToParticularUser(username: String, message: String) {
        try {
            usernameSessionMap[username]!!.basicRemote.sendText(message)
        }
        catch (e: IOException) {
            logger.info("Exception: " + e.message.toString())
            e.printStackTrace()
        }
    }

    private fun broadcast(message: String) {
        sessionUsernameMap.forEach { (session, s) ->
            try {
                session.basicRemote.sendText(message)
            }
            catch (e: IOException) {
                logger.info("Exception: " + e.message.toString())
                e.printStackTrace()
            }
        }
    }

    private fun getChatHistory(): String {
        val messages = msgRepo.findAll() ?: error("no messages!")

        val sb = StringBuilder()
        if(messages.size != 0) {
            messages.forEach {
                sb.append(it.getFrom() + ": " + it.getText() + "\n")
            }
        }
        return sb.toString()
    }

    fun getNewId(): Long {
        val list = msgRepo.findAllByOrderByIdDesc()
        if(!list.isEmpty()) {
            return list.first().getId() + 1
        }
        else {
            return 0.toLong()
        }
    }
}