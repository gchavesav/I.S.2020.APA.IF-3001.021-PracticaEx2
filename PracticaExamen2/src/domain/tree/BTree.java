/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.tree;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * BTree = Binary Tree
 */
public class BTree implements Tree {
    private BTreeNode root; //representa la unica entrada al arbol

    //Constructor
    public BTree(){
        this.root = null;
    }
         
    
    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return size(root);
    }
    
    private int size(BTreeNode node){
        if(node==null) return 0;
        else
            return 1+size(node.left)+size(node.right);
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);
    }
    
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null) return false;
        else
            if(util.Utility.equals(node.data, element)){
                return true; //YA LO ENCONTRO
            }else
                return binarySearch(node.left, element)
                    || binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        //root = add(root, element);
        root = add(root, element, "root", 0);
    }
    
    private BTreeNode add(BTreeNode node, Object element){
        if(node==null){ //el arbol esta vacio
            node = new BTreeNode(element);
        }else
            if(node.left==null){
                node.left = add(node.left, element);
            }else
                if(node.right==null){
                    node.right = add(node.right, element);
                }else{ //debemos establecer algun criterio de insercion
                    //con un criterio aletario, decide como agregar
                    //el nuevo elemento
                    int num = util.Utility.random(10);
                    if(num%2==0){//si es par, inserta por la izq
                        node.left = add(node.left, element);
                    }else //sino inserta por la der
                        node.right = add(node.right, element);
                }
        return node;
    }
    
    private BTreeNode add(BTreeNode node, Object element, String label, int level){
        if(node==null){ //el arbol esta vacio
            node = new BTreeNode(element, label, level);
        }else
            if(node.left==null){
                node.left = add(node.left, element, label+"/left", ++level);
            }else
                if(node.right==null){
                    node.right = add(node.right, element, label+"/right", ++level);
                }else{ //debemos establecer algun criterio de insercion
                    //con un criterio aletario, decide como agregar
                    //el nuevo elemento
                    int num = util.Utility.random(10);
                    if(num%2==0){//si es par, inserta por la izq
                        node.left = add(node.left, element, label+"/left", ++level);
                    }else //sino inserta por la der
                        node.right = add(node.right, element, label+"/right", ++level);
                }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        root =remove(root, element);
    }
    
    private BTreeNode remove(BTreeNode node, Object element){
        if(node!=null){
            if(util.Utility.equals(node.data, element)){
                //CASO 1. EL NODO A SUPRIMIR ES UN NODO SIN HIJOS
                //EN ESTE CASO, EL NODO A SUPRMIR ES UNA HOJA
                if(node.left==null&&node.right==null){
                    return node=null;
                }else
                    //CASO 2. EL NODO A SUPRIMIR SOLO TIENE UN HIJO
                    //EN ESE CASO, EL NODO A SUPRIMIR CON EL DATA A ELIMINAR
                    //ES REEMPLAZADO POR EL HIJO
                    if(node.left==null&&node.right!=null){
                        node = node.right;
                    }else
                        if(node.left!=null&&node.right==null){
                            node = node.left;
                        }else
                           //CASO 3 EL NODO A SUPRIMIR TIENE 2 HIJOS
                            if(node.left!=null&&node.right!=null){
                                //OBTENGA UNA HOJA DEL SUBARBOL DERECHO
                                Object value =getLeaf(node.right);
                                node.data = value;
                                node.right = removeLeaf(node.right, value);
                            }
            }//equals(node.data, element))
            //SI NO HEMOS ENCONTRADO EL NODO CON EL DATA A SUPRIMIR
            //TENEMOS QUE ASEGURARNOS QUE BUSQUE EN TODO EL ARBOL
            node.left = remove(node.left, element);
            node.right = remove(node.right, element);
        }//node!=null
        return node;
    }
    
    private Object getLeaf(BTreeNode node){
        Object aux;
        if(node==null) return null;
        else 
            if(node.left==null&&node.right==null){
                return node.data; //es una hoja
            }else{
                aux = getLeaf(node.left);
                if(aux==null){ 
                  //quiere decir q todavia no ha encontrado una hoja
                  aux = getLeaf(node.right);  
                }
            }
        return aux; 
    }
    
    private BTreeNode removeLeaf(BTreeNode node, Object element){
        if(node==null) return null;
        else
            if(node.left==null&&node.right==null
               &&util.Utility.equals(node.data, element)){
                //ES UNA HOJA Y ES LA QUE ANDO BUSCANDO, 
                //YA LA PUEDO ELIMINAR
                return null;
            }else{
                node.left = removeLeaf(node.left, element);
                node.right = removeLeaf(node.right, element);
        }
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return height(root, element, 0);
    }
    private int height(BTreeNode node, Object element, int count){
        if(node==null) return 0;
        else
            if(util.Utility.equals(node.data, element)){
                return count;
            }else
                //AQUI ES DONDE DEBERÍA BUSCAR POR LA IZQ O DER
                return Math.max(
                        height(node.left, element, ++count),
                        height(node.right, element, count)
                        );
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return height(root)-1;
    }
    
    private int height(BTreeNode node){
        if(node==null) return 0;
        else
            return Math.max(
                    height(node.left), 
                    height(node.right))+1;
    }

    @Override
    public Object min() throws TreeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object max() throws TreeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return "PreOrder Transversal Tour: "
                +preOrder(root);
    }
    
    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            //result = node.data+", ";
            result = node.data
                    +"("+node.label
                    +", "+node.level
                    +"), ";
            result+=preOrder(node.left);
            result+=preOrder(node.right);
        }
        return result;
    }

    @Override
    public String InOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return "InOrder Transversal Tour: "
                +inOrder(root);
    }
    
    //left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result=inOrder(node.left);
            result+=node.data+", ";
            result+=inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return "PostOrder Transversal Tour: "
                +postOrder(root);
    }
    
    //left-right-node
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result=postOrder(node.left);
            result+=postOrder(node.right);
            result+=node.data+", ";
        }
        return result;
    }

    @Override
    public String toString() {
        if(isEmpty()){
            return "Binary Tree is empty";
        }
        String result="BINARY TREE TOUR...";
        result+="\nPreOrder: "+preOrder(root);
        result+="\nInOrder: "+inOrder(root);
        result+="\nPostOrder: "+postOrder(root);
        return result;
    }
    
    /**
     * I-2020. Practica para el Segundo Examen Parcial 
     */

    /**
     * BTree btNodeSum(BTree t1, BTree t2)
     * Devuelve un árbol que suma el contenido de los nodos de los arboles 
     * binarios 1 y 2 según corresponda.Si el nodo no existe en alguno de los 
     * arboles, conserva el valor del nodo existente
     * @param t1
     * @param t2
     * @return 
     * @throws domain.tree.TreeException 
     */
    public BTree btNodeSum(BTree t1, BTree t2) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        BTree tree = new BTree();
        tree.root = btNodeSum(t1.root, t2.root, "root");
        return tree;
    }
    
    public BTreeNode btNodeSum(BTreeNode node1, BTreeNode node2, String label){
        if(node1==null){
            if(node2==null) return null;
            else return new BTreeNode(node2.data, 
                    btNodeSum(null, node2.left, label+"/left"), 
                    btNodeSum(null, node2.right, label+"right"), 
                    label);
        }else //entra si node1!=null
            if(node2==null){
                return new BTreeNode(node1.data, 
                    btNodeSum(node1.left, null, label+"/left"),
                    btNodeSum(node1.right, null, label+"/right"),
                    label);
            }else{
                return new BTreeNode((int)node1.data+(int)node2.data,
                        btNodeSum(node1.left, node2.left, label+"/left"),
                        btNodeSum(node1.right, node2.right, label+"/right"),
                        label);
            }
    }

    public boolean isABM() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return isABM(root, (Integer) root.data);
    }

    /**
     * Recorre cada nodo y verifica que sus hijos sean igual o mayores que el valor
     * que contiene el nodo seleccionado.
     * 
     * @author Jeison Araya Mena | B90514
     * 
     * @param node actual.
     * @param value valor del nodo padre.
     * 
     * @return {@code true} es igual o mayores a su nodo padre,{@code false} es menor a su nodo padre.
     */
    private boolean isABM(BTreeNode node, Integer value) {
        // Caso base -> Es una hoja   
        if (node == null) {
            return true;
        }
        // Caso base -> Si lo que tiene este nodo es menor a value, no es válido.
        if((Integer) node.data < value)
            return false;
        // Caso recursivo
        // Recorrer todos los hijos de este nodo.
        return isABM(node.left, (Integer) node.data) && isABM(node.right, (Integer) node.data);
    }


    /**
     * BTree joinABM(BTree t1, BTree t4)
     * devuelve un nuevo arbol ABM como resultado de la unión de los árboles 
     * binarios ABM "a" y "b". Como parte de la solución deberá comprobar que 
     * los árboles binarios recibidos como parámetros también sean ABM.
     * @param t1
     * @param t4
     * @return 
     */
    public BTree joinABM(BTree t1, BTree t4) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * BTree btNodeSum()
     * Recibe un árbol binario simple de numeros enteros y devuelve un arbol 
     * binario simple de numeros enteros en el que cada uno de sus nodos 
     * contiene la suma del elemento del nodo, junto con la suma de todos los 
     * elementos de sus nodos descendientes.
     * @author Milena Rojas
     * @return 
     * @throws domain.tree.TreeException 
     */
   public BTree btNodeSum() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        BTree tree = new BTree();
        tree.root = btNodeSum(root);
        return tree;
    }

    private BTreeNode btNodeSum(BTreeNode nodo) {
        if (nodo.left == null && nodo.right == null) {
            return nodo;
        } else if (nodo.left != null && nodo.right != null) {
            nodo.data = (int) nodo.data + (int) btNodeSum(nodo.left).data + (int) btNodeSum(nodo.right).data;
        } else if (nodo.left != null && nodo.right == null) {
            nodo.data = (int) nodo.data + (int) btNodeSum(nodo.left).data;
        } else if (nodo.left == null && nodo.right != null) {
            nodo.data = (int) nodo.data + (int) btNodeSum(nodo.right).data;
        }
        return nodo;
    }
    
}
