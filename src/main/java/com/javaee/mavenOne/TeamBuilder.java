package com.javaee.mavenOne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Stack;

import com.javaee.mavenOne.tarjan.*;

/**
 * This class is specified in the exercise as to be created. The one function,
 * specialLocations, executes the logic for calculating the desired counts.
 * 
 * @author stevendyer
 *
 */
public class TeamBuilder {

	/**
	 * Calculate number of "Special Locations".
	 * 
	 * Main processing function, taking in a graph (as an array of strings of
	 * outgoing edge lists) and returning the number of "special locations" -
	 * those that can reach or be reached by all other nodes.
	 * 
	 * @param paths
	 *            - array of nodes, each a string containing a series of 0's or
	 *            1's, 1 indicating a link to a node.
	 * 
	 * @return int[] - first number is count of nodes that can reach all others,
	 *         second is count of those that can be reached by all others
	 */
	public int[] specialLocations(String[] paths) {
		/**
		 * Set number of raw nodes in our graph, i.e. the number of strings in
		 * the paths array.
		 */
		int numNodes = paths.length;

		/**
		 * From the path string array, construct graph. I.e. a list of nodes and
		 * edges. And count the edges
		 */
		List<Integer>[] nodeList = new List[numNodes];
		int edgeCount = 0;
		for (int i = 0; i < numNodes; i++) {
			nodeList[i] = new ArrayList<Integer>();
			for (int j = 0; j < paths[i].length(); j++) {
				if (paths[i].charAt(j) != '0') {
					nodeList[i].add(j);
					edgeCount++;
				}
			}
		}
		/**
		 * Output the nodelist and edge count to the console so the user can
		 * follow along.
		 */
		System.out.println("\nNodes and their outgoing edges (nodeList) : ");
		for (int i = 0; i < numNodes; i++) {
			System.out.println(i + " " + nodeList[i]);
		}
		System.out.println("edgeCount = " + edgeCount);

		/** If too few edges, it's a forest, so return {0,0} */
		if (edgeCount < numNodes - 1) {
			System.out.println("Too few edges, returning {0,0}");
			return new int[] { 0, 0 };
		}

		/**
		 * Create a tarjan object and have it calculate the Strongly Connected
		 * Components.
		 */
		Tarjan tarjan = new Tarjan();
		List<List<Integer>> scComponents = tarjan.getSCComponents(nodeList);
		/** Print the SCC list and the map of the original nodes to the SCCs **/
		System.out.println("\nTarjan-calculated SCC's (scComponents) : \n" + scComponents);
		System.out.println("sccCount = " + tarjan.sccCount);
		System.out.println("\nSCC that each node is in (nodeSccMap) : \n" + Arrays.toString(tarjan.nodeSccMap));

		/**
		 * Using the original nodeList and the map of those nodes to SCCs. The
		 * result is two bitsets, one bit per SCC, indicating which SCCs have
		 * incoming/outgoing edges.
		 */
		BitSet sccHasIncoming = new BitSet(tarjan.sccCount);
		BitSet sccHasOutgoing = new BitSet(tarjan.sccCount);
		/** Run through the nodes in the original graph */
		for (int i = 0; i < nodeList.length; i++) {
			/** evaluate each outgoing edge in the node */
			for (int j : nodeList[i]) {
				/**
				 * If nodes are in different SCC's, update the source and sink
				 * data for the scc's involved in the edge. Note: we ignore
				 * edges within the same SCC - i.e. both nodes i and j map to
				 * the same SCC.
				 */
				if (tarjan.nodeSccMap[i] != tarjan.nodeSccMap[j]) {
					/**
					 * Set that the target of the edge, j, has at least one
					 * incoming edge
					 */
					sccHasIncoming.set(tarjan.nodeSccMap[j]);
					/**
					 * Set that the source of the edge, i, has at least one
					 * outgoing edge
					 */
					sccHasOutgoing.set(tarjan.nodeSccMap[i]);
				}
			}
		}

		/**
		 * Determine count of "can reach all" nodes.
		 * 
		 * If *exactly one* SCC is a source, then the "can reach all" count is
		 * the number of nodes in that SCC. If there is more than one SCC that
		 * is a source - i.e. more than one zero in the bitset - then these
		 * nodes cannot get to each other, therefore there cannot be any node
		 * that can reach all others, so the "can reach all" count is zero.
		 * 
		 * The "has incoming/outgoing" bitsets created above contain the info on
		 * which SCCs have incoming/outgoing edges.
		 */
		int canReachAll = 0;
		/** If exactly one bit is zero in the BitSet... */
		if (sccHasIncoming.cardinality() == tarjan.sccCount - 1) {
			/**
			 * get the position of the one zero bit, which is the scc's index...
			 */
			int pos = sccHasIncoming.nextClearBit(0);
			/** set the count to the number of nodes in that scc */
			canReachAll = scComponents.get(pos).size();
		}

		/**
		 * Determine count of "reached by all" nodes. The logic is the same as
		 * above for calculating canReachAll. If *exactly one* SCC is a sink,
		 * then the count is the number of nodes in that SCC.
		 */
		int reachedByAll = 0;
		if (sccHasOutgoing.cardinality() == tarjan.sccCount - 1) {
			int pos = sccHasOutgoing.nextClearBit(0);
			reachedByAll = scComponents.get(tarjan.nodeSccMap[pos]).size();
		}

		/**
		 * Return integer array containing the "can reach all" and "reached by
		 * all" counts.
		 */
		return new int[] { canReachAll, reachedByAll };
	}
}
