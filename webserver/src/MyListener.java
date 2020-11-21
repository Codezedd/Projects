import java.io.*;
import java.net.*;
public class MyListener {
    //port number is where we are connecting for communication from the web browser(firefox)
    //from initial understanding once the telnet client connects to the browser
    //listener should be receiving what the browser has for information
    public static boolean controlSwitch = true;

    public static void main(String a[]) throws IOException {
        int queueLength = 6; /* Number of requests for OpSys to queue */

        int portNum = 2540;
        Socket sock;

        ServerSocket servSock = new ServerSocket(portNum, queueLength);

        System.out.println("Keven Zarate's Port listener running on port: " + portNum);
        while (controlSwitch) {

            sock = servSock.accept();
            new MyListenWorker (sock).start(); // Uncomment to see shutdown bug:
            // try{Thread.sleep(10000);} catch(InterruptedException ex) {}
        }
    }
}

class MyListenWorker extends Thread {
    Socket sock;
    MyListenWorker (Socket s) {sock = s;}

    public void run(){
        // Here we are getting the information from our telnet client
        PrintStream out = null;
        BufferedReader in = null;
        try {
            out = new PrintStream(sock.getOutputStream());
            in = new BufferedReader
                    (new InputStreamReader(sock.getInputStream()));
            String sockdata;
            while (true) {
                sockdata = in.readLine ();
                if (sockdata != null)

                System.out.println(sockdata);
                System.out.flush ();
            }
            //sock.close(); // close this connection, but not the server;
        } catch (IOException x) {
            System.out.println("reseting connection! Listening again...");
        }
    }
}