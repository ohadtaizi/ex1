package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class WGraph_DS implements weighted_graph,Serializable  {
	private HashMap<Integer, node_info> nodes;
	private HashMap<Integer,HashMap<Integer,Double>> edges;
	private int edgesSize = 0;
	private int mcSize = 0;
	
	public class NodeInfo implements  node_info,Serializable  {
		private int key;
		private String info;
		private double tag;
		
		public NodeInfo(int key) {
			this.key = key;
		}
		
		public NodeInfo(node_info nodeInfo) {
			this(nodeInfo.getKey(), nodeInfo.getTag(), nodeInfo.getInfo());
		}
		
		public NodeInfo(int key, double tag, String info) {
			this.key = key;
			this.tag = tag;
			this.info = info;
		}
		public String toString() {
			return "NodeInfo [getKey=" + key + ",setTag" +tag+", getInfo=" + info + ", setInfo=" + info + ", getTag=" + tag
					+ "]";
		}
		
		@Override
		public int getKey() {
			return key;
		}

		@Override
		public String getInfo() {
			return info;
		}

		@Override
		public void setInfo(String s) {
			info = s;
		}

		@Override
		public double getTag() {
			return tag;
		}

		@Override
		public void setTag(double t) {
			tag = t;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((info == null) ? 0 : info.hashCode());
			result = prime * result + key;
			long temp;
			temp = Double.doubleToLongBits(tag);
			result = prime * result + (int) (temp ^ (temp >>> 32));
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
			node_info other = (node_info) obj;
			if (info == null) {
				if (other.getInfo() != null)
					return false;
			} else if (!info.equals(other.getInfo()))
				return false;
			if (key != other.getKey())
				return false;
			if (Double.doubleToLongBits(tag) != Double.doubleToLongBits(other.getTag()))
				return false;
			return true;
		}

		private WGraph_DS getEnclosingInstance() {
			return WGraph_DS.this;
		}
		
	}
	
	public WGraph_DS() {
		nodes = new HashMap<Integer, node_info>();
		edges = new HashMap<Integer, HashMap<Integer,Double>>();
	}
	
	public WGraph_DS(weighted_graph graph) {
		this.nodes = new HashMap<Integer, node_info>();
		this.edges = new HashMap<Integer,HashMap<Integer,Double>>();
		
		
		for(node_info node : graph.getV()) {
			node_info newNode = new NodeInfo(node);
			addNode(newNode);
		}
		

		// run over all vertexes of graph
		for(node_info node : graph.getV()) {
			
			// run over all neighbors of specific node
			for(node_info neighbor : graph.getV( node.getKey())) {
				//create neighbor
				double weight = graph.getEdge(node.getKey(), neighbor.getKey());
				
				// add neighbor
				connect(node.getKey(), neighbor.getKey(), weight);
			}
		}
		
		this.edgesSize = graph.edgeSize();
		this.mcSize = graph.getMC();
		
	}

	@Override
	public node_info getNode(int key) {
		return nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		HashMap<Integer,Double> nodeEdges = edges.get(node1);
		 if(nodeEdges == null) return false;
		 
		 return nodeEdges.get(node2) != null;
	}

	@Override
	public double getEdge(int node1, int node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) return -1;
        if (node1 == node2) return 0;
        if (!hasEdge(node1, node2)) return -1;
        
		
		
		return edges.get(node1).get(node2);
	}

	@Override
	public void addNode(int key) {
		if(!nodes.containsKey(key)) {
			nodes.put(key, new NodeInfo(key));
            HashMap<Integer, Double> neighbors = new HashMap<Integer, Double>();
            edges.put(key, neighbors);
            mcSize++;
		}
	}
	
	public void addNode(node_info node) {
		if(!nodes.containsKey(node.getKey())) {
			nodes.put(node.getKey(), new NodeInfo(node));
            HashMap<Integer, Double> neighbors = new HashMap<Integer, Double>();
            edges.put(node.getKey(), neighbors);
            mcSize++;
		}
	}

	@Override
	public void connect(int node1, int node2, double w) {
		if(w < 0 || 
		   !nodes.containsKey(node1) || 
		   !nodes.containsKey(node2) || 
		   node1 == node2 || 
		   (hasEdge(node1, node2) && getEdge(node1, node2) == w)) return;
		
		if(!hasEdge(node1,node2)) {
			edgesSize++;
		}
		
		
		HashMap<Integer, Double> hashEdge1 = edges.get(node1);
		hashEdge1.put(node2, w);
		
		HashMap<Integer, Double> hashEdge2 = edges.get(node2);
		hashEdge2.put(node1, w);
		
        edges.put(node1, hashEdge1);
        edges.put(node2, hashEdge2);
        
		mcSize++;
		
	}

	@Override
	public Collection<node_info> getV() {
		return nodes.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		ArrayList<node_info> list = new ArrayList<node_info>();
		for(int key : edges.get(node_id).keySet()) {
			list.add(getNode(key));
		}
		
		
		return list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + edgesSize;
		result = prime * result + mcSize;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
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
		WGraph_DS other = (WGraph_DS) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (edgesSize != other.edgesSize)
			return false;
		if (mcSize != other.mcSize)
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	@Override
	public node_info removeNode(int key) {
		if(!nodes.containsKey(key)) return null;
		
		for(node_info  node : getV(key)) {
			removeEdge(node.getKey(), key);
		}
		
		mcSize++;
		return nodes.remove(key);
	}

	@Override
	public void removeEdge(int node1, int node2) {
		if(!this.hasEdge(node1, node2)) return;

		edges.get(node1).remove(node2);
		edges.get(node2).remove(node1);
		
		mcSize++;
		edgesSize--;
	}

	@Override
	public int nodeSize() {
		return nodes.size();
	}

	@Override
	public int edgeSize() {
		return edgesSize;
	}

	@Override
	public int getMC() {
		return mcSize;
	}
	
	@Override
	public String toString() {
		return "WGraph_DS [nodes=" + nodes + ", edges=" + edges + ", edgesSize=" + edgesSize + ", mcSize=" + mcSize
				+ "]";
	}

}
