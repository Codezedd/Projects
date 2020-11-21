/*--------------------------------------------------------
1. Name / Date:
Keven Zarate 11/4/2020
2. Java version used, if not the official version for the class:
    jdk1.8.0_181

3. Precise command-line compilation examples / instructions:
> javac -cp "gson-2.8.2.jar" BlockC.java

4. Precise examples / instructions to run this program:
> java -cp ".;gson-2.8.2.jar" BlockC
*once at the second input command you can select between BlockInput0.txt to BlockInput2.txt
*Examples: > java -cp ".;gson-2.8.2.jar" BlockC 0 or > java -cp ".;gson-2.8.2.jar" BlockC 2

> java -cp ".;gson-2.8.2.jar" BlockC 1

5. List of files needed for running the program.
 a. checklist-block.html
 b. BlockC.java
 c. gson-2.8.2.jar
 d. BlockchainLog.txt
 e. blockrecordC.json
 f. BlockInput0.txt
 g. BlockInput1.txt
 h. BlockInput2.txt

5. Notes:
part of mini projects in BlockDevStrategy. This is mini project C. 2-readme.txt has the full explanation of what
we are doing in BlockC.java

6.Credits
Reading lines and tokens from a file:
http://www.fredosaurus.com/notes-java/data/strings/96string_examples/example_stringToArray.html
Good explanation of linked lists:
https://beginnersbook.com/2013/12/linkedlist-in-java-with-example/
Priority queue:
https://www.javacodegeeks.com/2013/07/java-priority-queue-priorityqueue-example.html

https://mkyong.com/java/how-to-parse-json-with-gson/
http://www.java2s.com/Code/Java/Security/SignatureSignAndVerify.htm
https://www.mkyong.com/java/java-digital-signatures-example/ (not so clear)
https://javadigest.wordpress.com/2012/08/26/rsa-encryption-example/
https://www.programcreek.com/java-api-examples/index.php?api=java.security.SecureRandom
https://www.mkyong.com/java/java-sha-hashing-example/
https://stackoverflow.com/questions/19818550/java-retrieve-the-actual-value-of-the-public-key-from-the-keypair-object
https://www.java67.com/2014/10/how-to-pad-numbers-with-leading-zeroes-in-Java-example.html
----------------------------------------------------------*/

//copy working code into mini project C

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.security.*;

//making a block record that contains data we use will use. based on utilities block record
class BlockDataC {
    //starting with some more variables that we will find in our BlockInput.txt files
    String BlockID;
    String VerificationProcessID;
    String PreviousHash;
    UUID uuid;
    String firstName;
    String lastName;
    String SSNum;
    String DOB;
    String Diag;
    String Treat;
    String Rx;
    String RandomSeed;
    String WinningHash;

    //getters and setters for setting up our block data class
    public String getBlockID() {
        return BlockID;
    }

    public void setBlockID(String blockID) {
        this.BlockID = blockID;
    }

    public String getVerificationProcessID() {
        return VerificationProcessID;
    }

    public void setVerificationProcessID(String verificationProcessID) {
        this.VerificationProcessID = verificationProcessID;
    }

    public String getPreviousHash() {
        return this.PreviousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.PreviousHash = previousHash;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSSNum() {
        return SSNum;
    }

    public void setSSNum(String SSNum) {
        this.SSNum = SSNum;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDiag() {
        return Diag;
    }

    public void setDiag(String diag) {
        this.Diag = diag;
    }

    public String getTreat() {
        return Treat;
    }

    public void setTreat(String treat) {
        this.Treat = treat;
    }

    public String getRx() {
        return Rx;
    }

    public void setRx(String rx) {
        this.Rx = rx;
    }

    public String getRandomSeed() {
        return RandomSeed;
    }

    public void setRandomSeed(String randomSeed) {
        this.RandomSeed = randomSeed;
    }

    public String getWinningHash() {
        return WinningHash;
    }

    public void setWinningHash(String winningHash) {
        this.WinningHash = winningHash;
    }
}

public class BlockC {
    //here we will put the three components that are necessary
    //for hashing before solving our puzzle
    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    //setting up the file we want to get for putting into JSON and
    //ultimately into our blocks in the block chain
    private static String FILENAME;
    //Also putting variables for when we read each line of the file and
    //place each data in its own variable
    private static final int iFNAME = 0;
    private static final int iLNAME = 1;
    private static final int iDOB = 2;
    private static final int iSSNUM = 3;
    private static final int iDIAG = 4;
    private static final int iTREAT = 5;
    private static final int iRX = 6;


    public BlockC(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = computeHash();
    }

    //2.next step in simple blockchain is cryptographic algorithm or SHA256
    // code from class D2L website and
    // https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
    public String computeSha(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);

                if (hex.length() == 1) {
                    hexString.append('0');
                    hexString.append(hex);
                }
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //3. using the computeSha method we can now calculate the hash with our three components and nonce for mining
    public String computeHash() {
        String computedHash = computeSha(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
        return computedHash;
    }

    //4.adding additional components to WriteJSON method
    public void WriteJSON(String args[]) throws Exception {
        LinkedList<BlockDataC> blockRecords = new LinkedList<BlockDataC>();

        //4A. setting variable for processNum for when we process BlockInput.txt files
        int processNum;

        //4B. if statement to determine which text file we want to process
        //processNum determines if it is BlockInput.txt file 0, 1, or 2
        if (args.length < 1) {
            processNum = 0;
        } else if (args[0].equals("0")) {
            processNum = 0;
        } else if (args[0].equals("1")) {
            processNum = 1;
        } else if (args[0].equals("2")) {
            processNum = 2;
        } else {
            //in last case the text file will resort to BlockInput0.txt
            processNum = 0;
        }
        //4C.Switch case to process the processNum variable after if statement
        switch (processNum) {
            case 1:
                FILENAME = "BlockInput1.txt";
                break;
            case 2:
                FILENAME = "BlockInput2.txt";
                break;
            default:
                FILENAME = "BlockInput0.txt";
                break;
        }
        System.out.println("You have selected file..." + FILENAME);
        //4D. here based on the WriteJSON method we added additional file read in functionality
        //once JSON file is created from the BlockInput.txt files we can then work on readJSON method
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String[] tokens = new String[10];
            String InputLineStr;

            UUID blockUUID;
            String BID;

            while ((InputLineStr = br.readLine()) != null) {
                //A.setting new block data
                BlockDataC blockrecord = new BlockDataC();

                blockUUID = UUID.randomUUID();

                BID = blockUUID.toString();
                //A1. using string array to put in our data into manageable tokens
                tokens = InputLineStr.split(" +");
                //B.getting the values from the text file and placing it into our blockrecord
                //C.BlockID;
                blockrecord.setBlockID(BID);
                //D.VerificationProcessID;
                String VPID = String.valueOf(processNum);
                blockrecord.setVerificationProcessID(VPID);
                //E.uuid;
                blockrecord.setUuid(blockUUID);
                //F.firstName;
                blockrecord.setFirstName(tokens[iFNAME]);
                //G.lastName;
                blockrecord.setLastName(tokens[iLNAME]);
                //H. Social Security Number
                blockrecord.setSSNum(tokens[iSSNUM]);
                //I.DOB
                blockrecord.setDOB(tokens[iDOB]);
                //J.Diagnosis
                blockrecord.setDiag(tokens[iDIAG]);
                //K.Treatment
                blockrecord.setTreat(tokens[iTREAT]);
                //L.Rx
                blockrecord.setRx(tokens[iRX]);
                //M.RandomSeed;
                Random rr = new Random();

                int randomval = rr.nextInt(16777215);
                String randSeed = Integer.toHexString(randomval);
                blockrecord.setRandomSeed(randSeed);
                //N. most important dont forget to add to Linked List
                blockRecords.add(blockrecord);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        System.out.println("======> In WriteJSON <======\n");

        //7.just like we did in mini project A we will build JSON, but with added function
        //of writing to a JSON file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(blockRecords);

        //this shows what we put in the file in json
        System.out.println("The blockrecord in JSON is... " + json);

        //7A. this part we are writing the json to a file on disk
        try (FileWriter writer = new FileWriter("blockrecordC.json")) {
            //using file writer we create a file object then take the file object and
            // turn the file object to a equivalent json object
            gson.toJson(blockRecords, writer);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void ReadJSON() {
        System.out.println("\n=========> In ReadJSON <=========\n");

        Gson gson = new Gson();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("blockrecordC.json"));
            //since I was dealing with multiple items and essentially created an array of arrays with each block
            //record I now have to setup a String that can print out the data within the Block record array.
            // Read and convert JSON File to a Java Object:
            ArrayList<BlockDataC> blockRecordIn = gson.fromJson(reader, new TypeToken<ArrayList<BlockDataC>>() {
            }.getType());

            // Print the blockRecord
            for (BlockDataC getRecords : blockRecordIn) {
                //here we can customize what we want to print from the block records using variables
                //we sent in BlockDataC
                System.out.println("Name: " + getRecords.firstName + " " + getRecords.lastName);
                System.out.println("Diagnosis: " + getRecords.Diag + " " + "Treatment: " + getRecords.Treat);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BlockC(String argv[]) {
        System.out.println("Prepping files to be read in...");
    }

    public void run(String argv[]) {
        //only a few times I have dealt with an argument as a value for a method to use
        // at first I was not sure if using the try method would work, but based on the utility code
        //I just used a try method to see how it works and to later incorporate the ReadJSON(); method in the run method
        //as well
        System.out.println("Running process now....\n");
        try {
            WriteJSON(argv);

        } catch (Exception x) {
        }

        ReadJSON();

    }

    public static void main(String argv[]) {
        BlockC process = new BlockC(argv);
        process.run(argv);


    }
}
