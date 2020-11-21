import java.io.*;
import java.net.*;

//similar to the inet client code that we started with the semester
//I went and added version I have been using since starting the lab assignments
//this helps to remind me what the code is doing and what I am trying to do with the webserver
public class MyTelnetClient {

    public static void main (String args[]) {
        //from initial understanding telnet client is sending information to the browser (firefox)
        //then retrieving whatever information is on the browser
        //which in this case is the http code from WebAdd.html
        String mainServer;
        if (args.length < 1)
            mainServer = "localhost";
        else mainServer = args[0];

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        System.out.println("Keven Zarate's MyTelnet Client, 1.0.\n");
        System.out.println("Server: " + mainServer + " now running on Port: 2540");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            sock = new Socket(mainServer, 2540); // change this to 2540 if you want to talk to MyListener at localhost!
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            String dataToSend;
            do {
                System.out.print("Please enter a command for the server or <stop> to end process: ");
                System.out.flush ();
                dataToSend = in.readLine ();

                if (dataToSend.indexOf("stop") < 0){
                    toServer.println(dataToSend);
                    toServer.flush();
                }
            } while (dataToSend.indexOf("stop") < 0);
            for (int i = 1; i <=20; i++){
                textFromServer = fromServer.readLine();
                if (textFromServer != null) System.out.println(textFromServer);
            }
            sock.close();
        } catch (IOException x) {x.printStackTrace ();}
    }
}
