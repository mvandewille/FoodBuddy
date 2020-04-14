import java.util.Scanner;


import java.net.*;
import java.io.*;

public class client {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        try { //first we get the users name and pass the access code they provide to the server
            Scanner input = new Scanner(System.in);

            System.out.print("enter your name: ");
            String name = input.nextLine();
            Socket socket = new Socket("coms-309-hv-3.cs.iastate.edu", 4444);
            //Socket socket = new Socket("localhost", 4444);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            out.println("name;" + name);
            out.flush();

            String buffer;
            Boolean done = false;
            Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()));

            System.out.println();

            while(in.hasNextLine() || !done) {
                buffer = in.nextLine();
                if(!buffer.equals("you are connected")) {
                    System.out.println(parse(buffer));
                }
                else {
                    System.out.println(parse(buffer));
                    break;
                }
            }

            Thread t = new Thread(new MsgHandler(socket, name));
            t.start();

        } catch (ConnectException e) {
            System.out.println("Connection failed!");
        }

    }

    static void printSocketInfo(Socket s) {
        System.out.print("Socket on Client Side: ");
        System.out.print("Local Address: " + s.getLocalAddress() + ":" + s.getLocalPort());
        System.out.println("  Remote Address: " + s.getRemoteSocketAddress());
    }

    public static String parse(String input) {
        String[] temp = input.split(";");
        if(temp.length > 2)
            return temp[2].split(" ")[1] + " " + temp[0] + ": " + temp[1];
        else    
            return temp[0];
    }
}

class MsgHandler implements Runnable {
    Socket s;
    String name;
    String backspace = "";

    MsgHandler(Socket s, String name) {
        this.s = s;
        this.name = name;
        for(int i = 0; i < name.length() + 2; i++)
            this.backspace = this.backspace + "\b";
    }

    //this thread handles sending and receiving messages with the server
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            String msg = "";
            System.out.print(name + ": ");
            while(!s.isClosed()) {
                if(in.ready()) {
                    String buffer = in.readLine();
                    if(buffer != null) {
                        System.out.print(backspace);
                        System.out.println(parse(buffer));
                        System.out.print(name + ": ");
                    }
                }
                if(user.ready())
                    msg = user.readLine();
                    if(msg.length() > 0) {
                        out.println(name + ";" + msg);
                        msg = "";
                        System.out.print(name + ": ");
                    }
            }
        } catch (IOException e) {
            System.out.println("Err");
        }

    }

    public String parse(String input) {
        String[] temp = input.split(";");
        if(temp.length > 1)
            return temp[2].split(" ")[1] + " " + temp[0] + ": " + temp[1];
        else    
            return temp[0];
    }

}
