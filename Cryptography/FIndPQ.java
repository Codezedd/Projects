import java.util.*;
import java.math.*;
//NOTE: This is a exercise in RSA and finding the values
//P and Q only based on a knowing the value of Φ(n)
public class FIndPQ {
    public static void main(String args[]) {
        //placing very large numbers as string inputs for big integer
        BigInteger n = new BigInteger("898607526590969848863184322603417866026220164569611859928589");
        BigInteger phiN = new BigInteger("898607526590969848863184322601520436048324820954773803576156");

        //this will equal p + q portion of RSA since p+q = n+1 - Φ(n); using algebra
        BigInteger pAddQ = (n.add(BigInteger.valueOf(1))).subtract(phiN);
        System.out.println("This is p+q : " + pAddQ);

        //now find p-q with:
        //(p−q)^2=(p+q)^2−4n
        //This is 4n
        BigInteger fourN = BigInteger.valueOf(4).multiply(n);
        //this is (p+q^2) - 4n
        //Had to use java 9 to use square root function on a online java IDE
        BigInteger pMinusQSquared = (pAddQ.pow(2)).subtract(fourN);

        BigInteger pMinusQ = new BigInteger("76226075932352462711493609900");
        System.out.println("This is p-q : " + pMinusQ);

        //Now we calculate q with: (p+q)−(p−q)/2
        BigInteger finalQ = (pAddQ.subtract(pMinusQ)).divide(BigInteger.valueOf(2));
        System.out.println("This is q :   " + finalQ);

        //now finding p
        BigInteger finalP = n.divide(finalQ);
        System.out.println("This is p :   " + finalP);

        //Testing our values
        System.out.println("Testing if pq = n: " + (finalP.multiply(finalQ)));

        System.out.println("This is n :        " + n);
    }
}
