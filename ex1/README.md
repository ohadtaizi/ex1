#EX1
Initially, I implemented the node info department Then I put it into the weighed graph class.

In the second step, I implemented the weighed graph, All the features in the graph, connecting nodes, deleting nodes, deleting ribs that connect two nodes, to store all the nodes in the graph I created a hash map that stores all the nodes and another hash map to store all the ribs that connect all the nodes, This is how we implement the weighed graph.

In the third step, I implemented the weighted graph algorithms.
I created a copy function that performs all the operations in WGraph_DS.
I created a function is connected in which we use the copy function and check that it gets nodes and ribs and perform dijkstra on it and then we will run on all the vertices and check that they are all black similar to bfs.



I created a function shortestPathDist
returns the length of the shortest path between src to dest, If the graph is blank it will return 1- I have created predecessors with which we can store all the ancestors I visited to find the shortest route so we will use dijkstra.

The shortestPathDist function is similar to the shortestPath function,
 It's just that instead of returning the weight of the shortest path I return a NODE list that describes the path
