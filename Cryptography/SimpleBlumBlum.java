
import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class SimpleBlumBlum {

    public static void main(String[] args) throws IOException {
        //1) since we are using simple integers I went with scanner to read in file
        //the bbstext.txt file will be included in the src folder
	
	//NOTE:Here we use the bbstext.txt file and the file path it is on in the users computer
        //File file = new File("bbstext.txt");
        Scanner scanner = new Scanner(file);

        //2) variables we need to fill from the bbstext.txt file
        /*
        int p = scanner.nextInt();
        int q = scanner.nextInt();
        int x = scanner.nextInt();
        */
        BigInteger p = BigInteger.valueOf(scanner.nextLong());
        BigInteger q = BigInteger.valueOf(scanner.nextLong());
        BigInteger x = BigInteger.valueOf(scanner.nextLong());

        //reading in the bit plaintext left the zero out from the beginning of the bit, so I converted to string

        String bitPlainText = scanner.next();

        //2A) checking to see our values from the text file was loaded successfully
        System.out.println("P value is: " + p);
        System.out.println("Q value is: " + q);
        System.out.println("X value is: " + x);
        System.out.println("Plain text bit string is: " + bitPlainText);

        //3)Blum Blum Shub takes the form of: xj ≡ xj−1^2 (mod n)

        //3A)We set n = p * q and choose a random integer x (5) that is relatively prime to n.
        //int modN = p * q;
        BigInteger modN = p.multiply(q);


        //Note: String is about 8 bits long, so one letter is 8 bits to keep it simple
        int count = 8;
        System.out.println("Count value is: " + count);
        //Storing the bbs numbers we calculated into this array
        //int[] blumBlumArray = new int[count];
        BigInteger[] blumBlumArray = new BigInteger[count];

        //3C)To initialize the BBS generator, set the initial seed to x0 ≡ x2 (mod n).
        //blumBlumArray[0] = (x * x) % modN;
        blumBlumArray[0] = (x.multiply(x)).mod(modN);
        //4)for loop to continue the rest of the BBS values until we reach our
        //count value (8)
        for (int i = 1; i < 8; i++) {
            //5) following the formula from section 5.1 Xj = Xj-1^2 (mod n)
            //blumBlumArray[i] = (blumBlumArray[i - 1] * blumBlumArray[i - 1]) % modN;
            blumBlumArray[i] = (blumBlumArray[i - 1].multiply(blumBlumArray[i - 1])).mod(modN);
        }

        //6)now checking through all the values for the least significant bit
        //to generate a sequence of bits for the XOR operation
        //Odd is 1 while Even is 0 based on section 5.1
        System.out.println("Blum Blum numbers are: " + Arrays.toString(blumBlumArray));

        String[] LSB = new String[count];

        for (int j = 0; j < 8; j++) {
            /*
            int lsbCheck = blumBlumArray[j] % 2;

            if (lsbCheck != 0) {
                LSB[j] = "1";
            } else {
                LSB[j] = "0";
            }
             */
            //New version below:
            BigInteger lsbCheck = blumBlumArray[j].mod(BigInteger.valueOf(2));
            int check =lsbCheck.compareTo(BigInteger.valueOf(0));
            if (check != 0) {
                LSB[j] = "1";
            } else {
                LSB[j] = "0";
            }
        }

        //7) last step would be to XOR our plaintext string and the LSB String
        String blumblumRand = String.join("", LSB);
        System.out.println("Bit values from Blum Blum numbers are: " + blumblumRand);

        String encryptedText = "";
        for (int k = 0; k < 8; k++) {
            if (bitPlainText.charAt(k) == blumblumRand.charAt(k)) {
                encryptedText += "0";
            } else {
                encryptedText += "1";
            }
        }
        System.out.println("Ciphertext bit string is: " + encryptedText);
    }
}



