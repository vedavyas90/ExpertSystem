/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prolog.ebg;

import java.util.ArrayList;

/**
 *
 * @author vyasa
 */




public class Unify {
    ArrayList<String> constants=new ArrayList<String>();
    ArrayList<String> variables=new ArrayList<String>();
    ArrayList<String> lists=new ArrayList<String>();
    
    String unify(String E1,String E2)
    {  
      //if( (constants.contains(E1) && constants.contains(E2)) || (E1 == null && E2 == null) )// base case
        if (E1 == null ? E2 == null : E1.equals(E2)) 
               return null;
          else 
               return "FAIL";
    }
    
    String unify(String E1,ArrayList<String> E2){
        //if (variables.contains(E1) && lists.contains(E2)){
            if (E2.contains(E1))
                 return "FAIL";
            else{
               
                return "Substitue E2 with E1";
            }
        
        //}
    }
    
    String unify(ArrayList<String> E1,String E2){
        //if (variables.contains(E2) && lists.contains(E1)){
            if (E1.contains(E2))
                 return "FAIL";
            else 
                 return "Substitue E1 with E2";
        
        //}
    }
      //else                //              % both E1 and E2 are lists
      //  {
     String unify(ArrayList<String> E1,ArrayList<String> E2){  
          String HE1 = E1.get(0);
          String HE2 = E2.get(0);
          String SUBS1 = unify(HE1, HE2);            //%   recursion
          if( "FAIL".equals(SUBS1)) 
              return "FAIL";
          String TE1 = apply(SUBS1, E1);
          String TE2 = apply(SUBS1, E2);
          String SUBS2 = unify(TE1, TE2);       //     %  recursion
          if ("FAIL".equals(SUBS2) )
              return "FAIL";
          else 
               return composition(SUBS1, SUBS2);
    }
    
    private String composition(String SUBS1, String SUBS2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String apply(String SUBS1, ArrayList<String> E1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}