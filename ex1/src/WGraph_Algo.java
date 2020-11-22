package ex1.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;




public class WGraph_Algo implements weighted_graph_algorithms,Serializable {


	private weighted_graph graph;

	public void dijkstra(node_info src, weighted_graph weightedGraph, Hashtable<Integer, Integer> predecessors)  {

		// run over vertexes and set all
		for ( node_info vertex : weightedGraph.getV()) {
			vertex.setInfo("white");
			vertex.setTag(Integer.MAX_VALUE);
			predecessors.put(vertex.getKey(),-1);
		}


		PriorityQueue<node_info> q = new PriorityQueue<>(new NodeCompare());
		src.setTag(0);
		q.add(src);

		while (!q.isEmpty()) {
			node_info vertex = q.poll();
			Collection<node_info> neighbors =  weightedGraph.getV(vertex.getKey());

			for (node_info neighbor : neighbors) {
				double w = weightedGraph.getEdge(vertex.getKey(), neighbor.getKey());

				if(neighbor.getInfo() == "white") {
					double sourceWeight = vertex.getTag() + w;

					if (sourceWeight < neighbor.getTag()) {
						neighbor.setTag(sourceWeight);
						predecessors.put(neighbor.getKey(),vertex.getKey());
						q.add(neighbor);
					}
				}
			}
			vertex.setInfo("black");
		}
	}

	@Override
	public void init(weighted_graph g) {
		this.graph = g;		
	}

	@Override
	public weighted_graph getGraph() {

		return graph;
	}

	@Override
	public weighted_graph copy() {
		if(this.graph==null ) {
			return null;
		}

		return new WGraph_DS(graph);
	}

	@Override
	public boolean isConnected() {

		Hashtable<Integer, Integer> predecessors = new Hashtable<Integer, Integer>();

		//copy graph
		weighted_graph copy = copy();

		//get first node 
		Iterator<node_info> it = copy.getV().iterator();
		if (!it.hasNext()) return true;
		node_info src = it.next();

		// activate Dijkstra 
		dijkstra(src, copy, predecessors);



		Iterator<node_info> iterator = copy.getV().iterator();

		// run over all vertexes and check if all the vertexes are equal to "black"
		while (iterator.hasNext()) {
			node_info node_info = iterator.next();
			if(node_info.getInfo() != "black") {
				return false;
			}
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		if (graph.getNode(src) == null || graph.getNode(dest) == null) 
			return -1;
		Hashtable<Integer, Integer> predecessors = new Hashtable<Integer, Integer>();
		weighted_graph copy = copy();
		dijkstra(copy.getNode(src),copy, predecessors);
		return copy.getNode(dest).getTag();

	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {

		if(graph.getNode(src) == null || graph.getNode(dest) == null)  return null;
		Stack<node_info> list = new Stack<node_info>();

		Hashtable<Integer, Integer> predecessors = new Hashtable<Integer, Integer>();
		weighted_graph copy = copy();
		dijkstra(copy.getNode(src),copy, predecessors);

		Integer perent = dest;

		while (perent != src && perent != -1) {
			node_info nodePerent =  copy.getNode(perent);
			list.add(nodePerent);
			perent = predecessors.get(perent);		
		}

		list.add(copy.getNode(src));
		Collections.reverse(list);
		return list;


	}

	@Override
	public boolean save(String file) {

		try {
			FileOutputStream fileStreamPrimitive  = new FileOutputStream(file);
			ObjectOutputStream fileStreamObject = new ObjectOutputStream(fileStreamPrimitive);

			fileStreamObject.writeObject(this.graph);

			fileStreamPrimitive.close();
			fileStreamObject.close();
		} catch (IOException Error) {
			Error.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean load(String file) {

		try {
			FileInputStream fileStreamPrimitive = new FileInputStream(file);
			ObjectInputStream fileStreamObject = new ObjectInputStream(fileStreamPrimitive);

			WGraph_DS loadedFile = (WGraph_DS) fileStreamObject.readObject();
			
			fileStreamPrimitive.close();
			fileStreamObject.close();
			
			this.graph = loadedFile;
			
		} catch (IOException | ClassNotFoundException Error) {
			
			Error.printStackTrace();
			return false;
		}


		return false;
	}
	
	@Override
	public String toString() {
		return "WGraph_Algo [graph=" + graph + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WGraph_Algo other = (WGraph_Algo) obj;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		return true;
	}
}
