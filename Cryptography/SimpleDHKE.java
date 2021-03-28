import java.util.*;
import java.math.*;
//Note: This implementation is based on the textbook
//Introduction to Cryptography with Coding Theory, 3rd Edition
public class SimpleDHKE {
    public static void main(String[] args) {
        BigInteger A,P,X,id,Z,AXY,XOR,testValue;

        //1)Our public value A and P
        A = BigInteger.valueOf(16);
        P = BigInteger.valueOf(335);

        //1A)Our secret value Y
        int Y = 7;

        //2)The alpha^x mod p = .
        // The integer value X is based on our partners X value result or in
        //for our example we can calculate our own X value for testing
        testValue = (A.pow(10)).mod(P);
        X = testValue;

        //3)following the calculation for DHK Encryption first with
        //z = alpha^y mod p
        Z = (A.pow(Y)).mod(P);

        //4)calculating the agreed-upon key a^xy mod p
        AXY = (X.pow(Y)).mod(P);

        //5)XOR our key a^xy mod p with a random number
        id = BigInteger.valueOf(4561630);
        XOR = AXY.xor(id);


        System.out.println("Z value " + Z);
        System.out.println("a^XY agreed upon key " + AXY);
        System.out.println("C value " + XOR);
    }
}
