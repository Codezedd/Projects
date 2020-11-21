/*--------------------------------------------------------
1. Name / Date:
Keven Zarate 11/4/2020
2. Java version used, if not the official version for the class:
    jdk1.8.0_181

3. Precise command-line compilation examples / instructions:
> javac -cp "gson-2.8.2.jar" BlockD.java

4. Precise examples / instructions to run this program:
> java -cp ".;gson-2.8.2.jar" BlockD
*once at the second input command you can select between BlockInput0.txt to BlockInput2.txt
*Examples: > java -cp ".;gson-2.8.2.jar" BlockD 0 or > java -cp ".;gson-2.8.2.jar" BlockD 2

> java -cp ".;gson-2.8.2.jar" BlockD 1

5. List of files needed for running the program.
 a. checklist-block.html
 b. BlockD.java
 c. gson-2.8.2.jar
 d. BlockchainLog.txt
 e. BlockInput0.txt
 f. BlockInput1.txt
 g. BlockInput2.txt

5. Notes:
part of mini projects in BlockDevStrategy. This is mini project D. 3-readme.txt has the full explanation of what
we are doing in BlockD.java

6.Credits
Reading lines and tokens from a file:
http://www.fredosaurus.com/notes-java/data/strings/96string_examples/example_stringToArray.html
Good explanation of linked lists:
https://beginnersbook.com/2013/12/linkedlist-in-java-with-example/
Priority queue:
https://www.javacodegeeks.com/2013/07/java-priority-queue-priorityqueue-example.html
----------------------------------------------------------*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BlockD {

    private static String message;

    //1.setting basic method to accept a command line argument for a process ID
    //for this part of the miniproject I had to move on from part C to work on D first.
    //I think working with the arguments and sending messages was helpful to understand
    //how it works with the blockinput.txt files and what I was trying to accomplish with the miniproject C.
    //moving on the to more simpler goal was helpful to figure out what I was doing wrong in miniproject C.
    public static void sendMessage(String args[]) {
        //1A. using if statement to determine next action based on user input
        //this variable will tell us what process ID is being used
        int processID;

    //1B. if statement that tells the difference between user inputs and
    //puts it into variable that stores the response
    if (args.length < 1) {
        processID = 0;
    } else if (args[0].equals("0")) {
        processID = 0;
    } else if (args[0].equals("1")) {
        processID = 1;
    } else if (args[0].equals("2")) {
        processID = 2;
    } else {
        //Default choice will be 0
        processID = 0;
    }
    //2.Switch case based on utilities code will actually print out the message we want based on
    // the actual argument input that was provided. This was pretty helpful when going back to do the miniproject C
    //I wasnt sure how I could input the right information to get the blockinput.txt files, but using the switch statment
    //helped to use the argument to pick the written out txt file instead of trying to combine the number into a string
    // to get our txt file. Example: doing this filename = "blockinput.txt" was easier that doing this:
    //string filename = "blockinput" + argumentvalueNum + ".txt"
    switch (processID) {
        case 1:
            message = "Hello from Process 1";
            break;
        case 2:
            message = "Hello from Process 2";
            break;
        default:
            message = "Hello from Process 0";
            break;
    }

    //3. printing out the results of the process ID
    System.out.println(message);

    }

    //4. setting main method for the sendMessage method when we input our argument value form the console or terminal
    public static void main(String argv[])  {
        sendMessage(argv);
    }
}
