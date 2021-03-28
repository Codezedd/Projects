
//NOTE: this is a implementation of the simple DES algorithm as discussed in the textbook
//Introduction to Cryptography with Coding Theory 3RD edition
//With the ability to run in a console and deciding to either do 4 rounds of encryption or decryption
//based on user input.
public class SDES {
//1)Here we will be using the code we established from SDES part 1
//now updated to the version that can use the appropriated range of bits correctly

    //2) our expand method
    public static int expand(int number) {
        int expandRight = number & 0x3;
        int expandRightOne = expandRight | ((number & 0xC) << 1);
        int expandRightTwo = expandRightOne | ((number & 0x30) << 2);
        int expandRightThree = expandRightTwo | ((number & 0x4) << 3);
        int expandRightFour = expandRightThree | ((number & 0x8) >> 1);

        return expandRightFour;
    }

    /*
    3)Short summary of encryptRound method:
     We are implementing the simple DES as described in the textbook.
     Each round will call our encryptRound method, since we are doing 4 rounds
     this method will be called for each new sub key we are using based on the
     i round.
     */
    public static int encryptRound(int pt, int k) {
        Integer[][] sBoxOne = {
                {5, 2, 1, 6, 3, 4, 7, 0},
                {1, 4, 6, 2, 0, 7, 5, 3}
        };

        Integer[][] sBoxTwo = {
                {4, 0, 6, 5, 7, 1, 3, 2},
                {5, 3, 0, 7, 6, 2, 1, 4}
        };

        int leftZero = (pt & 0xFC0) >> 6;
        int rightZero = pt & 0x3F;

        int expandedF = expand(rightZero) ^ k;

        int leftFour = (expandedF & 0xF0) >> 4;
        int rightFour = expandedF & 0xF;

        int sBoxOneLeft = sBoxOne[(leftFour & 0x8) >> 3][leftFour & 0x7];

        int sBoxTwoRight = sBoxTwo[(rightFour & 0x8) >> 3][rightFour & 0x7];

        Integer functionOutput = (sBoxOneLeft << 3) | sBoxTwoRight;

        int leftFinalOutput = (rightZero << 6);

        int rightFinalOutput = (leftZero ^ functionOutput);

        int sdesRoundOutput = leftFinalOutput | rightFinalOutput;

        return sdesRoundOutput;
    }

    //4)the shifting keys was a bit confusing at first when I tried to implement
    //it within the encrypt method. Based on the requirements it probably would be best
    //to implement a separate key shift method to call in our encrypt(plaintext, key) method
    //especially after breaking out the pen and paper to do it manually first..
    public static int shiftKey(int currentKey, int currentRound) {
        if (currentRound == 1) {
            return (currentKey >> 1) & 0xFF;

        } else {
            int k1 = (currentKey << (currentRound - 2)) & 0xFF;

            return k1 | (currentKey >> (11 - currentRound));
        }
    }

    //5) Here we are doing a separate encrypt method as stated in the assignment requirements
    public static int encrypt(int plaintext, int key) {
        //5A) storing our keys that we will be using for the encryption and using for loop to calculate keys
        //ahead of time.
        int[] keys = new int[4];

        for (int i = 0; i < 4; i++) {
            int roundKey = shiftKey(key, i + 1);
            keys[i] = roundKey;
        }
        //5B) here I found it was easier to store the results of each round for the next
        //During my initial debug of the program, I had everything in a for loop and found that I was not using
        //the output from the first round of encryption as an input for the second etc...
        //that's why I was having the wrong values appearing. I am sure there is a better way to implement this.
        int roundOne = encryptRound(plaintext, keys[0]);
        int roundTwo = encryptRound(roundOne, keys[1]);
        int roundThree = encryptRound(roundTwo, keys[2]);
        int roundFour = encryptRound(roundThree, keys[3]);

        return roundFour;
    }

    //6) here we are implementing our decrypt method.
    //based on decryption for SDES in the textbook
    public static int decrypt(int ciphertext, int key) {
        //7) here we are starting from the key scheduled for round 4 to round 1
        //Since the decryption method starts with flipping Ln and Rn from the ciphertext
        //we will implement a swap on our ciphertext input
        int swapCT = (ciphertext & 0x3F) << 6;
        int swapFinalCT = swapCT | ((ciphertext & 0xFC0) >> 6);

        //7A) doing similar process to what we did in encrypt method for our keys
        int[] decryptKeys = new int[4];
        for (int i = 0; i < 4; i++) {
            int roundKey = shiftKey(key, i + 1);
            decryptKeys[i] = roundKey;
        }
        //8) we are doing the same process as we did in the encrypt method
        //except working in reverse by processing each round starting from round 4/ key 4
        int roundFour = encryptRound(swapFinalCT, decryptKeys[3]);
        int roundThree = encryptRound(roundFour, decryptKeys[2]);
        int roundTwo = encryptRound(roundThree, decryptKeys[1]);
        int roundOne = encryptRound(roundTwo, decryptKeys[0]);

        //9)once we do the final decrypt round to get R0 L0 we must swap
        //this result again to get the original plain text L0 R0
        int swapPT = (roundOne & 0x3F) << 6;
        int swapFinalPT = swapPT | ((roundOne & 0xFC0) >> 6);

        return swapFinalPT;
    }


    public static void main(String[] args) {
        //Test numbers: (0,85) = 1097 (010001001001)
        //              (4095, 255) = 1793 (011100000001)
        //since we our user inputs is taken as a string first
        //we convert that string to a character to be used with out if statement
        String userInput = args[0];
        char userChar = userInput.charAt(0);

        //Using integer.parseInt to accept terminal inputs
        int plaintext = Integer.parseInt(args[1]);
        int key = Integer.parseInt(args[2]);

        //based on user input we will either decrypt or encrypt
        if (userChar == 'd') {
            System.out.println("This is our plaintext integer: " + decrypt(plaintext, key) + " Binary: (" + Integer.toBinaryString(decrypt(plaintext, key)) + ")");

        } else if (userChar == 'e') {
            System.out.println("This is our ciphertext integer: " + encrypt(plaintext, key) + " Binary: (" + Integer.toBinaryString(encrypt(plaintext, key)) + ")");
        }
    }
}



