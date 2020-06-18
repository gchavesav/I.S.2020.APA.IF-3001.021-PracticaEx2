/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.tree;

import org.testng.annotations.Test;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class BTreeNGTest {
    
    public BTreeNGTest() {
    }

    @Test
    public void testSomeMethod() {
        try{
            BTree t1 = new BTree();
            t1.add(2); t1.add(5); t1.add(4);
            t1.add(5); t1.add(7); t1.add(8);
            System.out.println("BTREE-1..."
                     +t1.toString());
            BTree t2 = new BTree();
            t2.add(10); t2.add(3); t2.add(15);
            t2.add(2); t2.add(1); t2.add(4); t2.add(6);
            System.out.println("BTREE-2..."
                     +t2.toString());

            BTree t3 = t1.btNodeSum(t1, t2);
            System.out.println("BTREE NODE SUM T1 AND T2..."
                     +t3.toString());

            System.out.println("\nBTREE-1 IS ABM? "
                     +t1.isABM());
            System.out.println("\nBTREE-2 IS ABM? "
                     +t2.isABM());
            
            BTree t4 = new BTree();
            t4.add(10); t4.add(12); t4.add(15); t4.add(17);
            t4.add(19); t4.add(23); t4.add(30);
            System.out.println("BTREE-4..."
                     +t4.toString());
            System.out.println("\nBTREE-4 IS ABM? "
                     +t4.isABM());
//            
//            BTree t5 = t1.joinABM(t1, t4);
//            System.out.println("\nJOIN ABM T1 AND T4..."
//                    +t5.toString());
            
            System.out.println("BTREE-1..."
                     +t1.toString());
            System.out.println("BTREE-1 NODE SUM..."
                    +t1.btNodeSum());
            System.out.println("BTREE-2 NODE SUM..."
                    +t2.btNodeSum());
            System.out.println("BTREE-3 NODE SUM..."
                    +t3.btNodeSum());
//            System.out.println("BTREE-4 NODE SUM..."
//                    +t4.btNodeSum());
//            System.out.println("BTREE-5 NODE SUM..."
//                    +t5.btNodeSum());
              
        }catch(TreeException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
