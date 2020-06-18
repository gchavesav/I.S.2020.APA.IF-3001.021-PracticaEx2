/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.stack;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class LinkedStack implements Stack {
    private Node top; //un apuntador al tope de la pila
    private int count; //contador de elementos

    //Constructor
    public LinkedStack() {
        top = null; //al apuntador a nulo
        count = 0;
    }
    
    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top==null;
    }

    @Override
    public Object peek() throws StackException {
        if(isEmpty()){
            throw new StackException("Linked Stack is Empty");
        }
        return top.data;
    }

    @Override
    public Object top() throws StackException {
        if(isEmpty()){
            throw new StackException("Linked Stack is Empty");
        }
        return top.data;
    }

    @Override
    public void push(Object element) throws StackException {
        Node newNode = new Node(element);
        if(isEmpty()){
            top = newNode;
        }else{
           newNode.next = top;
           top = newNode;
        }
        count++; //incremento el contador de elementos
    }

    @Override
    public Object pop() throws StackException {
        if(isEmpty()){
            throw new StackException("Linked Stack is Empty");
        }
        Object element = top.data;
        top = top.next; //lo movemos al sgte nodo
        count--;
        return element;
    }
    
     @Override
    public String toString() {
        String result = "Linked Stack Content:";
        try{
            LinkedStack aux = new LinkedStack();
            while(!isEmpty()){
                result+="\n"+peek();
                aux.push(pop());
            }
            
            //se sale cuando la pila esta vacia
            //entonces debemos dejar la pila como al inicio
            while(!aux.isEmpty()){
                push(aux.pop());
            }
            
        }catch(StackException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
}
