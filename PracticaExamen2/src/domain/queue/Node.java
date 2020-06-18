/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.queue;

/**
 *
 * @author Profesor Lic. Gilberth Chaves Avila
 */
public class Node {
    public Object data; //objeto almacenado
    public int priority; //1=low, 3=high
    public Node next; //apuntador al sgte nodo
    
    //Constructor
    public Node(Object data){
        this.data = data;
        this.next = null;
    }
    
    //Constructor sobrecargado
    public Node(){
        this.next = null;
    }
    
    //Constructor sobrecargado #2
    public Node(Object data, int priority){
        this.data = data;
        this.priority = priority;
        this.next = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    
    
}
