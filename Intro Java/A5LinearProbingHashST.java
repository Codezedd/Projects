package csc403;

import stdlib.*;
import algs13.Queue;
/* ***********************************************************************
 *  Compilation:  java LinearProbingHashST.java
 *  Execution:    java LinearProbingHashST
 *
 *  Symbol table implementation with linear probing hash table.
 *
 *  % java LinearProbingHashST
 *  128.112.136.11
 *  208.216.181.15
 *  null
 *
 *
 *************************************************************************/

public class A5LinearProbingHashST<K, V> {
	private static final int INIT_CAPACITY = 4;

	private int N; // first of key-value pairs in the symbol table
	private int M; // size of linear probing table

	private K key;
	private V val;
	private K[] keys; // the keys
	private V[] vals; // the values

	// create an empty hash table - use 16 as default size
	public A5LinearProbingHashST() {
		this(INIT_CAPACITY);
	}

	// create linear proving hash table of given capacity
	@SuppressWarnings("unchecked")
	public A5LinearProbingHashST(int capacity) {
		M = capacity;
		keys = (K[]) new Object[M];
		vals = (V[]) new Object[M];
	}

	// return the first of key-value pairs in the symbol table
	public int size() {
		return N;
	}

	// is the symbol table empty?
	public boolean isEmpty() {
		return size() == 0;
	}

	// does a key-value pair with the given key exist in the symbol table?
	public boolean contains(K key) {
		return get(key) != null;
	}

	// hash function for keys - returns value between 0 and M-1
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	// resize the hash table to the given capacity by re-hashing all of the keys
	private void resize(int capacity) {
		A5LinearProbingHashST<K, V> temp = new A5LinearProbingHashST<>(capacity);
		for (int i = 0; i < M; i++) {
			if (keys[i] != null) {
				temp.put(keys[i], vals[i]);
			}
		}
		keys = temp.keys;
		vals = temp.vals;
		M = temp.M;
	}

	// insert the key-value pair into the symbol table
	public void put(K key, V val) {
		if (val == null)
			delete(key);

		// double table size if 50% full
		if (N >= M / 2)
			resize(2 * M);

		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if (keys[i].equals(key)) {
				vals[i] = val;
				return;
			}
		}
		keys[i] = key;
		vals[i] = val;
		N++;
	}

	// return the value associated with the given key, null if no such value
	public V get(K key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
			if (keys[i].equals(key))
				return vals[i];
		return null;
	}

	// delete the key (and associated value) from the symbol table
	public void delete(K key) {
		if (!contains(key))
			return;

		// find position i of key
		int i = hash(key);
		while (!key.equals(keys[i])) {
			i = (i + 1) % M;
		}

		// delete key and associated value
		keys[i] = null;
		vals[i] = null;

		// rehash all keys in same cluster
		i = (i + 1) % M;
		while (keys[i] != null) {
			// delete keys[i] an vals[i] and reinsert
			K keyToRehash = keys[i];
			V valToRehash = vals[i];
			keys[i] = null;
			vals[i] = null;
			N--;
			put(keyToRehash, valToRehash);
			i = (i + 1) % M;
		}

		N--;

		// halves size of array if it's 12.5% full or less
		if (N > 0 && N <= M / 8)
			resize(M / 2);

		assert check();
	}

	// return all of the keys as in Iterable
	public Iterable<K> keys() {
		Queue<K> queue = new Queue<>();
		for (int i = 0; i < M; i++)
			if (keys[i] != null)
				queue.enqueue(keys[i]);
		return queue;
	}

	// integrity check - don't check after each put() because
	// integrity not maintained during a delete()
	private boolean check() {

		// check that hash table is at most 50% full
		if (M < 2 * N) {
			System.err.println("Hash table size M = " + M + "; array size N = " + N);
			return false;
		}

		// check that each key in table can be found by get()
		for (int i = 0; i < M; i++) {
			if (keys[i] == null)
				continue;
			else if (get(keys[i]) != vals[i]) {
				System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
				return false;
			}
		}
		return true;
	}

	// adding sameValueCount(V value)

	public int sameValueCount(V val) {

		int counter = 0;

		for (int i = 0; i < vals.length; i++) {

			if (val.equals(vals[i]))

				counter++;
		}

		return counter;
	}

	// adding clusterCount()

	public int clusterCount() {

		int counter = 0;

		for (int i = 1; i < vals.length; i++) {
			if (vals[i - 1] == null && vals[i] != null) {
				counter++;
			}
		}
		return counter;
	}

	// adding maximum cluster size
	public int maxClusterSize() {

		Integer first = 0;
		Integer second = 0;

		for (int i = 1; i < vals.length;) {
			int counter = 0;
			while (vals[i] != null) {
				counter++;
				i++;
			}
			first = counter;
			if (second.equals(first)) {
				second = first;
			} else if (second > first) {
				second = second + 0;
			} else {
				second = first;
			}
			i++;
		}
		return second;
	}

	/*
	 * *********************************************************************
	 * Unit test client.
	 ***********************************************************************/
	public static void main(String[] args) {
		StdIn.fromFile("data/tiny.txt");

		A5LinearProbingHashST<String, Integer> st = new A5LinearProbingHashST<>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}

		// print keys
		for (String s : st.keys())
			StdOut.println(s + " " + st.get(s));
	}

}