package com.javaee.mavenOne.tarjan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Stack;

/**
 * Implements Tarjan algorithm for determining Strongly Connected Components
 * (SCCs) in a given graph.
 * 
 * @author stevendyer
 *
 */
public class Tarjan {
	/** number of vertices **/
	private int V;
	/** preorder number counter **/
	private int preCount;
	/** low number of v **/
	private int[] low;
	/** to check if v is visited **/
	private boolean[] visited;
	/** to store given graph **/
	private List<Integer>[] graph;
	/** to store all scc **/
	private List<List<Integer>> sccComp;
	private Stack<Integer> stack;

	public int[] nodeSccMap;
	public int sccCount;

	public void printBitSet(String prompt, BitSet b) {
		System.out.print(prompt + " ");
		for (int i = 0; i < b.size(); i++) {
			System.out.print(b.get(i) ? "1" : "0");
		}
		System.out.println();
	}

	/**
	 * Function to get all strongly connected components.
	 * 
	 * @param graph
	 *            Array of integer lists. Each element in the array is a node,
	 *            the integer list for each node is the outgoing edge list for
	 *            that node.
	 * 
	 * @return List of integer lists - i.e. a list of Components, each a list of
	 *         node numbers in that component
	 */
	public List<List<Integer>> getSCComponents(List<Integer>[] graph) {
		V = graph.length;
		this.graph = graph;
		low = new int[V];
		visited = new boolean[V];
		stack = new Stack<Integer>();
		sccComp = new ArrayList<List<Integer>>();
		nodeSccMap = new int[V];
		sccCount = 0;

		for (int v = 0; v < V; v++)
			if (!visited[v])
				dfs(v);

		return sccComp;
	}

	/**
	 * Depth First Search
	 * 
	 * This function runs a DFS on the object's member graph array. It sets the
	 * member sccComp, nodeSccMap and sccCount items.
	 */
	public void dfs(int v) {
		low[v] = preCount++;
		visited[v] = true;
		stack.push(v);
		int min = low[v];
		for (int w : graph[v]) {
			if (!visited[w])
				dfs(w);
			if (low[w] < min)
				min = low[w];
		}
		if (min < low[v]) {
			low[v] = min;
			return;
		}
		List<Integer> component = new ArrayList<Integer>();
		int w;
		do {
			w = stack.pop();
			/**
			 * Update the node/scc map for the node just popped, setting it to
			 * the current component number
			 */
			nodeSccMap[w] = sccComp.size();
			component.add(w);
			low[w] = V;
		} while (w != v);
		sccComp.add(component);
		/** update the count of SCCs */
		sccCount++;
	}

}
