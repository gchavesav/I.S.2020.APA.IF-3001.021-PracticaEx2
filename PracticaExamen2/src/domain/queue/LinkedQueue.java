/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.queue;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Cola enlazada sin nodo cabecera
 */
public class LinkedQueue implements Queue {
    private Node front; //apuntador al anterior/frente de la cola
    private Node rear; //apuntador al posterior/final de la cola
    private int count; //control de elementos encolados

    //Cosntructor
    public LinkedQueue() {
        front=rear=null;
        count=0;
    }
    
    @Override
    public int size() {
        return this.count;
    }

    @Override
    public void clear() {
        front=rear=null;
        count=0;
    }

    @Override
    public boolean isEmpty() {
        return front==null;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is Empty");
        }
        LinkedQueue aux = new LinkedQueue();
        int index = 1; //estructura dinamica
        int index2 = -1; //-1 significa que no existe
        while(!isEmpty()){
            if(util.Utility.equals(front(), element)){
                index2 = index; //ya lo encontro
            }
            aux.enQueue(deQueue());
            index++;
        }
        //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return index2;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);
        if(isEmpty()){ //la cola no existe
            rear = newNode; //encolo por el extremo posterior
            //garantizo que anterior quede apuntando al primer nodo
            front = rear;
        }else{ //que pasa si ya hay elementos encolados
            rear.next = newNode; //encolo por el extremo posterior
            rear = newNode; //muevo el apuntador a newNode
        }
        //al final actualzo el contador
        this.count++;
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is Empty");
        }
        Object element = front.data; //desencolo por el extremo anterior
        //Caso 1. Que pasa si solo hay un elemento encolado
        if(front==rear){
            clear(); //anulo la cola
        }else{ //Caso 2. Caso contrario
            front = front.next; //muevo front al sgte nodo
        }
        //actualizo el contador de elementos
        count--;
        return element; //retorno el elemento desencolado
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is Empty");
        }
        LinkedQueue aux = new LinkedQueue();
        boolean finded = false; //encontrado
        while(!isEmpty()){
            if(util.Utility.equals(front(), element)){
                finded = true; //ya lo encontro
            }
            aux.enQueue(deQueue());
        }
        //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return finded;
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is Empty");
        }
        return front.data;
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is Empty");
        }
        return front.data;
    }
    
    @Override
    public String toString() {
        String result = "Linked Queue Content:\n";
        try{
           LinkedQueue aux = new LinkedQueue();
           while(!isEmpty()){
               result+=front()+"\n";
               aux.enQueue(deQueue());
           }
           //al final dejamos la cola en su estado original
           while(!aux.isEmpty()){
               enQueue(aux.deQueue());
           }
           
        }catch(QueueException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
}
