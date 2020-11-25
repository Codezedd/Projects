package csc403;

import stdlib.*;
import java.util.*;

public class DNAStatistics {

	// main method
	public static void main(String[] args) {

		StdIn.fromFile("data/sequences.txt");

		Hashtable<String, Integer> CHT = new Hashtable<String, Integer>();
		Hashtable<Character, Integer> NHT = new Hashtable<Character, Integer>();

		int totalcondons = 0;
		int totalnucleo = 0;

		while (StdIn.hasNextLine()) {
			String file = StdIn.readLine();

			String[] fileArray = file.trim().split("\\s");

			String Species = fileArray[0] + " " + fileArray[1];

			String DNA_Code = fileArray[2];

			for (int i = 0; i < DNA_Code.length(); i++) {
				totalnucleo++;
				
				Character DNAChar = Character.toUpperCase(DNA_Code.charAt(i));

				if (NHT.containsKey(DNAChar)) {
					NHT.put(DNAChar, NHT.get(DNAChar) + 1);
				}

				else {
					NHT.put(DNAChar, 1);
				}
			}

			for (int i = 0; (i + 3) < DNA_Code.length(); i += 3) {

				String codon = DNA_Code.substring(i, (i + 3)).toUpperCase();

				totalcondons++;

				if (CHT.containsKey(codon)) {
					CHT.put(codon, CHT.get(codon) + 1);
				}

				else {
					CHT.put(codon, 1);

				}
			}

			StdOut.println(Species);
			StdOut.println("Number of Nucleotides: " + totalnucleo);
			StdOut.println("A: " + NHT.get('A') + " C: " + NHT.get('C') + " G: " + NHT.get('G') + " T: " + NHT.get('T'));
			StdOut.println("Number of Codons: " + totalcondons);
			StdOut.println("Number of Different Codons: " + CHT.size());

			String[] codons = CHT.keySet().toArray(new String[CHT.size()]);

			for (int i = 0; i < codons.length;) {
				
				for (int j = 0; j < 8 && i < codons.length; j++, i++) {
					StdOut.print(String.format("%5s:%3d", codons[i], CHT.get(codons[i])));
				}
				
				StdOut.println();
			}
			StdOut.println();
		}
	}

}
