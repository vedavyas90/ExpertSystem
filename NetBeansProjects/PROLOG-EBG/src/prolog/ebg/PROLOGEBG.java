package prolog.ebg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author vyasa
 */

public class PROLOGEBG {
        
        
    
   // public boolean goalcheck(String ngoal) {
   //     System.out.println("Entered goal check");
   // }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        
        System.out.println("Welcome to EBG");
        Scanner scn=new Scanner(System.in);
        ArrayList<String> Rules =new ArrayList<String>();
        ArrayList<String> Facts =new ArrayList<String>();
        ArrayList<String> Goals =new ArrayList<String>();
        Map<String,ArrayList<ArrayList<String>>> prules=new HashMap<String,ArrayList<ArrayList<String>>>();
        ArrayList<String> Functions=new ArrayList<String>();
        
        
        System.out.println("Stage one on: Loading the Knowledge Base .....");
        
        try {
			File file = new File("/home/vyasa/NetBeansProjects/PROLOG-EBG/src/prolog/ebg/input.txt");
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
        String[] temp;
        String temp2;
        String Head;
        ArrayList<ArrayList<String>> Tails=new ArrayList<ArrayList<String>>();
        ArrayList<String> Tail=new ArrayList<String>();
 
        System.out.println("Stage 2: processing rules....");
        for(String R : Rules){
            
            String delimiter = ":-";
            temp = R.split(delimiter);
            int k=0;
            Head=temp[0];
            temp2=temp[1];
            
           
            System.out.println("Conlusion part of ("+R+") is :"+Head);
            if(prules.containsKey(Head)){
                k=prules.get(Head).size();
            }
            else
                prules.put(R,Tails);
            delimiter = ",";
            if(temp2.contains(",")){
                temp=temp2.split(delimiter);
                for (int i=0;i<temp.length;i++){
                    System.out.println("premises part of ("+R+") has :"+temp[i]);
                    System.out.println(k);
                   // System.out.println(prules.get(Head)[k].size()); 
                   //Tails[k].add(temp[i]);
                    Tail.add(temp[i]);
                }
            }
            else{
                Tail.add(temp2);    
                System.out.println(" premises part of ("+R+") is :"+temp2);
            }
            Tails.add(Tail);
            prules.put(R,Tails);
        }
        /*
        String fun1 = Head.substring(0, Head.indexOf('('));//"Mortal";  
            Functions.add(fun1);
            System.out.println(fun1);
            Tail.clear();
        */
        System.out.println("Stage 3: determining constants and variables....");
        ArrayList<String> Constants =new ArrayList<String>();
        for(String s:Facts){
        String con=s.substring(s.indexOf('(')+1, s.indexOf(')'));//"Ram","Sita";
        Constants.add(con);
        }
         System.out.println("Contants found :");
        for(String c:Constants){
            System.out.println(c);
        }
        
        ArrayList<String> Variables =new ArrayList<String>();
        String Var1="X";

        Variables.add(Var1); 
        
        System.out.println("Stage 4: Goal solving");
        int flag=1;
        System.out.println("Goals stored are:"); 
        for(String g: Goals){   
            System.out.println(g);
        }
        System.out.println("Facts stored are:"); 
        for(String f: Facts){   
            System.out.println(f);
        }
        System.out.println("Goal Tree");
        System.out.println("L0:Root");
        System.out.println("L1:");
        for(String g: Goals){   
            System.out.println(g);
        }
        for(String g: Goals){  
          if(!Facts.contains(g)){
                //System.out.println("you cant obtain this goal directly from given facts");    
                String funct=g.substring(0,g.indexOf("("));
                String cons=g.substring(g.indexOf("(")+1,g.indexOf(")"));
                
                //System.out.println("goal has fuction "+funct +" and with constanct "+cons);    
                String ruleKey=null;
                Iterator itr=prules.entrySet().iterator();
                while(itr.hasNext()){
                  Map.Entry<String,ArrayList<String>> mapEntry=(Map.Entry<String,ArrayList<String>>)itr.next();
                  String str=mapEntry.getKey();
                  if(str.contains(funct))
                  {
                      ruleKey=str;
                  }
                }
                
                if(prules.containsKey(ruleKey));
                {
                    
                   // System.out.println("Rule found");
                   // System.out.println("New goals ");
                   System.out.println("L2:");
                    for(int j=0;j<prules.get(ruleKey).size();j++){
                        ArrayList<ArrayList<String>> premises=prules.get(ruleKey);
                        for(int n=0;n<premises.get(j).size();n++){
                            String var=premises.get(j).get(n).substring(premises.get(j).get(n).indexOf("(")+1, premises.get(j).get(n).indexOf(")"));
                            String ngoal=premises.get(j).get(n);
                            System.out.println(ngoal.replaceAll(var,cons));
                           // if(goalcheck(ngoal)){Facts.add(ngoal);}
                    } }
                 }
                flag=0;
            }
        }
        if(flag==0)
        { 
            System.out.println("FALSE");
      
        }
        else
            System.out.println("TRUE");
        
        
        
    }  
    
}

