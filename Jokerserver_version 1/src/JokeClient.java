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
import java.util.*;

public class JokeClient {
    public static void main(String args[]) {
        //Port used for Joke server and client is 4545
        int portNumber = 4545;

        //using same code from inet to use the localhost as default ip address
        String mainServer;

        if (args.length < 1) {
            mainServer = "localhost";
        } else {
            mainServer = args[0];
        }

        //Same as InetClient, telling client that it is running
        System.out.println("Keven Zarate's Joke Client, 1.8.\n");
        System.out.println("Using: " + mainServer + ", at PORT: " + portNumber);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //starting the connection in try
        try {
            //1.Asking user for their username of choice before loop
            System.out.println("Hello! Please enter your username: ");

            String userName;
            System.out.flush();
            userName = in.readLine();
            //1B. creating UUID for the username that the was entered
            //new variable to get a unique identifer for each client
            System.out.flush();
            String userID = UUID.randomUUID().toString();


            //2.variable to keep track of the users <Enter> command press
            //enter command equals this : ""
            String userInput;

            //2.Inside of loop <Enter> signals the connection to our server default mode is "Joke Mode"
            do {
                System.out.println("Please press <Enter> to for a okay Joke or okay Proverb");
                //2A. once connected to the server it will display Joke as default
                System.out.flush();
                userInput = in.readLine();
                System.out.flush();
                if (userInput.indexOf("quit") < 0)
                    //changed variable name as reminder that we are sending
                    //initial user inputs to the Joker server
                    connectToServer(mainServer, userID, userName, userInput);

            } while (userInput.indexOf("quit") < 0);
            System.out.println("Disconnected by user. Thank You! Come back soon!");
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //Socket in this static method is necessary for the client to attempt a line of communication to the server
    //using the same variable name helped me to keep track of what information is going where
    static void connectToServer(String serverName, String userID, String userName, String userInput) {
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String resultsFromServer;

        try {
            //again setting the sock connection to connect to jokeserver on port 4545
            sock = new Socket(serverName, 4545);
            //refresher: inputstream is reading info from the server
            // while outputstream is writing info to the server
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            //here we send out initial information from the client
            //which would be: , user UUID, username, and user input for the server to process
            toServer.println(userID);
            toServer.println(userName);
            toServer.println(userInput);
            toServer.flush();

            //had to modify this read in from server
            //as to not limit the amount of jokes that being sent to the client
            //and to print out the complete cycle when the conditions are met

            for (int i = 1; i <= 5; i++) {
                resultsFromServer = fromServer.readLine();
                if (resultsFromServer != null)
                    System.out.println(resultsFromServer);
            }
            sock.close();
        } catch (IOException x) {
            System.out.println("Socket error.");

            x.printStackTrace();
        }
    }
}
