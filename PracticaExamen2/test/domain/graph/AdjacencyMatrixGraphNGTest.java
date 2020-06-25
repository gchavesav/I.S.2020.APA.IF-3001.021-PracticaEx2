/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.graph;

import domain.list.ListException;
import org.testng.annotations.Test;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class AdjacencyMatrixGraphNGTest {
    
    public AdjacencyMatrixGraphNGTest() {
    }

    @Test
    public void testSomeMethod() {
       try{
            AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(20);
            //add vertexes
            graph.addVertex("SJ");
            graph.addVertex("A");
            graph.addVertex("C");
            graph.addVertex("H");
            graph.addVertex("P");
            graph.addVertex("L");
            graph.addVertex("G");
            
            //add edges and weights
            graph.addEdgeWeight("SJ", "A", 15);
            graph.addEdgeWeight("SJ", "C", 20);
            graph.addEdgeWeight("SJ", "H", 15);
            graph.addEdgeWeight("SJ", "L", 150);
            graph.addEdgeWeight("SJ", "P", 70);
            graph.addEdgeWeight("C", "L", 130);
            graph.addEdgeWeight("A", "H", 10);
            graph.addEdgeWeight("A", "P", 90);
            graph.addEdgeWeight("P", "G", 100);
         
            System.out.println(graph.toString());
            System.out.println("GET SHORTEST DISTANCE: "
                    +graph.getShortestDistance());
            System.out.println("TOTAL KMS: "
                    +graph.totalKms());
        
       }catch(GraphException | ListException ex){
           System.out.println(ex.getMessage());
       }
    }
    
}
