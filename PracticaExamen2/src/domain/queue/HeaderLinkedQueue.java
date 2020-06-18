/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.queue;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class HeaderLinkedQueue implements Queue {
    private Node front; //apuntador al anterior/frente de la cola
    private Node rear; //apuntador al posterior/final de la cola
    private int count; //control de elementos encolados

    //Constructor
    public HeaderLinkedQueue(){
        //inicialmente van a apuntar a un nodo vacio/cabecera
        front=rear=new Node(); //apunta a un nodo cabecera
        count=0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        front=rear=new Node();
        count=0;
    }

    @Override
    public boolean isEmpty() {
        return front==rear;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Header Linked Queue is Empty");
        }
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
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
        rear.next = newNode;
        rear = newNode; //lo movemos al nuevo nodo
        count++; //al final incremento el contador
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Header Linked Queue is Empty");
        }
        Object element = front.next.data; //desencolo por el extremo anterior
        //Caso 1. Que pasa si solo hay un elemento encolado
        if(front.next==rear){
            clear(); //anulo la cola
        }else{ //Caso 2. Caso contrario
            front.next = front.next.next; //muevo front al sgte nodo
        }
        //actualizo el contador de elementos
        count--;
        return element; //retorno el elemento desencolado
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Header Linked Queue is Empty");
        }
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
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
        return front.next.data;
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is Empty");
        }
        return front.next.data;
    }
    
    @Override
    public String toString() {
        String result = "Header Linked Queue Content:\n";
        try{
           HeaderLinkedQueue aux = new HeaderLinkedQueue();
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
