/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.graph;

import domain.list.ListException;
import domain.list.SinglyLinkedList;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A
 */
public class SinglyLinkedListGraph implements Graph {
    private SinglyLinkedList vertexList;
    //DFS, BFS TRAVERSAL
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public SinglyLinkedListGraph(){
        this.vertexList = new SinglyLinkedList();
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
    }

    @Override
    public int size() throws ListException {
        return vertexList.size();
    }

    @Override
    public void clear() {
        vertexList.clear();
    }

    @Override
    public boolean isEmpty() {
        return vertexList.isEmpty();
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Singly Linked List Gaph is Empty");
        }
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if(util.Utility.equals(vertex.data, element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Singly Linked List Graph is Empty");
        }
        if(!containsVertex(a)||!containsVertex(b)) return false;
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if(util.Utility.equals(vertex.data, a)
             &&vertex.edgesList.contains(new EdgeWeight(b, null))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addVertex(Object element) throws GraphException, ListException {
        if(isEmpty()){
            vertexList.add(new Vertex(element));
        }else
            if(!containsVertex(element)){
                vertexList.add(new Vertex(element));
            }
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        addVertexEdgeWeight(a, b, null, "addEdge");
        addVertexEdgeWeight(b, a, null, "addEdge");
    }
    
    private void addVertexEdgeWeight(Object a, Object b, Object weight, String action) throws ListException{
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if(util.Utility.equals(vertex.data, a)){
                switch(action){
                    case "addEdge":
                        vertex.edgesList.add(new EdgeWeight(b, weight));
                        break;
                    case "addWeight":
                        vertex.edgesList.getNode(new EdgeWeight(b, null))
                                .setData(new EdgeWeight(b, weight));
                        break;
                    case "remove":
                        if(!vertex.edgesList.isEmpty()){
                            vertex.edgesList.remove(new EdgeWeight(b, weight));
                        }
                }
            }
            
        }
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is no edge between the vertexes "
                    + "["+a+"] and ["+b+"]");
        }
        addVertexEdgeWeight(a, b, weight, "addWeight");
        addVertexEdgeWeight(b, a, weight, "addWeight");
    }
    
    @Override
    public void addEdgeWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        addVertexEdgeWeight(a, b, weight, "addEdge");
        addVertexEdgeWeight(b, a, weight, "addEdge");
    }

    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        if(!vertexList.isEmpty()&&containsVertex(element)){
            for (int i = 1; i <= vertexList.size(); i++) {
                Vertex vertex = (Vertex) vertexList.getNode(i).data;
                if(util.Utility.equals(vertex.data, element)){ 
                    //suprime el vertice la lista de vertices
                    vertexList.remove(new Vertex(element));
                    //ahora se debe suprimir las aristas y pesos (si existen)
                    for (int j = 1; j <= vertexList.size(); j++) {
                        vertex = (Vertex) vertexList.getNode(j).data; 
                        addVertexEdgeWeight(vertex.data, element, null, "remove");
                    }
                }
            }
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot remove edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        addVertexEdgeWeight(a, b, null, "remove");
        addVertexEdgeWeight(b, a, null, "remove"); //grafo no dirigido
    }

    @Override
    public String toString() {
        String result = "SINGLY LINKED LIST GRAPH CONTENT...";
        try {
            for (int i = 1; i <= vertexList.size(); i++) {
                Vertex vertex = (Vertex) vertexList.getNode(i).data;
                result+="\nVextex in the position "+i+": "+vertex.data;
                if(!vertex.edgesList.isEmpty()){
                    result+="\n......EDGES AND WEIGHTS: "+vertex.edgesList.toString()+"\n";
                }
            }      
        } catch (ListException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    

     /**
    *____________RECORRIDOS POR GRAFOS
    */
    
    /***
     * RECORRIDO EN PROFUNDIDAD
     * @return 
     * @throws domain.graph.GraphException
     * @throws domain.stack.StackException
     * @throws domain.list.ListException
     */
    @Override
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false);//marca todos los vertices como no vistados
        // inicia en el vertice 1
        Vertex vertex = (Vertex)vertexList.getNode(1).data;
        String info =vertex+", ";
        vertex.setVisited(true); //lo marca
         stack.clear();
         stack.push(1); //lo apila
         while( !stack.isEmpty() ){
             // obtiene un vertice adyacente no visitado, 
             //el que esta en el tope de la pila
             int index = adjacentVertexNotVisited((int) stack.top());
             if(index==-1) // no lo encontro
                 stack.pop();
             else{
                 vertex = (Vertex)vertexList.getNode(index).data;
                 vertex.setVisited(true); // lo marca
                 info+=vertex+", ";
                 stack.push(index); //inserta la posicion
             }
         }
         return info;
	}//dfs

    /***
     * RECORRIDO POR AMPLITUD
     * @return 
     * @throws domain.graph.GraphException
     * @throws domain.queue.QueueException
     * @throws domain.list.ListException
     */
    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
        // inicia en el vertice 1
        Vertex vertex = (Vertex)vertexList.getNode(1).data;
        String info =vertex+", ";
        vertex.setVisited(true); //lo marca
         queue.clear();
         queue.enQueue(1); // encola el elemento
         int index2;
         while(!queue.isEmpty()){
             int index1 = (int) queue.deQueue(); // remueve el vertice de la cola
             // hasta que no tenga vecinos sin visitar
             while((index2=adjacentVertexNotVisited(index1)) != -1 ){
                 // obtiene uno
                vertex = (Vertex)vertexList.getNode(index2).data;
                vertex.setVisited(true); //lo marco
                 info+=vertex+", ";
                 queue.enQueue(index2); // lo encola
             } 
         }
    return info;
    }
    
    //setteamos el atributo visitado del vertice respectivo
    private void setVisited(boolean value) throws ListException {
        for (int i=1; i<=vertexList.size(); i++) {
            Vertex vertex = (Vertex)vertexList.getNode(i).data; 
            vertex.setVisited(value); //value==true or false
        }//for
    }
    
    private int adjacentVertexNotVisited(int index) throws ListException {
        Vertex vertex1 = (Vertex)vertexList.getNode(index).data;        
        for(int i=1; i<=vertexList.size(); i++){
            Vertex vertex2 = (Vertex)vertexList.getNode(i).data; 
	    if(!vertex2.edgesList.isEmpty()&&vertex2.edgesList
                .contains(new EdgeWeight(vertex1.data, null)) 
                && !vertex2.isVisited())
	             return i;   
        }
        return -1;
    }
    
    public Vertex getVertex(Object element) throws GraphException, ListException{
	if(containsVertex(element)){
            for (int i = 1; i <= vertexList.size(); i++) {
                Vertex vertex = (Vertex) vertexList.getNode(i).data;
                if(util.Utility.equals(vertex.data, element))
                    return vertex;
            }
        }
        return null; //no existe el vertice
    }

    /**
     * I-2020.Quiz No.5 
     * isValidPath(Object a, Object b)
     * @param a
     * @param b
     * @return 
     */
    @Override
    public boolean isValidPath(Object a, Object b) throws GraphException, ListException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * I-2020. Practica para el Segundo Examen Parcial 
     */
    
    /**
     * String getShortestDistance()
     * Devuelve un string con la información de la distancia mas corta y las 
     * ciudades que se conectan con esa distancia. El metodo se debe implementar 
     * para grafo matriz de adyacencia, grafo lista de adyacencia, grafo lista 
     * enlazada.
     * Ejemplo de salida por consola: Cities: H-A, distance: 10kms
     * @return 
     */
    public String getShortestDistance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * int totalKms()
     * devuelve un número entero que representa el total de kilómetros incluidos 
     * en el grafo del Sistema de Abastecimiento de Combustible
     * @return 
     */
    public int totalKms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
