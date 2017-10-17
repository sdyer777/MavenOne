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
	 * Main program call.
	 * 
	 * Creates a TeamBuilder object, processes the specified path scenario,
	 * writes the "can reach all" and "reached by all" counts to the console.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/** Set the scenario to test */
		int scenario = 9;

		/**
		 * Retrieve the scenario's path string array from the helper class function Scenarios.getPaths.
		 */
		String[] paths = Scenarios.getPaths(scenario);
		System.out.println("Path scenario " + scenario + ": " + Arrays.toString(paths));

		/** Create TeamBuilder object, the main object in the exercise. */
		TeamBuilder tb = new TeamBuilder();

		/** Calculate the special location counts */
		int[] counts = tb.specialLocations(paths);

		/** Write the results to the console */
		System.out.println("\nRESULTS:\nCan Reach All = " + counts[0] + "\nReached By All = " + counts[1]);
	}

}
