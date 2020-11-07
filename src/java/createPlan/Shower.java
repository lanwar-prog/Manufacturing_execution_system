/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createPlan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Shower {   
    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    public static String blockToStr(String c,int width){
        String s="";
        if(width%5==0){         //уменьшил ширину в 5 раз
            width=width/5;
        }
        for(int i=0;i<width;i++){
            s=s+c;
        }
        return s;
    }
    
    public static void ShowState(DataModel datamodel){
        /*
        System.out.println("State.StartTimeDataModel:"+datamodel.StartTimeDataModel);
        for(int i=0;i<datamodel.lines.size();i++){
            Line l=datamodel.lines.get(i);
            System.out.println("State.lines["+i+"]:");
            System.out.println("    name:"+l.getName());
            ArrayList<Block> lb=l.getListBlock();
            for(int j=0;j<lb.size();j++){
                Block b=lb.get(j);
                System.out.println("    block["+j+"]:");
                System.out.println("        flag:"+b.flag);
                System.out.println("        DateBegin:"+b.getDateBegin());
                System.out.println("        DateEnd:"+b.getDateEnd());
                System.out.println("        IdDetail:"+b.getIdDetail());
                System.out.println("        IdOperation:"+b.getIdOperation());
                System.out.println("        NumberDetail:"+b.getNumberDetail());                
            }
            System.out.println("    task:");
            Task  t=l.task;
            System.out.println("        name:"+t.getNameTask());
            System.out.println("        id:"+t.idTask);            
        }
    */
    }
}
