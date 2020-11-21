/*--------------------------------------------------------
1. Name / Date:
Keven Zarate 11/4/2020
2. Java version used, if not the official version for the class:
    jdk1.8.0_181

3. Precise command-line compilation examples / instructions:
> javac -cp "gson-2.8.2.jar" BlockB.java

4. Precise examples / instructions to run this program:
In separate shell windows:
> java -cp ".;gson-2.8.2.jar" BlockB

5. List of files needed for running the program.

e.g.:
 a. checklist-block.html
 b. BlockB.java
 c. gson-2.8.2.jar
 d. BlockchainLog.txt
 e. blockrecordB.json

5. Notes:
part of mini projects in BlockDevStrategy. This is mini project B. 1-readme.txt has the full explanation of what
we are doing in BlockB.java

6.Credits
https://mkyong.com/java/how-to-parse-json-with-gson/
http://www.java2s.com/Code/Java/Security/SignatureSignAndVerify.htm
https://www.mkyong.com/java/java-digital-signatures-example/ (not so clear)
https://javadigest.wordpress.com/2012/08/26/rsa-encryption-example/
https://www.programcreek.com/java-api-examples/index.php?api=java.security.SecureRandom
https://www.mkyong.com/java/java-sha-hashing-example/
https://stackoverflow.com/questions/19818550/java-retrieve-the-actual-value-of-the-public-key-from-the-keypair-object
https://www.java67.com/2014/10/how-to-pad-numbers-with-leading-zeroes-in-Java-example.html
----------------------------------------------------------*/

//copy working code into mini project B

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.security.*;

//making a block record that contains data we use will use. based on utilities block record
class BlockDataB {
    //starting with some variables to deal with small data
    String BlockID;
    String VerificationProcessID;
    String PreviousHash;
    UUID uuid;
    String firstName;
    String lastName;
    String RandomSeed;
    String WinningHash;

    //getters and setters for setting up our block data b class
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

//1.starting with a simple basic blockchain
public class BlockB {
    //here we will put the three components that are necessary
    //for hashing before solving our puzzle
    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    public BlockB(String data, String previousHash) {
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
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
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

    //4.mining the blocks
    public void mine(int difficulty) {
        //this determines how hard the puzzle will be based on how many zeros are before a hash from my understanding
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = computeHash();
        }
        System.out.println("this block has been mined, hash is: " + hash);
    }


    //5. writing to disk as JSON format, taken from utilities program in BlockJ.java
    public static void WriteJSON() {
        System.out.println("======> In WriteJSON <======\n");

        UUID blockUUID = UUID.randomUUID();
        String BID = blockUUID.toString();
        System.out.println("Unique Block ID is: " + BID + "\n");

        //setting new block data
        BlockDataB blockrecord = new BlockDataB();
        //A.BlockID;
        blockrecord.setBlockID(BID);
        //B.VerificationProcessID;
        blockrecord.setVerificationProcessID("Process2");
        //C.uuid;
        blockrecord.setUuid(blockUUID);
        //D.firstName;
        blockrecord.setFirstName("Solid");
        //E.lastName;
        blockrecord.setLastName("Snake");
        //F.RandomSeed;
        Random rr = new Random();

       int randomval = rr.nextInt(16777215);
        String randSeed = Integer.toHexString(randomval);
        System.out.println("Our string random seed is: " + randSeed + "\n");

        blockrecord.setRandomSeed(randSeed);
        //G.With our data for the block collected we need to hash it for the hash value
        //putting it all in as string then hashing it in SHA-256 for our winning hash value
        String catRecord =
                blockrecord.getBlockID() +
                        blockrecord.getVerificationProcessID() +
                        blockrecord.getPreviousHash() +
                        blockrecord.getFirstName() +
                        blockrecord.getLastName() +
                        blockrecord.getRandomSeed();

        System.out.println("String blockRecord is: " + catRecord);

        String SHA256String = "";
        //similar to the computeSHA method but just hashing the string of the cat Record
        try {
            MessageDigest ourMD = MessageDigest.getInstance("SHA-256");
            ourMD.update(catRecord.getBytes());
            byte byteData[] = ourMD.digest();

            //similar to chaning bytes to hex
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            SHA256String = sb.toString();
        } catch (NoSuchAlgorithmException x) {
        }
        //set winning hash for block record
        blockrecord.setWinningHash(SHA256String);

        //7.just like we did in mini project A we will build JSON, but with added function
        //of writing to a JSON file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(blockrecord);

        //this shows what we put in the file in json
        System.out.println("The blockrecord in JSON is... " + json);

        //7A. this part we are writing the json to a file on disk
        try (FileWriter writer = new FileWriter("blockrecordB.json")) {
            //using file writer we create a file object then take the file object and
            // turn the file object to a equivalent json object
            gson.toJson(blockrecord, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //8. setting up our blockchain and difficulty level as public variables to be used in validatechain method
    public static ArrayList<BlockB> blockchain = new ArrayList<BlockB>();
    //the difficulty of mining the blocks, we will try a super easy number
    public static int difficultLevel = 1;

    //9. using our main method we can create the four nodes we need.
    //usually main method I would put in the first few lines,
    // but putting it near the bottom of the code makes more sense for blockchain,
    // also creating new blocks, mining, and adding
    //to the block chain are important. hashes weren't equal to each other
    // when I tried to create new blocks all together and mining them all together at the same time.
    public static void main(String[] args) {

        BlockB genesisBlock = new BlockB("Genesis Block!", "0");
        genesisBlock.mine(difficultLevel);
        blockchain.add(genesisBlock);

        BlockB firstBlock = new BlockB("first Block!", genesisBlock.hash);
        firstBlock.mine(difficultLevel);
        blockchain.add(firstBlock);

        BlockB secondBlock = new BlockB("second Block!", firstBlock.hash);
        secondBlock.mine(difficultLevel);
        blockchain.add(secondBlock);

        BlockB thirdBlock = new BlockB("third Block!", secondBlock.hash);
        thirdBlock.mine(difficultLevel);
        blockchain.add(thirdBlock);

        //NOTE: trying out the gson by printing the basic information we put into block
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);

        //trying out the validateChain method
        System.out.println("the chain is... " + validateChain());
        //trying out the write JSON method from utilities blockJ.java
        WriteJSON();
    }

    //10.following a basic blockchain setup, we can verify our blockchain to see if our current block previous hash is
    // the same as the last blocks hash
    public static Boolean validateChain() {
        BlockB currentBlock;
        BlockB previousBlock;
        //using the piece of code from mine method so when we compare hashes it is based on difficulty level of puzzle
        //and the value of the hash that was computed from the difficulty level.
        String target = new String(new char[difficultLevel]).replace('\0', '0');

        for (int j = 1; j < blockchain.size(); j++) {
            currentBlock = blockchain.get(j);//here is block in position 1 in list
            previousBlock = blockchain.get(j - 1);//here is block in position 0 in list
            //actual comparison is being done here
            if (!currentBlock.hash.equals(currentBlock.computeHash())) {
                System.out.println("Current Hashes are not equal!");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes are not equal!");
                return false;
            }
        }
        return true;
    }


}
