package csc403;

import java.text.DecimalFormat;
import algs31.SequentialSearchST;
import algs32.BST;
import stdlib.*;

public class TimeSymbolTables {

	public static void main(String[] args) {
		StdIn.fromFile("data/tale.txt");
		String[] Tale = StdIn.readAllStrings();
		DecimalFormat time = new DecimalFormat("%.2f");

		SequentialSearchST<String, Integer> SQSearch = new SequentialSearchST<String, Integer>();
		BST<String, Integer> BST = new BST<String, Integer>();

		int SQWordcount = 0;
		int BSTWordcount = 0;

		Stopwatch SQTimer = new Stopwatch();

		for (int i = 0; i < Tale.length; i++) {
			String key = Tale[i].toLowerCase();
			SQSearch.put(key, i);
		}

		for (int j = 0; j < Tale.length; j++) {

			if (SQSearch.contains(Tale[j].toLowerCase())) {
				SQWordcount++;
			}
		}

		double SQTime = SQTimer.elapsedTime();

		Stopwatch BSTTime = new Stopwatch();

		for (int i = 0; i < Tale.length; i++) {
			String key = Tale[i].toLowerCase();
			BST.put(key, i);
		}

		for (int j = 0; j < Tale.length; j++) {
			if (BST.contains(Tale[j].toLowerCase())) {
				BSTWordcount++;
			}
		}
		double BSTtimer = BSTTime.elapsedTime();

		StdOut.println("Sequential Search Word Count: " + SQWordcount);
		StdOut.println("Sequential Search Time: " + time.format(SQTime));
		StdOut.println();
		StdOut.println("BST Word Count: " + BSTWordcount);
		StdOut.println("BST Time: " + time.format(BSTtimer));
	}

}