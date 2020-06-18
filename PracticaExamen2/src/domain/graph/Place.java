/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.graph;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A
 */
public class Place {
    private String name;
    
    //Constructor
    public Place(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Place{" + "name=" + name + '}';
    }
    
    
}
