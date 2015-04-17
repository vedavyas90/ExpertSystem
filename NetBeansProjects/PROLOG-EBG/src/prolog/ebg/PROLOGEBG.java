package prolog.ebg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author vyasa
 */
   

public class PROLOGEBG {

     
     
        ArrayList<String> Rules =new ArrayList<String>();
        ArrayList<String> Facts =new ArrayList<String>();
        ArrayList<String> Goals =new ArrayList<String>();
        Map<String,ArrayList<ArrayList<String>>> prules=new HashMap<String,ArrayList<ArrayList<String>>>();
        Set<String> Functions=new HashSet<String>();
        Set<String> Constants =new HashSet<String>();
        ArrayList<String> Variables =new ArrayList<String>();
        
    
    public PROLOGEBG(String ipfile) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     System.out.println("Stage one on: Loading the Knowledge Base .....");
        
        try {
			System.out.println("Welcome to EBG");
                        File file = new File(ipfile);
			FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			if(line.equals("Rules:")){
                            System.out.println("Loading the rules from KB");
                            line = bufferedReader.readLine();
                            while(!"Facts:".equals(line)){
                                    String rule1=line;
                                    System.out.println(rule1);
                                    Rules.add(rule1);
                                    line = bufferedReader.readLine();
                                }}
 			if(line.equals("Facts:")){
                            System.out.println("Loading the Facts from KB");
                            line = bufferedReader.readLine();
                            while(!"Goals:".equals(line)){
                                    String fact1=line;
                                    System.out.println(fact1);
                                    Facts.add(fact1);
                                    line = bufferedReader.readLine();
                                }}
                        if(line.equals("Goals:")){
                            //goals will be given in csf "Mortal(Ram),Mortal(Sita)";
                            System.out.println("Loading the Goals from KB");
                            line = bufferedReader.readLine();
                            while(line!=null){
                                String goal =line;
                                String delimiter2 =",";
                                String[] temp;
                                if(goal.contains(",")){
                                    temp=goal.split(delimiter2);
                                    for (int i=0;i<temp.length;i++){
                                        Goals.add(temp[i]);
                                    }
                                }
                                else{
                                        Goals.add(goal);    
                                    }
                                line = bufferedReader.readLine();
                            }
                            System.out.println("The number of goals:"+Goals.size());
                            for(String c:Goals){
                                System.out.println(c);
                            }
			}
                        }
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
        
    private void processRules() {
        
        String[] LRtemp,temp;
        String temp2=null;
        String Head=null;
        int i=0,k=0;//counts the number of rules with same head.
        
        ArrayList<ArrayList<String>> Tails=new ArrayList<ArrayList<String>>();
        ArrayList<String> Tail=new ArrayList<String>();

        System.out.println("Stage 2: processing rules....");
        
        for(String R : Rules){
            //Tail.clear();//clear the tail
            
            LRtemp = R.split(":-");
            
            Head=LRtemp[0];//head part
            temp2=LRtemp[1];//tail part
            
            System.out.println("processing Rule "+R);
            //System.out.println("Conlusion part of ("+R+") is :"+Head);
            
            if(!prules.containsKey(Head)){//if already key exists for the head , just add the tail in new list for the same key.
                
                k=0;
                //System.out.println("curretly the list of lists of tails has size:"+k);
                if(temp2.contains(",")){
                temp=temp2.split(",");
                for (i=0;i<temp.length;i++){
                    //System.out.println("premises part of ("+R+") has :"+temp[i]);
                    //System.out.println(Tail);
                    //System.out.println(prules.get(Head)); 
                    //Tails[k].add(temp[i]);
                     Tail.add(temp[i]);
                    //System.out.println(k+" after "+Tail);
                }
             }
             else if(!temp2.contains(",")) {
                //System.out.println(temp2);
                Tail.add(temp2);    
                //System.out.println(k+" "+Tail);
                //System.out.println(" premises part of ("+R+") is :"+temp2);
             }
             Tails.add(k,Tail );
             prules.put(Head,Tails);
             //System.out.println(k+" "+prules.get(Head));
                
            }else if(prules.containsKey(Head))
            {
                ArrayList<String> Tailtemp=new ArrayList<String>();
                k=k+1;
                if(temp2.contains(",")){
                temp=temp2.split(",");
                for (i=0;i<temp.length;i++){
                    //System.out.println("premises part of ("+R+") has :"+temp[i]);
                    //System.out.println(Tailtemp);
                    //System.out.println(prules.get(Head)); 
                    //Tails[k].add(temp[i]);
                     Tailtemp.add(temp[i]);
                    //System.out.println(k+" after "+Tailtemp);
                }
                }
                else if(!temp2.contains(",")) {
                    Tailtemp.add(temp2);    
                    //System.out.println("premises part of ("+R+") is :"+temp2);
                }
                prules.get(Head).add(k,Tailtemp);
                //System.out.println(k+" and "+prules.get(Head));
            }
            System.out.println(Head);
            for(i=0;i<prules.get(Head).size();i++)
                System.out.println("---> "+prules.get(Head).get(i));
        }
        
    }        
    
    private void constGen() {
         
        System.out.println("Stage 3: determining constants and variables....");
        
        
        for(String s:Facts){
        String con=s.substring(s.indexOf('(')+1, s.indexOf(')'));//"Ram","Sita";
        Constants.add(con);
        }
         System.out.println("Constants found :");
        for(String c:Constants){
            System.out.println(c);
        }
        String Var1="X";
        Variables.add(Var1); 
    }
     private boolean solveGoals(ArrayList<String> tempGoals) {
        ArrayList<String> newGoals=new ArrayList<String>();
        boolean flag=true;
        /*
        System.out.println("Stage 4: Goal solving");
        int flag=1;
        System.out.println("Goals stored are:"); 
        for(String g: tempGoals){
            //goalCheck(g);
            if(g.substring(g.indexOf("(")+1, g.indexOf(")")).matches("[A-Z][a-zA-Z]*")){
                  System.out.println("answering query:"+g);
            }
            else if(g.substring(g.indexOf("(")+1, g.indexOf(")")).matches("[a-z][a-zA-Z]*")){
                  System.out.println("Yes or No query:"+g);  
                    }    
        }
        System.out.println("Facts stored are:"); 
        for(String f: Facts){   
            System.out.println(f);
        }
        System.out.println("Goal Tree");
        System.out.println("L0:Root");
        System.out.println("L1:primary goals");
        for(String g: tempGoals){   
            System.out.println(g);
        }
        */
        for(String g: tempGoals){  
            if(Facts.contains(g)){
                System.out.println("direct goal ,matching fact found :"+g);
                flag= true;    
            }
            else if(!Facts.contains(g)){
                System.out.println("you cant obtain this goal directly from given facts: "+g);    
                String funct=g.substring(0,g.indexOf("("));
                String cons=g.substring(g.indexOf("(")+1,g.indexOf(")"));
                
                //System.out.println("goal has fuction "+funct +" and with constanct "+cons);    
                String ruleKey=null;
                Iterator itr=prules.entrySet().iterator();
                while(itr.hasNext()){
                  Map.Entry<String,ArrayList<ArrayList<String>>> mapEntry=(Map.Entry<String,ArrayList<ArrayList<String>>>)itr.next();
                  String str=mapEntry.getKey();
                  if(str.contains(funct))
                  {
                      ruleKey=str;
                  }
                }
                if(prules.containsKey(ruleKey))
                {
                    
                   System.out.println("Rule found");
                   System.out.println("New goals ");
                   System.out.println("L2:");
                   System.out.println("size of rule base :-"+prules.size());
                   System.out.println("matched rules in rule base :-"+prules.get(ruleKey));
                   for(int j=0;j<prules.get(ruleKey).size();j++){
                        ArrayList<ArrayList<String>> premises=prules.get(ruleKey);
                        
                        for(int n=0;n<premises.get(j).size();n++){
                            String var=premises.get(j).get(n).substring(premises.get(j).get(n).indexOf("(")+1, premises.get(j).get(n).indexOf(")"));
                            String ngoal=premises.get(j).get(n);
                            newGoals.add(ngoal.replaceAll(var,cons));
                            flag= solveGoals(newGoals);
                             System.out.println(newGoals);
                             newGoals.clear();
                           //if(goalcheck(ngoal)){Facts.add(ngoal);}
                        }
                        System.out.println("OR");
                       
                    }
                   
                 }
                else if(!prules.containsKey(ruleKey)){
                System.out.println("No fact found and No Rule Found:"+g);
                return true;    
                }
                
                
            }
        }
//        if(flag==0)
//        { 
//            System.out.println("FALSE");
//      
//        }
//        else
//            System.out.println("TRUE");
        return flag;
        }

        public boolean goalcheck(String ngoal,ArrayList<String> goals) {
            System.out.println("Entered goal check");
        return false;
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        
        //Scanner scn=new Scanner(System.in);    
        System.out.println("\n\n*********************************************************************");
        
        PROLOGEBG pebg=new PROLOGEBG("/home/vyasa/NetBeansProjects/PROLOG-EBG/src/prolog/ebg/input.txt");
               
        
        System.out.println("\n\n*********************************************************************\n\n");
        pebg.processRules();
        System.out.println("\n\n*********************************************************************");
        pebg.constGen();
        System.out.println("\n\n*********************************************************************");
        
        if(pebg.solveGoals(pebg.Goals)){
            System.out.println("TRUE");   
        }
        else
            System.out.println("FALSE");
//            String fun1 = Head.substring(0, Head.indexOf('('));//"Mortal";  
//            Functions.add(fun1);
//            System.out.println(fun1);
//            //Tail.clear();

//     
        
        
       
        
    }  

   


    
}

