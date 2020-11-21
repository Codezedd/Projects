/*--------------------------------------------------------

1. Keven Zarate / Date: 9/27/2020

2. Java version used, if not the official version for the class:

e.g. build 1.8

3. Precise command-line compilation examples / instructions:

e.g.:

> javac JokeServer.java
> javac JokeClient.java
> javac JokeClientAdmin.java

4. Precise examples / instructions to run this program:

e.g.:

In separate shell windows:

> java JokeServer
> java JokeClient
> java JokeClientAdmin

All acceptable commands are displayed on the various consoles.

5. List of files needed for running the program.

e.g.:

 a. checklist.html
 b. JokeServer.java
 c. JokeClient.java
 d. JokeClientAdmin.java

5. Notes:
-I first went with establishing the basic setup and started to add the necessary requirements from the checklist
as best as possible.
-Checklist will have the exact requirements as stated by course syllabus.
-This is the most updated version for the necessary files for the Jokeserver, client, and client admin.
-It took me longer to get the basic layout for how I would be sending jokes and proverbs to the client.
-I was stuck on the actual data processing, but I do have a better understanding now and hopefully it continues to grow
by the end of the course.
 */


import java.io.*;
import java.net.*;

public class JokeClientAdmin {

    //setting up similar code from JokeClient to connection in main JokerServer admin code
    public static void main(String args[]) {
        String mainServer;

        if (args.length < 1) {
            mainServer = "localhost";
        } else {
            mainServer = args[0];
        }
        //telling the client that we are entering into chosen mode
        System.out.println("Entering : " + mainServer + " at port: 5050");

        //using the try loop to wait on client response
        //if user types "quit" ends process or presses <enter> to start in default "Joke Mode"
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {

            String clientInput;
            do {
                System.out.println("Press <Enter> to begin or quit to exit");
                System.out.flush();
                clientInput = in.readLine();
                //calling our command here with the client input and our local host as
                //mainServer. similar to our inetcode
                signalServer(clientInput, mainServer);

            } while (clientInput.indexOf("quit") < 0);
            System.out.println("Disconnected. Come back soon!");

        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //Here we are sending out input to the JokeServer
    static void signalServer(String clientCommand, String serverName) {
        Socket sock;
        BufferedReader fromJokeServer;
        PrintStream toJokeServer;
        String textFromJokeServer;
        //method signalServer takes the basis from the inet server for the joke server
        try {
            sock = new Socket(serverName, 5050);
            //taken from out previous Inet client code
            //connecting with our JokeServer
            fromJokeServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toJokeServer = new PrintStream(sock.getOutputStream());

            toJokeServer.println(clientCommand);
            toJokeServer.flush();
            //receiving the notice of when we are switching modes from the jokeserver
            textFromJokeServer = fromJokeServer.readLine();
            if (textFromJokeServer != null)
                System.out.println(textFromJokeServer);

            sock.close();
        } catch (IOException x) {
            System.out.println("Socket error.");

            x.printStackTrace();
        }
    }
}