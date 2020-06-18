/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.graph;
import domain.list.ListException;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Grafo Matriz de Adyacencia
 */
public class AdjacencyMatrixGraph implements Graph {
    private int n;
    private Vertex vertexList[];
    private Object adjacencyMatrix[][];
    private int count; //contador de vertices
    
    //para los recorridos dfs, bfs
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public AdjacencyMatrixGraph(int n){
        if(n<=0) System.exit(1);
        this.n = n;
        this.count = 0;
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = new Object[n][n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
        initMatrix();
    }
    
    private void initMatrix(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        this.vertexList = null;
        this.initMatrix();
        this.count = 0;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException {
        if(isEmpty()){
            throw new GraphException("Adjacency Matrix Graph is Empty");
        }
        for (int i = 0; i < count; i++) {
            if(util.Utility.equals(vertexList[i].data, element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException {
        if(isEmpty()){
            throw new GraphException("Adjacency Matrix Graph is Empty");
        }
        return !util.Utility.equals(adjacencyMatrix[indexOf(a)][indexOf(b)], 0);
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
            throw new GraphException("Adjacency Matrix Graph is Full");
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
        adjacencyMatrix[indexOf(a)][indexOf(b)] = 1;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = 1; //grafo no dirigido
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is no edge between the vertexes "
                    + "["+a+"] y ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = weight; //grafo no dirigido
    }
    
    @Override
    public void addEdgeWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = weight; //grafo no dirigido
    }
    
    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        int index = indexOf(element);
        if(index!=-1){ //si el vertice existe
            for (int i = index; i < count-1; i++) {
                vertexList[i] = vertexList[i+1];
                //System.arraycopy(adjacencyMatrix[i+1], 0, adjacencyMatrix[i], 0, count);
                //movemos todas las filas, una pos hacia arriba
                for (int j = 0; j < count; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i+1][j];
                }
            }
            //movemos todas las columnas una posicion a la izq
            for (int i = 0; i < count; i++) {
                for (int j = index; j < count-1; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i][j+1];
                }//for j
            }//for i
            count--; //se decrementa por el vertice suprimido
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("There's no some of the vertexes");
            //throw new GraphException("Alguno o ninguno de los vértices existe");
        }
        int i = indexOf(a);
        int j = indexOf(b);
        if(i!=-1&&j!=-1){
            adjacencyMatrix[i][j] = 0;
            adjacencyMatrix[j][i] = 0;
        }
    }
    
    @Override
    public String toString(){
        String result = "ADJACENCY MATRIX GRAPH CONTENT...";
        for (int i = 0; i < count; i++) {
            result+="\nThe vertex in the position: "+i+" is: "+vertexList[i].data;
        }
        result+="\n";
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if(!adjacencyMatrix[i][j].equals(0)){ //si existe una arista
                    result+="\nThere is edge between the vertexes:  "+vertexList[i].data+"......"
                            +vertexList[j].data;
                    if(!adjacencyMatrix[i][j].equals(1)){
                        result+="_____WEIGHT: "+adjacencyMatrix[i][j];
                    }
                }
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
     */
    @Override
    public String dfs() throws GraphException, StackException {
        setVisited(false);//marca todos los vertices como no vistados
        // inicia en el vertice 0
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
                 info+=vertexList[index].data+", "; //lo muestra
                 stack.push(index); //inserta la posicion
             }
         }
         return info;
	}

    /***
     * RECORRIDO POR AMPLITUD
     * @return 
     * @throws domain.graph.GraphException
     */
    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
         // inicia en el vertice 0
         String info =vertexList[0].data+", ";
         vertexList[0].setVisited(true); // lo marca
         queue.clear();
         queue.enQueue(0); // encola el elemento
         int v2;
         while(!queue.isEmpty()){
             int v1 = (int) queue.deQueue(); // remueve el vertice de la cola
             // hasta que no tenga vecinos sin visitar
             while((v2=adjacentVertexNotVisited(v1)) != -1 ){
                 // obtiene uno
                 vertexList[v2].setVisited(true); // lo marca
                 info+=vertexList[v2].data+", "; //lo muestra
                 queue.enQueue(v2); // lo encola
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
    
    private int adjacentVertexNotVisited(int index) {
        for (int i = 0; i < count; i++) {
            if(!adjacencyMatrix[index][i].equals(0)
               && !vertexList[i].isVisited())
                return i;//retorna la posicion del vertice adyacente no visitado
        }//for i
        return -1;
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
