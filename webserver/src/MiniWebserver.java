/*
1. Keven Zarate / Date: 10/11/2020

2. Java version used, if not the official version for the class:

e.g. build 1.8.0_181

3. Precise command-line compilation examples / instructions:

e.g.:
> javac MiniWebserver.java

4. Precise examples / instructions to run this program:

e.g.:
> java MiniWebserver in separate terminal window

In separate firefox browser open WebAdd.HTML file

Fill out username of choice, one number of choice, and a second number of choice

then press submit button for user results for entered numbers

5. List of files needed for running the program.

e.g.:
MiniWebChecklist.html
MiniWebserver.java
WebAdd.html

6. Notes:
first time working with a webserver that could communicate with a html web browser.
it took me a while to understand that we are sending html formatted code through our web server to the
web browser itself and that's how we are displaying our results. It was pretty interesting that we could do that with
some lines of code. Favicon requests were an issue and I had to make some changes to get around that and with the help
of the discussion board I was able to finalize this version to work properly.

7.For the MiniWebserver assignment answer these questions briefly in YOUR OWN
WORDS here in your comments:

    1. How MIME-types are used to tell the browser what data is coming.
    some browsers handle this differently, but from my understanding the browser would first determine what kind of
    data the browser received. There are known MIME-types that the browser could compare to the incoming data, based on
    those results the browser can choose which MIME-type to use such as text, audio, message, video, or application
    being some of the default MIME-type

    2. How you would return the contents of requested files of type HTML
    (text/html)
    it would be similar to what we are doing in our MiniWebBrowser and our method addnums.
    we are taking in the web response from the web browser which is the GET response, taking the information from
    the response which would be the person, num1, and num2. In our case we can do a extraction of the values by placing
    it into a array of strings so that we can pick and choose from the array easily and compile it into whatever we want
    such as a string or adding the numbers together as per assignment instructions. To send it out as text/html we send
    out HTTP/1.1 200 OK, Content Length:, and Content Type: text/HTML so the browser can display it as a text/html.
    Since the MIME-type is recognized by the browser this should display our HTML code that we send after sending the
    necessary MIME-type to display our results correctly. There might be an easier way to process the GET response, but
    this is one way we did it for the response.

    3. How you would return the contents of requested files of type TEXT
    (text/plain)
    I believe it would be a similar process to what we did for HTML except we would have to ensure that our response to
    the web server only contains regular text with no binary values or information and that our content type be
    text/plain before our web server sends a response back in that type. Text/plain i just data that is human-readable
    content. CSS, CSV, or HTML would not be recognized since there are TEXT types for those kinds of information. Based
    on the documentation from Mozilla firefox on MIME-types and how they handle those MIME-types.
    https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types
 */


import java.io.*;
import java.net.*;

public class MiniWebserver {
    public static void main(String a[]) throws IOException {
        //establishing the queue length and port number we are using
        int queueLength = 6;
        //port number is where our WebbAdd.html is at
        int portNum = 2540;

        Socket socket;

        ServerSocket serverSock = new ServerSocket(portNum, queueLength);

        System.out.println("Keven Zarate's MiniWebserver active on port: " + portNum);
        while (true) {
            socket = serverSock.accept();
            new WebWorker(socket).start();
        }
    }
}


//class webworker is doing most of the processing for the web add html form
//first time writing a webserver, so I focused on getting initial setup to
//gather information from our web add form to our sockets in MiniWebserver
class WebWorker extends Thread {
    Socket socket;

    WebWorker(Socket s) {
        socket = s;
    }

    public void run() {
        PrintStream out = null;
        BufferedReader in = null;

        try {
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //1. getting information from WebbAdd.html with the "GET" and putting it into our webRequest string
            String webRequest = "";
            webRequest = in.readLine();
            //2. Print out what we got so it shows to us on the terminal
            System.out.println("Receiving data from WebbAdd form: ");
            System.out.println(webRequest);
            //3.favicon errors were persistent I tried the header and if statement to remove them
            //turns out the header did remove the favicon request and putting this if statement to only
            //do our addNums method when it got the GET response for the web add form was helpful.
            //header came from the initial web response code that was provided for the assignment

            if (webRequest.contains("GET /WebAdd.fake-cgi")) {
                System.out.println("Sending web response... ");
                addNums(webRequest, out);
            }
            System.out.flush();
            socket.close();

        } catch (IOException x) {
            System.out.println("Disconnected! Searching for connection...");
        }
    }

//----------------------------------------------
    // similar steps to what we did for jokeserver, declaring a separate method addnums() as per instructions
    // below is what we are working with when we used MyListener.java and using web add form
    // the GET method is what we need to parse into strings and get the Yourname, num1, and num2
    //    GET /WebAdd.fake-cgi?person=YourName&num1=4&num2=5 HTTP/1.1
    //    Host: localhost:2540
    //    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0
    //    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
    //        Accept-Language: en-US,en;q=0.5
    //        Accept-Encoding: gzip, deflate
    //        Connection: keep-alive
    //        Upgrade-Insecure-Requests: 1

    public void addNums(String webRequest, PrintStream sendToWeb) {
        //here we are just extracting the string we want which is:
        //person=YourName&num1=4&num2=5 as an example
        //the second variable for substring just removes the end of the get response
        //so we are only working with the variables we want
        String extractString = webRequest.substring(21, webRequest.length() - 9);

        //splitting here required a bit more research to see how it works
        //this uses a set to specify which characters we want to separate by
        //https://www.geeksforgeeks.org/split-string-java-examples/ -credit
        String[] extractArray = extractString.split("[=&]");


        //1.getting our name from the query string
        String webPerson = extractArray[1];
        //2.collecting first number here
        String webNum1 = extractArray[3];
        //3.finally collecting our second number
        String webNum2 = extractArray[5];
        //4.adding the numbers together
        int sum = Integer.parseInt(webNum1) + Integer.parseInt(webNum2);
        //5.putting together our web response for the web add form as described in the assignment instructions
        String webAddResponse = "Dear " + webPerson + "," + " the sum of " + webNum1 + " and " + webNum2 + " is " + sum;

        sendToWeb.print("HTTP/1.1 200 OK");
        sendToWeb.print("Content Length: " + webAddResponse.length());
        sendToWeb.print("Content-type: text/html \r\n\r\n");

        //6.from the checklist we are sending back a new copy of the form for the user. I figured that we would just
        //send multiple out.print statements to get the form HTML code back to the browser
        sendToWeb.print("<HTML>");
        sendToWeb.print("<HEAD>");
        sendToWeb.print("<link rel=\"icon\" href=\"data:,\">");
        sendToWeb.print("</HEAD>");
        sendToWeb.print("<BODY>");
        sendToWeb.print("<H1> WebAdd </H1>");
        sendToWeb.print("<FORM method=\"GET\" action=\"http://localhost:2540/WebAdd.fake-cgi\">");
        sendToWeb.print("<INPUT TYPE=\"text\" NAME=\"person\" size=20 value=\"YourName\"><P>");
        sendToWeb.print("<INPUT TYPE=\"text\" NAME=\"num1\" size=5 value=\"4\"> <br>");
        sendToWeb.print("<INPUT TYPE=\"text\" NAME=\"num2\" size=5 value=\"5\"> <p>");
        sendToWeb.print("<INPUT TYPE=\"submit\" VALUE=\"Submit Numbers\">");
        //our response is here in the sea of out.print statements
        sendToWeb.print("<p>" + webAddResponse + "</p>");
        sendToWeb.print("</FORM>");
        sendToWeb.print("</BODY>");
        sendToWeb.print("</HTML");

    }
}
/*
<HTML>
<head>
<link rel="icon" href="data:,">

</head>
<BODY>
<H1> WebAdd </H1>

<FORM method="GET" action="http://localhost:2540/WebAdd.fake-cgi">

Enter your name and two numbers. My program will return the sum:<p>

<INPUT TYPE="text" NAME="person" size=20 value="YourName"><P>

<INPUT TYPE="text" NAME="num1" size=5 value="4"> <br>
<INPUT TYPE="text" NAME="num2" size=5 value="5"> <p>

<INPUT TYPE="submit" VALUE="Submit Numbers">

</FORM>
</BODY>
</HTML>

 */