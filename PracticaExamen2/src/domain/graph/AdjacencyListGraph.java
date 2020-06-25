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
 * Grafo Lista de Adyacencia
 */
public class AdjacencyListGraph implements Graph {
    private int n;
    private Vertex vertexList[];
    private int count; //contador de vertices
    
    //para los recorridos dfs, bfs
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public AdjacencyListGraph(int n){
        if(n<=0) System.exit(1);
        this.n = n;
        this.count = 0;
        this.vertexList = new Vertex[n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
    }
    
    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        this.vertexList = null;
        this.count = 0;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException {
        if(isEmpty()){
            throw new GraphException("Adjacency List Graph is Empty");
        }
        for (int i = 0; i < count; i++) {
            if(util.Utility.equals(vertexList[i].data, element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Adjacency List Graph is Empty");
        }
        return !vertexList[indexOf(a)].edgesList.isEmpty()
                ?vertexList[indexOf(a)].edgesList.contains(new EdgeWeight(b, null))
                :false;
    }
    
    private int indexOf(Object element){
        for (int i = 0; i < count; i++) {
            if(util.Utility.equals(vertexList[i].data, element)){
                return i;
            }
        }
        return -1; //significa q el data del vertice no existe
    }

    @Override
    public void addVertex(Object element) throws GraphException {
        if(count>=vertexList.length){
            throw new GraphException("Adjacency List Graph is Full");
        }
        if(isEmpty()){
            vertexList[count++] = new Vertex(element);
        }else
            if(!containsVertex(element)){
                vertexList[count++] = new Vertex(element);
            }
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, null));
        //grafo no dirigido
        vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, null));
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is no edge between the vertexes "
                    + "["+a+"] and ["+b+"]");
        }
        EdgeWeight ew = (EdgeWeight) vertexList[indexOf(a)].edgesList
                .getNode(new EdgeWeight(b, null)).getData();
        ew.setWeight(weight);
        vertexList[indexOf(a)].edgesList.getNode(new EdgeWeight(b, null))
                .setData(ew);
        //grafo no dirigido
        ew = (EdgeWeight) vertexList[indexOf(b)].edgesList
                .getNode(new EdgeWeight(a, null)).getData();
        ew.setWeight(weight);
        vertexList[indexOf(b)].edgesList
                .getNode(new EdgeWeight(a, null)).setData(ew);
    }
    
    @Override
    public void addEdgeWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, weight));
        //grafo no dirigido
        vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, weight));
    }
    
    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        if(containsVertex(element)){
            for (int i = 0; i < count; i++) {
                if(util.Utility.equals(vertexList[i].data, element)){
                    //se deben suprimir todas las aristas asociadas
                    for (int j = 0; j < count; j++) {
                        if(containsEdge(vertexList[j].data, element)){
                            removeEdge(vertexList[j].data, element);
                        }
                    }
                 //se debe suprimir el vertice
                    for (int j = i; j < count-1; j++) {
                        vertexList[j] = vertexList[j+1];
                        
                    }
                    count--; //por el vertice suprimido
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
        if(!vertexList[indexOf(a)].edgesList.isEmpty()){
            vertexList[indexOf(a)].edgesList.remove(new EdgeWeight(b, null));
        }
        //grafo no dirigido
        if(!vertexList[indexOf(b)].edgesList.isEmpty()){
            vertexList[indexOf(b)].edgesList.remove(new EdgeWeight(a, null));
        }
    }
    
    @Override
    public String toString(){
         String result = "ADJACENCY LIST GRAPH CONTENT...";
            for (int i = 0; i < count; i++) {
                result+="\n\nVextex in the position "+i+": "+vertexList[i].data;
                if(!vertexList[i].edgesList.isEmpty()){
                    result+="\n......EDGES AND WEIGHTS: "+vertexList[i].edgesList.toString();
                    //result+="\n......PESOS: "+vertexList[i].edgesList.toString();
                }
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
        String info =vertexList[0].data+", ";
         vertexList[0].setVisited(true); // lo marca
         stack.clear();
         stack.push(0); //lo apila
         while( !stack.isEmpty() ){
             // obtiene un vertice adyacente no visitado, 
             //el que esta en el tope de la pila
             int index = adjacentVertexNotVisited((int) stack.top());
             if(index==-1) // no lo encontro
                 stack.pop();
             else{
                 vertexList[index].setVisited(true); // lo marca
                 info+=vertexList[index].data+", ";
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
         // inicia en el vertice 0
         String info =vertexList[0].data+", ";
         vertexList[0].setVisited(true); // lo marca
         queue.clear();
         queue.enQueue(0); // encola el elemento
         int index2;
         while(!queue.isEmpty()){
             int index1 = (int) queue.deQueue(); // remueve el vertice de la cola
             // hasta que no tenga vecinos sin visitar
             while((index2=adjacentVertexNotVisited(index1)) != -1 ){
                 // obtiene uno
                 vertexList[index2].setVisited(true); // lo marca
                 info+=vertexList[index2].data+", ";
                 queue.enQueue(index2); // lo encola
             } 
         }
    return info;
    }
    
    //setteamos el atributo visitado del vertice respectivo
    private void setVisited(boolean value) {
        for (int i = 0; i < count; i++) {
            vertexList[i].setVisited(value); //value==true o false
        }//for
    }
    
    private int adjacentVertexNotVisited(int index) throws ListException {
        Object vertexData = vertexList[index].data;
        for(int i=0; i<count; i++)
	    if(!vertexList[i].edgesList.isEmpty()
              &&vertexList[i].edgesList
                .contains(new EdgeWeight(vertexData, null)) 
                && !vertexList[i].isVisited())
	             return i;
	     return -1;
    }
    
    public Vertex getVertex(int index) throws GraphException, ListException{
        for (int i = 0; i < count; i++) {
            if(i==index){
                return this.vertexList[i];
            }//if
        }//for
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
    public boolean isValidPath(Object a, Object b) throws GraphException, ListException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * I-2020. Practica para el Segundo Examen Parcial 
     */
    
    /**
     * Devuelve un string con la información de la distancia mas corta y las 
     * ciudades que se conectan con esa distancia.El metodo se debe implementar 
     * para grafo matriz de adyacencia, grafo lista de adyacencia, grafo lista 
     * enlazada.Ejemplo de salida por consola: Cities: H-A, distance: 10kms
     * @author Rita Ortega
     * @return
     * @throws domain.graph.GraphException 
     * @throws domain.list.ListException 
     */
     public String getShortestDistance() throws GraphException, ListException {
        if (isEmpty()) {
            throw new GraphException("Adjacency List Graph is Empty");
        }
        Vertex vertex = vertexList[0];
        String city1 = "";
        String city2 = "";
        int sd1 = getShortestWeight(vertex.edgesList);
        for (int i = 1; i < count; i++) {
            vertex = vertexList[i];
            int sd2 = getShortestWeight(vertex.edgesList);
            if (sd1 > sd2) {
                sd1 = sd2;
                city1 = vertex.data.toString();
                city2 = getShortestEdge(vertex.edgesList);
            }
        }
        return "Cities: "+city1+"-"+city2+", distance: "+sd1+"kms";
    }
     
    private int getShortestWeight(SinglyLinkedList list) throws ListException {
        EdgeWeight b = (EdgeWeight) list.getNode(1).data;
        int a = Integer.parseInt(b.getWeight().toString());
        for (int i = 2; i < list.size(); i++) {
            EdgeWeight w = (EdgeWeight) list.getNode(i).data;
            int p = Integer.parseInt(w.getWeight().toString());
            if (p < a)
                a = p;
        }
        return a;
    }
    
    private String getShortestEdge(SinglyLinkedList list) throws ListException {
        EdgeWeight b = (EdgeWeight) list.getNode(1).data;
        int a = Integer.parseInt(b.getWeight().toString());
        String s = "";
        for (int i = 2; i < list.size(); i++) {
            EdgeWeight w = (EdgeWeight) list.getNode(i).data;
            int p = Integer.parseInt(w.getWeight().toString());
            if (p < a){
                a = p;
                s = w.getEdge().toString();
            }
        }
        return s;
    }


    /**
     * int totalKms()
     * devuelve un número entero que representa el total de kilómetros incluidos 
     * en el grafo del Sistema de Abastecimiento de Combustible
     * @author Ian Ondy
     * @return 
     * @throws domain.graph.GraphException 
     * @throws domain.list.ListException 
     */
    public int totalKms() throws GraphException, ListException {
        int totalR = 0;
        if(vertexList.length <= 0) throw new GraphException("Graph is empty");
        for (int i = 0; i < count; i++) {
            if(vertexList[i].edgesList.isEmpty()) 
                throw new GraphException("NO EDGES OR WEIGHTS FOUND");
            else{
                totalR += getTotalW(vertexList[i].edgesList);
            }//end else
            
        }//end for        
        return totalR/2;
    }//end totalKMS
    
    //Se separa en este método para no perder el dato del recorrido, pues sino daría NullPointerException
    private int getTotalW(SinglyLinkedList list) throws ListException{ 
        int total=0;
        for (int i = 1; i <= list.size(); i++) {
              EdgeWeight ew = (EdgeWeight)list.getNode(i).data;
              total+=(int)ew.getWeight();
        }
        return total;
    }
    
}
