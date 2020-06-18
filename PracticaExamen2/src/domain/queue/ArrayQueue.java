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
public class ArrayQueue implements Queue {
    private int n; //el tam max de la cola
    private Object queue[]; //arreglo de objetos
    private int front, rear; //indices anterior y posterior

    //Contructor
    public ArrayQueue(int n) {
        if(n<=0) System.exit(1); //se sale
        this.n = n;
        this.queue = new Object[n];
        this.rear = n-1; //ult casilla del arreglo
        this.front = rear;
        
    }
    
    
    @Override
    public int size() {
        return rear-front;
    }

    @Override
    public void clear() {
        queue = new Object[n];
        rear = n-1; //ult casilla del arreglo
        front = rear;
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Array Queue is Empty");
        }
        ArrayQueue aux = new ArrayQueue(size());
        int index = 0; //arreglo estatico inicia en cero
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
        if(size()==queue.length){
            throw new QueueException("Array Queue is Full");
        }
        
        //la primera vez no entra al for
        for (int i = front; i < rear; i++) {
            queue[i] = queue[i+1];
        }
        queue[rear] = element;
        front--; //la idea es q front quede en un campo ario
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Array Queue is Empty");
        }
        return queue[++front];
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Array Queue is Empty");
        }
        ArrayQueue aux = new ArrayQueue(size());
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
            throw new QueueException("Array Queue is Empty");
        }
        return queue[front+1];
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Array Queue is Empty");
        }
        return queue[front+1];
    }

    @Override
    public String toString() {
        String result = "Array Queue Content:\n";
        try{
           ArrayQueue aux = new ArrayQueue(size());
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
