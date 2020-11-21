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


public class JokeServer {
    //set variable for default server mode is JOKE
    public static String serverMode = "Joke";

    public static void main(String a[]) throws IOException {
        int queue_length = 6;
        Socket sock;

        //port number changed to 4545 for Jokeserver
        int defaultPortNum = 4545;
        //started our connection
        ServerSocket serv_sock = new ServerSocket(defaultPortNum, queue_length);
        System.out.println("Connected to Joker Server: starting in Joke Mode");

        //Additional AdminLooper code added as seen in the assignment webpage
        AdminLooper AL = new AdminLooper();
        Thread newThread = new Thread(AL);
        newThread.start();


        System.out.println("Keven Zarate's Joke Server 1.8 is starting, listening at PORT: " + defaultPortNum + ".\n");
        //once connected the worker thread will start
        while (true) {
            sock = serv_sock.accept();
            new Worker(sock).start();
        }
    }
}

//getting information from our JokerClientAdmin
//using code from assignment page
class AdminLooper implements Runnable {
    public static boolean adminControlSwitch = true;

    public void run() {
        System.out.println("In Admin Looper thread");
        int queue_length = 6;
        int port_num = 5050;
        Socket sock;
        //Here we are communicating with the clientadmin
        //necessary for the server mode switches
        try {
            ServerSocket servSock = new ServerSocket(port_num, queue_length);
            while (adminControlSwitch) {
                sock = servSock.accept();
                new AdminWorker(sock).start();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}

//added additional adminworker code from assignment page
class AdminWorker extends Thread {
    Socket sock;

    AdminWorker(Socket s) {
        sock = s;
    }

    public void run() {
        PrintStream out = null;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());
            try {
                //here we are using similar code to what was established in jokerClientAdmin.java
                String clientInput;
                clientInput = in.readLine();

                if (clientInput.equals("")) {
                    //default mode is already joke, so on first enter it should switch to proverb mode
                    //this what came to mind without being overwhelmed on how to switch modes
                    if (JokeServer.serverMode.equals("Joke")) {
                        JokeServer.serverMode = "Proverb";
                        System.out.println("Transferring mode to:" + JokeServer.serverMode);
                        out.println("Now transferring mode to: Proverb ");
                    } else {
                        JokeServer.serverMode = "Joke";
                        System.out.println("Transferring mode to:" + JokeServer.serverMode);
                        out.println("Now transferring mode to: Joke ");
                    }
                }
            } catch (IOException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}


//------------------------------------------------
//here following the pseudo code provided on the assignment webpage was helpful to
//place most of the joke/proverb handling in the worker class.
//the information sent by the client will be handled here.
class Worker extends Thread {
    Socket sock;

    Worker(Socket s) {
        sock = s;
    }

    //keeping linked lists for our jokes and proverbs
    //also keeping it accessible to all methods I might implement for the worker class
    //has convenient method to shuffle the jokes/proverbs already
    List<String> jokeList = new ArrayList<String>();
    List<String> proverbList = new ArrayList<String>();
    //trying the hashmap to store our UUID and username for record tracking
    Map<String, String> clientDataBase = new HashMap<>();


    public void run() {
        PrintStream out = null;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());

            try {
                //here we are receiving the information sent by the client
                //we process the data in the worker class, using same naming scheme so I don't forget
                //what data inputs we are receiving

                String userID = in.readLine();
                String userName = in.readLine();
                String userInput = in.readLine();

                //1.Here we are storing the userID and username to keep track of new users
                //1A. As well as checking if the hashmap has the user already
                //NOTE: I was not sure how to call for the index of the joke position to be placed here when
                //retrieving usernames, to the best of my abilities this is what I got for the given time

               //Updated Note: was not sure how to contain the values with it endlessly adding new user
              // if might be because of the format of the code not allowing continuous processing when sending new jokes
//                if (!clientDataBase.containsValue(userName)) {
//                    System.out.println("Adding new user to the database!");
//                    clientDataBase.put(userID, userName);
//                } else if (clientDataBase.containsValue(userName)) {
//                    System.out.println("Username found!");
//                }



                //2. Based on user input from the client and the current mode
                // we will either send out the jokes or proverbs
                if (userInput.equals("")) {
                    //2A. create the joke/proverb with username and send it out to client
                    if (JokeServer.serverMode.equals("Joke")) {

                        System.out.println("Prepping Jokes for: " + userName);
                        compileJoke(userName, out);
                    } else {
                        //similar process here where I decided to implement the out in the actual method
                        System.out.println("Prepping Proverbs for: " + userName);
                        compileProverb(userName, out);
                    }
                }
            } catch (IOException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            //this method closes the socket connection
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }


    //------------------------
    //trying to make a compact method to process the information from the client
    //just to enable use to call the method in the worker class
    //I included the printsream out as well since I was having issue with returns when
    //setting the method to return a string

    private void compileJoke(String userName, PrintStream prepForClient) {
        //jokes are from https://www.reddit.com/r/AskReddit/comments/1y26b3/what_is_the_shortest_very_funny_joke_you_know/
        //Batman & Robin(1997), and Her(2013)

        //we have our arraylist here that can be randomized conveniently can be randomized
        jokeList.add("JA " + userName + ": What killed the dinosaurs? The ice age! ");
        jokeList.add("JB " + userName + ": What does a baby computer call its father? Data");
        jokeList.add("JC " + userName + ": Geologist never take rocks for granite");
        jokeList.add("JD " + userName + ": Time flies like an arrow, fruit flies like a banana.");

        Collections.shuffle(jokeList);
        //List was giving out of bounds exception, changing to arrays cleared that up for some reason

        String[] jokeArray = new String[jokeList.size()];
        jokeArray = jokeList.toArray(jokeArray);
        //separate count to determine when the last joke has been sent to the client
        int count = 0;
        while(true) {
            for (int i = 0; i < jokeArray.length; i++) {
                prepForClient.println(jokeArray[i]);
                count++;
            }
            if (count == 4) {
                prepForClient.println("Joke cycle complete!");
            }
        }
    }

    private void compileProverb(String userName, PrintStream prepForClient) {
        //similar process to the compile joke method
        //proverbs from https://www.reddit.com/r/AskReddit/comments/2nyrld/hey_reddit_what_is_your_favorite_proverbsaying/
        proverbList.add("PA " + userName + ": He who knows that enough is enough will always have enough.");
        proverbList.add("PB " + userName + ": In wine there is wisdom, in beer there is freedom, in water there is bacteria.");
        proverbList.add("PC " + userName + ": Don't cry because it's over, smile because it happened.");
        proverbList.add("PD " + userName + ": The absence of evidence is not the evidence of absence.");

        Collections.shuffle(proverbList);

        //List was giving out of bounds exception, changing to arrays cleared that up for some reason

        String[] proverbArray = new String[proverbList.size()];
        proverbArray = proverbList.toArray(proverbArray);
        //separate count to determine when the last joke has been sent to the client
        int count = 0;

        for (int i = 0; i < proverbArray.length; i++) {
            prepForClient.println(proverbArray[i]);
            count++;
        }
        if (count == 4) {
            prepForClient.println("Proverb cycle complete!");
        }
    }
}
