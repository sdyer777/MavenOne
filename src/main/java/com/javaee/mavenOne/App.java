package com.javaee.mavenOne;

import java.util.Arrays;

/**
 * Coding exercise for Nabil at MassMutual.
 * 
 * Given a number of locations and he directional paths between them, calculate:
 * - the number of locations that can reach all others - the number of locations
 * that can be reached by all others
 * 
 * This is an exercise in graph manipulation and traversal. The code does the
 * following: - create graph node list from the "paths" parameter, the input to
 * the exercise - use Tarjan's algorithm to reduce cycles to Strongly Connected
 * Components (SCCs) - iterate over the SCCs to find sources and sinks - "to
 * all" and "from all" rely on their being exactly ONE source/sink, so if
 * exactly one is found, the number of nodes in that SCC is the desired result.
 *
 */
public class App {
	/**
	 * Helper function to organize test path string arrays. Set the scenario to
	 * process on the first line in MAIN below.
	 * 
	 * @param scenario
	 *            - which test patch scenario to return
	 */
	private static String[] getPaths(int scenario) {
		switch (scenario) {
		/** exercise example 0. Returns 1,1 */
		case 0:
			return new String[] { "010", "000", "110" };
		/** exercise example 1. Returns 1,3 */
		case 1:
			return new String[] { "0010", "1000", "1100", "1000" };
		/** exercise example 2. Returns 5,5 */
		case 2:
			return new String[] { "01000", "00100", "00010", "00001", "10000" };
		/** exercise example 3. Returns 1,3 */
		case 3:
			return new String[] { "0110000", "1000100", "0000001", "0010000", "0110000", "1000010", "0001000" };
		/** too few edges (two edges for four nodes). Returns 0,0 */
		case 4:
			return new String[] { "0000", "0000", "1000", "1000" };
		/** simple two-node, one-scc graph. Returns 2,2 */
		case 5:
			return new String[] { "01", "10" };
		/** larger graph of 12 nodes. Returns 0, 10 */
		case 6:
			return new String[] { "000010000000", "101000000010", "000000000010", "000011000000", "100000000010",
					"000000001000", "010100000000", "000000000010", "000101010000", "000000010000", "011000000001",
					"000000001000" };
		/**
		 * yet larger graph of 14 nodes. Same 12 nodes as above, with two nodes
		 * above indexes 9 and 6, node index 13 being a "can reach all" source.
		 * Returns 1, 10
		 */
		case 7:
			return new String[] { "00001000000000", "10100000001000", "00000000001000", "00001100000000",
					"10000000001000", "00000000100000", "01010000000000", "00000000001000", "00010101000000",
					"00000001000000", "01100000000100", "00000000100000", "00000010010000", "00000000000010" };
		/**
		 * Four nodes and 3 edges, two sources and two sinks. No cycles so no
		 * SCCs. I.e. raw, pre-SCC graph is a forest. Returns 0,0
		 */
		case 8:
			return new String[] { "0110", "0000", "0000", "0100" };
		/**
		 * Four nodes in two non-bridged cycles. I.e. the post-SCC DAG is a
		 * forest. Returns 0,0
		 */
		case 9:
			return new String[] { "0100", "1000", "0001", "0010" };
		/**
		 * default - simple two-node, one-scc graph, should return 2,2 (above as
		 * case 5)
		 */
		default:
			return new String[] { "01", "10" };
		}
	}

	/**
	 * Main program call.
	 * 
	 * Creates a TeamBuilder object, processes the specified path scenario,
	 * writes the "can reach all" and "reached by all" counts to the console.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/** Set the scenario to test */
		int scenario = 1;

		/**
		 * Retrieve the scenario's path string array from the helper getPaths
		 * function.
		 */
		String[] paths = getPaths(scenario);
		System.out.println("Path scenario: " + Arrays.toString(paths));

		/** Create TeamBuilder object, the main object in the exercise. */
		TeamBuilder tb = new TeamBuilder();

		/** Calculate the special location counts */
		int[] counts = tb.specialLocations(paths);

		/** Write the results to the console */
		System.out.println("\nRESULTS:\nCan Reach All = " + counts[0] + "\nReached By All = " + counts[1]);
	}

}
