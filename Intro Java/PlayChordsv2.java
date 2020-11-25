package csc403;

import stdlib.*;
import java.util.*;
import algs31.BinarySearchST;

public class PlayChordsv2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StdIn.fromFile("data/notes_frequencies.txt");

		BinarySearchST<String, Double> st = new BinarySearchST<>();

		while (StdIn.hasNextLine()) {
			String notesheet = StdIn.readLine();

			String notescore[] = notesheet.split("\\s+");

			String Musicnote = notescore[0];

			double Frequency = Double.parseDouble(notescore[1]);

			st.put(Musicnote, Frequency);

		}

		StdIn.fromFile("data/a2chords.txt");

		while (StdIn.hasNextLine()) {

			String a2Chords = StdIn.readLine();

			String[] Chordlist = a2Chords.split("\\s+");
			double[] frequencyvalue = new double[Chordlist.length];

			for (int i = 0; i < frequencyvalue.length; i++) {

				double frequency = st.get(Chordlist[i]);

				frequencyvalue[i] = frequency;

			}
			StdOut.println(Arrays.toString(frequencyvalue));
			playChordForOneSecond(frequencyvalue);

		}

	}

	public static void playChordForOneSecond(double... frequencies) {
		final int sliceCount = (int) (StdAudio.SAMPLE_RATE * 1);
		final double[] slices = new double[sliceCount + 1];
		for (int i = 0; i <= sliceCount; i++) {
			for (double frequency : frequencies) {
				slices[i] += Math.sin(2 * Math.PI * i * frequency / StdAudio.SAMPLE_RATE);
			}
			slices[i] /= frequencies.length;
		}
		StdAudio.play(slices);
	}

}
