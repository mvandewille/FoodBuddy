package com.main.app.websocket

import com.main.app.json.ResponseJ
import com.main.app.model.Message
import com.main.app.model.Status
import com.main.app.repository.MessageRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.io.IOException
import java.util.*
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint
import org.springframework.util.StringUtils.collectionToDelimitedString


@Controller // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{username}") // this is Websocket url
class ChatSocket {
    /*
   * Grabs the MessageRepository singleton from the Spring Application
   * Context.  This works because of the @Controller annotation on this
   * class and because the variable is declared as static.
   * There are other ways to set this. However, this approach is
   * easiest.
	 */
    @Autowired
    fun setMessageRepository(repo: MessageRepository?) {
        msgRepo = repo // we are setting the static variable
    }

    private val logger = LoggerFactory.getLogger(ChatSocket::class.java)
    private var session: Session? = null
    @OnOpen
    @Throws(IOException::class)
    fun onOpen(session: Session, @PathParam("username") username: String) {
        logger.info("Entered into Open")

        // store connecting user information
        sessionUsernameMap[session] = username
        usernameSessionMap[username] = session
        this.session = session

        //Send chat history to the newly connected user
        //sendMessageToParticularUser(username, chatHistory)

        // broadcast that new user joined
        val message = "$username has Joined the Chat"
        broadcast(Message(getNewId(), "server", message + ";" + collectionToDelimitedString(usernameSessionMap.keys, ";")))
    }

    @OnMessage
    @Throws(IOException::class)
    fun onMessage(session: Session, message: String) {

        // Handle new messages
        logger.info("Entered into Message: Got Message:$message")
        val username = sessionUsernameMap[session] ?: error("user not found")

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {
            val destUsername = message.split(":").toTypedArray()[0].substring(1)
            val newMsg= message.substring(destUsername.length + 2).trim(' ')

            // send the message to the sender and receiver
            sendMessageToParticularUser(destUsername, "[DM] $newMsg")
        }
        else if (message.contains("&&wipe")) {
            (msgRepo ?: error("no repo")).deleteAllBy()
            broadcast(Message(getNewId(), "server","$username wiped all messages!"))
        } else { // broadcast
            broadcast(Message(getNewId(), username, message))
        }
    }

    @OnClose
    @Throws(IOException::class)
    fun onClose(session: Session) {
        logger.info("Entered into Close")

        // remove the user connection information
        val username = sessionUsernameMap[session]
        sessionUsernameMap.remove(session)
        usernameSessionMap.remove(username)

        // broadcase that the user disconnected
        val message = "$username disconnected"
        broadcast(Message(getNewId(), "server", message + ";" + collectionToDelimitedString(usernameSessionMap.keys, ";")))
    }

    @OnError
    fun onError(session: Session?, throwable: Throwable) {
        // Do error handling here
        logger.info("Entered into Error")
        throwable.printStackTrace()
    }

    private fun sendMessageToParticularUser(username: String, message: String) {
        try {
            (usernameSessionMap[username] ?: error("user not found")).basicRemote.sendText(Message(getNewId(), username, message).toString())
        } catch (e: IOException) {
            logger.info("Exception: " + e.message.toString())
            e.printStackTrace()
        }
    }
    private fun sendMessageToParticularUser(username: String?, messages: MutableList<Message>) {
        try {
            messages.forEach {
                usernameSessionMap[username]!!.basicRemote.sendText(it.toString())
            }
        } catch (e: IOException) {
            logger.info("Exception: " + e.message.toString())
            e.printStackTrace()
        }
    }

    private fun broadcast(message: Message) {
        sessionUsernameMap.forEach { (session: Session, username: String?) ->
            try {
                if(message.getFrom() == "server" || session != this.session)
                    session.basicRemote.sendText(message.toString())
            } catch (e: IOException) {
                logger.info("Exception: " + e.message.toString())
                e.printStackTrace()
            }
        }
        msgRepo!!.save<Message>(Message(getNewId(), message.getFrom(), message.getText()))
    }// convert the list to a string

    // Gets the Chat history from the repository
    private val chatHistory: MutableList<Message>
        private get() {
            return msgRepo!!.findAll()
        }

    private fun getNewId(): Long {
        val list = (msgRepo ?: error("Repo not initialized")).findAllByOrderByIdDesc()
        if(list.isNotEmpty()) {
            return list.first().getId()+1
        }
        else {
            return 0.toLong()
        }
    }

    companion object {
        // cannot autowire static directly (instead we do it by the below
        // method
        private var msgRepo: MessageRepository? = null

        // Store all socket session and their corresponding username.
        private val sessionUsernameMap: MutableMap<Session, String> = Hashtable()
        private val usernameSessionMap: MutableMap<String?, Session> = Hashtable()
    }
} // end of Class

