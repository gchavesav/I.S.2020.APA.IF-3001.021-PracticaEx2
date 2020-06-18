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
public class ArrayStack implements Stack {
    private int n; //tam max de la pila
    private int top; //para el tope de la pila
    private Object stack[];

    //Constructor
    public ArrayStack(int n) {
        if(n<=0) System.exit(1); //se sale
        this.n = n;
        top = -1;
        stack = new Object[n];
    }
    
    @Override
    public int size() {
        return top+1;
    }

    @Override
    public void clear() {
        stack = null;
        stack = new Object[n];
    }

    @Override
    public boolean isEmpty() {
        return top==-1;
    }

    @Override
    public Object peek() throws StackException {
        if(isEmpty()){
            throw new StackException("Array Stack is Empty");
        }
        return stack[top];
    }

    @Override
    public Object top() throws StackException {
        if(isEmpty()){
            throw new StackException("Array Stack is Empty");
        }
        return stack[top];
    }

    @Override
    public void push(Object element) throws StackException {
        if(top==stack.length-1){
            throw new StackException("Array Stack is Full");
        }
        stack[++top] = element;
    }

    @Override
    public Object pop() throws StackException {
        if(isEmpty()){
            throw new StackException("Array Stack is Empty");
        }
        return stack[top--];
    }

    @Override
    public String toString() {
        String result = "Array Stack Content";
        try{
            ArrayStack aux = new ArrayStack(size());
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
