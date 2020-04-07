import java.util.Scanner;
import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) throws IOException
    {
        String code = "foodbuddy";

        ServerSocket serverSocket = null;
        ArrayList<Socket> clients = new ArrayList<Socket>();
        int clientNum = 0;
        try
        {
            serverSocket = new ServerSocket(4444);

            System.out.println(serverSocket);
        } catch (IOException e)
        {
            System.out.println("Can't listen on port: 4444");
            System.exit(-1);
        }

        while(true)
        {
            Socket clientSocket = null;

            try
            {
                clientSocket = serverSocket.accept();
                clients.add(clientSocket);

                Thread t = new Thread(new ClientHandler(clientSocket, clientNum, code, clients));
                t.start();
                clientNum++;
            } catch (IOException e)
            {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
            }
        }
    }
}

class ClientHandler implements Runnable
{
    Socket s;
    int num;
    String code;
    String name;
    ArrayList<Socket> clients;

    ClientHandler(Socket s, int n, String code, ArrayList<Socket> clients) {
        this.s = s;
        this.num = n;
        this.code = code;
        this.name = null;
        this.clients = clients;
    }

    public void run() {
        Scanner in;
        PrintWriter out;

        try {
            in = new Scanner(new BufferedInputStream(s.getInputStream()));
            out = new PrintWriter((new BufferedOutputStream(s.getOutputStream())));
            String authResponse = "0";
            String clientCode = "0";
            boolean auth = false;

            while(!auth) {
                if(in.hasNextLine())
                    authResponse = in.nextLine();
                    clientCode = authResponse.split(":")[1];
                    name = authResponse.split(":")[0];
                if (!clientCode.equals(code)) {
                    //System.out.println("denied");
                    out.println("incorrect access code");
                    out.flush();
                } else {
                    System.out.println("Welcome " + name + " to the chat!");
                    out.println("you are connected");
                    out.flush();
                    auth = true;
                }
            }
            String response;
            while(in.hasNextLine()) {
                response = in.nextLine();
                broadcastMsg(response.split(":"), clients);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Err");
        }
        System.out.flush();
    }

    // send a message to all connected clients, except the client that sent the message
    void broadcastMsg(String[] tokens, ArrayList<Socket> clients) throws IOException {
        PrintWriter out;
        for( int i = 0; i < clients.size(); i++ ) {
            if( i == num )
                continue;
            out = new PrintWriter((new BufferedOutputStream(clients.get(i).getOutputStream())));
            out.println(tokens[0] + ": " + tokens[1]);
            out.flush();
        }
        System.out.println(tokens[0] + ": " + tokens[1]);
    }
}
