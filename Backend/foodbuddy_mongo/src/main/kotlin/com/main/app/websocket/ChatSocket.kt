package com.main.app.websocket

import com.main.app.model.Message
import com.main.app.repository.MessageRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import java.io.IOException
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint


@Controller
@ServerEndpoint(value = "/chat")
class ChatSocket {

    //@Autowired
    //lateinit var msgRepo: MessageRepository

    private val sessionUsernameMap: MutableMap<Session, String> = mutableMapOf<Session, String>()
    private val usernameSessionMap: MutableMap<String, Session> = mutableMapOf<String, Session>()

    private val logger: Logger = LoggerFactory.getLogger(ChatSocket::class.java)

    @OnOpen
    fun onOpen(session: Session, @RequestParam(value = "username", required = true) username: String) {
        logger.info("Entered into Open")

        sessionUsernameMap[session] = "max"
        usernameSessionMap["max"] = session


    }

    @OnMessage
    fun onMessage(session: Session, message: String) {
        logger.info("Entered into Message: Got Message: $message")
        val username = sessionUsernameMap[session] ?: error("username is Null!")

        if(message.startsWith("@")) {
            val destUsername = message.split(" ")[0].substring(1)

            sendMessageToParticularUser(destUsername, "[DM] $username: $message")
            sendMessageToParticularUser(username, "[DM] $username: $message")
        }
        else {
            broadcast("$username: $message")
        }

        //msgRepo.save(Message(getNewId(), username, message))
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
            (usernameSessionMap[username] ?: error("user not found")).basicRemote.sendText(message)
        }
        catch (e: IOException) {
            logger.info("Exception: " + e.message.toString())
            e.printStackTrace()
        }
    }

    private fun broadcast(message: String) {
        sessionUsernameMap.forEach { (session, username) ->
            try {
                println(username)
                session.basicRemote.sendText(message)
            }
            catch (e: IOException) {
                logger.info("Exception: " + e.message.toString())
                e.printStackTrace()
            }
        }
    }

//    private fun getChatHistory(): String {
//        val messages = msgRepo.findAll() ?: error("no messages!")
//
//        val sb = StringBuilder()
//        if(messages.size != 0) {
//            messages.forEach {
//                sb.append(it.getFrom() + ": " + it.getText() + "\n")
//            }
//        }
//        return sb.toString()
//    }

//    fun getNewId(): Long {
//        val list = msgRepo.findAllByOrderByIdDesc()
//        if(!list.isEmpty()) {
//            return list.first().getId() + 1
//        }
//        else {
//            return 0.toLong()
//        }
//    }
}