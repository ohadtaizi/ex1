package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import ex1.src.*;

class WGraph_AlgoTestMyTest {


    @Test
    void init() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,0,1);
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(0,3,1);
        g.connect(0,3,1);

        weighted_graph_algorithms graph= new WGraph_Algo();
        graph.init(g);
      
        assertEquals(3, graph.getGraph().edgeSize());
        
        assertTrue(graph.getGraph().hasEdge(0, 1));
        
        assertFalse(graph.getGraph().hasEdge(0, 0));
      
    }
    @Test
    void copy() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);

        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);
        weighted_graph g2= ga.copy();

        g.addNode(4);
        g2.removeNode(0);

        assertNotEquals(g.nodeSize(),g2.nodeSize());
        assertNotEquals(g.edgeSize(),g2.edgeSize());
        assertNotEquals(g,g2);
    }
    @Test
    void isConnected() {

        weighted_graph g = new WGraph_DS();
        int n = 100000;

        for(int i = 0; i < n; i++) g.addNode(i);
        
        // Connect all egdg node with 0 except 1
        for(int i = 2; i < n; i++) g.connect(0, i,1);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertEquals(false, ga.isConnected());
        
        // Connect the missing edge
        g.connect(0, 1,1);
        assertEquals(true, ga.isConnected());
    }
    @Test
    void shortestPathDist() {
        weighted_graph g = new WGraph_DS();
        
        int n = 10;

        for(int i = 0; i < n; i++)
        	g.addNode(i);

        // 0 -> 1 -> 2 -> 3 -> 6 -> 5
        // weights: [2,4,5,3,1] = 15
        g.connect(0,1,2);
        g.connect(1,2,4);
        g.connect(2,3,5);
        g.connect(3,6,3);
        g.connect(6,5,1);

        // 0 -> 4 -> 7 -> 8 -> 9 -> 1
        //  : weights: [3,1,5,1,4] = 14
        g.connect(0,4,3);
        g.connect(4,7,1);
        g.connect(7,8,5);
        g.connect(8,9,1);
        g.connect(9,1,4);

        weighted_graph_algorithms mygraph = new WGraph_Algo();
        mygraph.init(g);
        double weights1 =  mygraph.shortestPathDist(0,5);
        assertEquals(15,weights1);

      

        double weights =  mygraph.shortestPathDist(0,1);
        assertEquals(2,weights);

    }
    @Test
    void shortestPath() {
     weighted_graph g = new WGraph_DS();
        
        int n =10;

        for(int i = 0; i < n; i++)
        	g.addNode(i);

        // weights: [2,4,5,3,1] = 14
        g.connect(0,1,2);
        g.connect(1,2,4);
        g.connect(2,3,5);
        g.connect(3,6,3);
 

        //  : weights: [3,1,6,1,4] = 14
        g.connect(0,4,3);
        g.connect(4,7,1);
        g.connect(7,8,5);
        g.connect(8,9,1);
        g.connect(9,5,4);

        // long way 
        g.connect(6,5,1);
        
        weighted_graph_algorithms mygraph = new WGraph_Algo();
        int[] pathA = {0,1,2,3,6};
        int[] pathB = {0,4,7,8,9,5};


        mygraph.init(g);
        List<node_info> listA =  mygraph.shortestPath(0,6);
        System.out.println(listA);
        for(int i = 0; i < listA.size(); i++) assertEquals(pathA[i],listA.get(i).getKey());
        
        List<node_info> listB =  mygraph.shortestPath(0,5);
        System.out.println(listB);
        for(int i = 0; i < listB.size(); i++) assertEquals(pathB[i],listB.get(i).getKey());


    }
    
    @Test
    void save_load() {
    	//save
        weighted_graph g0 = WGraph_DSTestMyTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.file";
        ag0.save(str);

        String str3 = "g3.file";
        ag0.save(str3);
        
        //load
        weighted_graph g1 = WGraph_DSTestMyTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
        
        //load
        weighted_graph g2 = WGraph_DSTestMyTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g1,g2);

        
        weighted_graph g3 = WGraph_DSTestMyTest.graph_creator(10,30,1);
        ag0.load(str3);
        assertEquals(g1,g3);
    }
}
