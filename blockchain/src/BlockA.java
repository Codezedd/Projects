/*--------------------------------------------------------
1. Name / Date:
Keven Zarate 11/4/2020
2. Java version used, if not the official version for the class:
    jdk1.8.0_181

3. Precise command-line compilation examples / instructions:
> javac -cp "gson-2.8.2.jar" BlockA.java

4. Precise examples / instructions to run this program:
In separate shell windows:
> java -cp ".;gson-2.8.2.jar" BlockA

5. List of files needed for running the program.

e.g.:
 a. checklist-block.html
 b. BlockA.java
 c.gson-2.8.2.jar

5. Notes:
part of mini projects in BlockDevStrategy. This is mini project A. 0-readme.txt has the full explanation of what
we are doing in BlockA.java

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.security.*;

//making a block record that contains data we use will use. based on utilities block record
class BlockData {
    //starting with some variables to deal with small data first before adding additional information we will require
    //later on
    String BlockID;
    UUID uuid;
    String firstName;
    String lastName;

    public String getBlockID() {
        return BlockID;
    }

    public void setBlockID(String blockID) {
        this.BlockID = blockID;
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
}


//1.starting with a simple basic blockchain
public class BlockA {
    //here we will put the three components that are necessary
    //for hashing before solving our puzzle
    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    public BlockA(String data, String previousHash) {
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
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

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

    //3A.mining the blocks
    public void mine(int difficulty) {
        //this determines how hard the puzzle will be based on how many zeros are before a hash from my understanding
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = computeHash();
        }
        System.out.println("this block has been mined, hash is: " + hash);
    }


    //4. setting up our blockchain and difficulty level as public variables to be used in validatechain method
    public static ArrayList<BlockA> blockchain = new ArrayList<BlockA>();
    //the difficulty of mining the blocks, we will try a super easy number
    public static int difficultLevel = 1;

    //5. using our main method we can create the four nodes we need.
    //usually main method I would put in the first few lines,
    // but putting it near the bottom of the code makes more sense for blockchain,
    // also creating new blocks, mining, and adding
    //to the block chain are important. hashes weren't equal to each other
    // when I tried to create new blocks all together and mining them all together at the same time.
    public static void main(String[] args) {
        //a bit messy setting up here, but trying out the block record format
        //before attempting json and writing to disk in mini project B
        BlockData blockrecord = new BlockData();

        UUID blockUUID = UUID.randomUUID();
        String BID = UUID.randomUUID().toString();


        blockrecord.setBlockID(BID);
        blockrecord.setUuid(blockUUID);
        blockrecord.setFirstName("Solid");
        blockrecord.setLastName("Snake");
        String blockrecordString =
                blockrecord.getBlockID() +
                        blockrecord.getUuid() +
                        blockrecord.getFirstName() +
                        blockrecord.getLastName();
        //trying out gson format for our sample data block record
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String blockrecordJson = gson.toJson(blockrecordString);

        BlockA genesisBlock = new BlockA(blockrecordString, "0");
        genesisBlock.mine(difficultLevel);
        blockchain.add(genesisBlock);
        //printing what the blockrecord data is in json
        System.out.println("Genesis Block data... " + blockrecordJson);

        BlockA firstBlock = new BlockA(blockrecordString, genesisBlock.hash);
        firstBlock.mine(difficultLevel);
        blockchain.add(firstBlock);

        BlockA secondBlock = new BlockA(blockrecordString, firstBlock.hash);
        secondBlock.mine(difficultLevel);
        blockchain.add(secondBlock);

        BlockA thirdBlock = new BlockA(blockrecordString, secondBlock.hash);
        thirdBlock.mine(difficultLevel);
        blockchain.add(thirdBlock);

        //NOTE: trying out the gson by printing the basic information we put into block
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
        //trying out the validateChain method
        System.out.println("the chain is... " + validateChain());
    }

    //6.following a basic blockchain setup, we can verify our blockchain to see if our current block previous hash is
    // the same as the last blocks hash
    public static Boolean validateChain() {
        BlockA currentBlock;
        BlockA previousBlock;
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


