/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folderClasses;

import java.sql.Types;
import java.util.ArrayList;
import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;

public class Connect1C{
    private static volatile Connect1C instance;
    private static DispatchPtr app;
    private static DispatchPtr ref;
    private static String ConnectionString;
            
    public static void close(DispatchPtr dispatch)throws COMException{
        if (dispatch != null)
            dispatch.close();
    }    
      
    public static Connect1C getInstance(String CS) {
        System.out.println(System.getProperty("java.class.path"));
        ConnectionString=CS;
        Connect1C localInstance = instance;
        if (localInstance == null) {
            synchronized (Connect1C.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Connect1C();
                }
            }
        }
        return localInstance;
    }
    
    private Connect1C() {}

    public boolean Open(){
        try{
            Ole32.CoInitialize();
            app = new DispatchPtr("V82.COMConnector");
            ref = (DispatchPtr) app.invoke("Connect", ConnectionString);
            return true;
        }catch(COMException e){
            return false;
        }
    }
    
    public boolean Close(){
        try{
            close(ref);
            close(app);
            Ole32.CoUninitialize();
            return true;
        }catch(COMException e){
            return false;
        }
    
    }
    //в общем передаём Запрос на встроенном языке запросов, миссив с названиями
    //полей и массив с типами этих полей, типы - константы из java.sql.Types
    public ArrayList<Object[]> GetFrom1C(String Query,String[] ColName,int[] ColType){
        if(ColName.length!=ColType.length){
            throw new IllegalArgumentException("ColName.length!=ColType.length");
        }
        ArrayList res=new ArrayList();
        try {
            DispatchPtr query=(DispatchPtr)ref.invoke("NewObject","Query");
            query.put("Text",Query);
            DispatchPtr chooser =(DispatchPtr)query.invoke("Выполнить");
            DispatchPtr choos =(DispatchPtr)chooser.invoke("Выбрать");
            while ((Boolean) choos.invoke("Следующий")) {
                Object[] RowData = new Object[ColName.length];
                for(int i=0;i<ColName.length;i++){
                    switch(ColType[i]){  //разбираю сейчас только integer и varchar
                        case Types.FLOAT:
                            Object o=choos.get(ColName[i]);
                            String sf=o.toString();
                            RowData[i] = Float.parseFloat(sf);
                            if(RowData[i]==null){
                                RowData[i]=0.0;
                            }
                            break;
                        case Types.INTEGER:
                            String si=(String)choos.get(ColName[i]);
                            RowData[i] = Integer.parseInt(si.trim());
                            if(RowData[i]==null){
                                RowData[i]=0;
                            }
                            break;
                        case Types.VARCHAR:
                            RowData[i] = ref.invoke("String", choos.get(ColName[i]));
                            if(RowData[i]==null){
                                RowData[i]="";
                            }
                            break;                                                
                    }
                }
                res.add(RowData);
            }
            close(choos);            
            close(chooser);
            close(query);
        } catch (COMException comex) { 
            System.out.println(comex); 
        }
        return res;
    }    
}
