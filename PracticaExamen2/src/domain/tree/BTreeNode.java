/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.tree;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class BTreeNode {
    public Object data;
    public BTreeNode left, right;
    //BTree
    public String label; //root/left/right
    public int level; //0(root), 1, 2, 3, ....
    
    //Constructor #1
    public BTreeNode(Object data){
        this.data = data;
        this.left = this.right = null;
    }
    
    //Contructor #2
    public BTreeNode(Object data, String label, int level){
        this.data = data;
        this.label = label;
        this.level = level;
        this.left = this.right = null;
    }

    //Constructor #3
    public BTreeNode(Object data, BTreeNode left, BTreeNode right, String label) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.label = label;
    }
    
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BTreeNode getLeft() {
        return left;
    }

    public void setLeft(BTreeNode left) {
        this.left = left;
    }

    public BTreeNode getRight() {
        return right;
    }

    public void setRight(BTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BTreeNode{" + "data=" + data + '}';
    }
    
}
