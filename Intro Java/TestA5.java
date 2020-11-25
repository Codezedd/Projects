package csc403;

import stdlib.*;

public class TestA5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StdIn.fromFile("data/tale.txt");

		A5LinearProbingHashST<String, Integer> LPH = new A5LinearProbingHashST<>();
		A5SeparateChainingHashST<String, Integer> SCH = new A5SeparateChainingHashST<>();

		for (int i = 0; !StdIn.isEmpty(); i++) {

			String key = StdIn.readString();
			LPH.put(key, i);
			SCH.put(key, i);

			// unique words in linear probing
			StdOut.println("Linear Probing Hash Table: ");
			StdOut.println("Number of unique words in Linear Probing: " + LPH.sameValueCount(1));
			StdOut.println("Number of words that appear 6 times in Linear Probing: " + LPH.sameValueCount(6));
			StdOut.println("Number of clusters: " + LPH.clusterCount());
			StdOut.println("Max cluster size: " + LPH.maxClusterSize());

			StdOut.println();

			StdOut.println("------------------------------------------------------------");
			StdOut.println();
			StdOut.println("Separate Chaining Hash Table: ");
			StdOut.println("Number of unique words in Separate Chaining: " + SCH.sameValueCount(1));
			StdOut.println("Number of words that appear 6 times in Separate Chaining: " + SCH.sameValueCount(6));
			StdOut.println("Chain Count: " + SCH.chainCount());
			StdOut.println("Max Chain Size: " + SCH.maxChainSize());

		}

	}
}
