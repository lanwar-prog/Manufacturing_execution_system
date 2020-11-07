package createPlan;

import folderClasses.WorkDB;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Line {

    String name = "";
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private Calendar dateStartPlan = Calendar.getInstance();
    private float  WorkTime;    //сумма продолжительностей всех блоков 
    public int idWorkplace;
    public Task task;
    int[] operations;
    float begin,end;
    
    public float getWorkTime(){
        return WorkTime;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void DelBlocks(){
        blocks.clear();
        WorkTime=0;
    }
    
    public void AddBlock(Block block){  //метод добавляет блок и наращивает время
        WorkTime+=block.getWidth();     //создаёт блоки, опеределяет лучшую линию
        blocks.add(block);              //и всё прочее другой класс... !!!
    }
    
    @Override
    public String toString(){
        String s="";
        for(int i=0;i<blocks.size();i++){
            char ch;
            switch(blocks.get(i).getIdOperation()){
                case -1:
                    ch='b'; //подготовка
                    break;
                case -2:
                    ch='f'; //завершение
                    break;
                case -3:
                    ch='п'; //простой
                    break;
                default:
                    ch=(char)('0'+blocks.get(i).getOrderId()); //номер заказа - 1 символ... неважно 
            }
            
            for(int j=0;j<(int)blocks.get(i).getWidth()/5;j++){
                s+=ch;
            }
        }
        
        return s;
    }
    
    public Line(int[] o,float b,float e){
        
        blocks=new ArrayList<Block>();
        WorkTime=0;
        begin=b;
        end=e;
        operations=o;
    }

    public Line(Integer[] o,float b,float e,int id_wp){
        int[] oi=new int[o.length];
        for(int i=0;i<o.length;i++){
            oi[i]=o[i];
        }
        blocks=new ArrayList<Block>();
        WorkTime=0;
        begin=b;
        end=e;
        operations=oi;
        idWorkplace=id_wp;
    }

    
    public void setDateStartPlan(Calendar t) {
        this.dateStartPlan = (Calendar) t.clone();
    }

    public Calendar getEndDate() {
        if (!blocks.isEmpty()) {
            return blocks.get(blocks.size() - 1).getDateEnd();
        } else {
            return (Calendar) dateStartPlan.clone();
        }
    }

    public int getIdWorkplace() {
        return this.idWorkplace;
    }

    public String getName() {
        return this.name;
    }

    public int getSizeBlocks() {
        return this.blocks.size();
    }

    public void SwapBlocks(int i1,int i2){
        Block bi1=blocks.get(i1);
        blocks.set(i1,blocks.get(i2));
        blocks.set(i2,bi1);        
    }
    
    public Block getBlock(int i) {
        return this.blocks.get(i);
    }
    /*
    public int getSkip(int i) {
        //return 0;
        int blok=i;
        long res = 0;
        if (blok == 0) {
            long le = this.blocks.get(blok).getDateBegin().getTimeInMillis();
            long l2 = this.dateStartPlan.getTimeInMillis();
            res = (le - l2) / 1000;
            return (int) res;
        } else { // (blok > 0) 
            res = (this.blocks.get(blok).getDateBegin().getTimeInMillis() - this.blocks.get(blok - 1).getDateBegin().getTimeInMillis()) / 1000;
            //res = res - (long)(this.blocks.get(blok - 1).get getWidth() * 1000);
            return ((int) res) - this.blocks.get(blok - 1).getWidth();
        }
        
    }
    */
    public ArrayList<Block> getListBlock() {
        return this.blocks;
    }

    private int getScip(int i, Calendar blackT) {
        int w;
        if (i == 0) {
            //w = (int) (blocks.get(i).getDateBegin().getTimeInMillis() - this.dateStartPlan.getTimeInMillis()) / 1000;
            if (blackT == null) {
                w = (int) (blocks.get(i).getDateBegin().getTimeInMillis() - this.dateStartPlan.getTimeInMillis()) / 1000;
            } else {
                w = (int) (blocks.get(i).getDateBegin().getTimeInMillis() - blackT.getTimeInMillis()) / 1000;
            }
        } else {
            if (blackT == null) {
                w = (int) (blocks.get(i).getDateBegin().getTimeInMillis() - blocks.get(i - 1).getDateEnd().getTimeInMillis()) / 1000;
            } else {
                w = (int) (blocks.get(i).getDateBegin().getTimeInMillis() - blackT.getTimeInMillis()) / 1000;
            }
        }
        return w;
    }
    /*
    public boolean checkInsert(Block b) {
        System.out.println("Baggen1");
        WorkDB database = new WorkDB();

        int start = database.getWidthOperationStart(b.getIdOperation());
        int end = database.getWidthOperationEnd(b.getIdOperation());
        int timeBlock = b.getWidth();

        boolean res = false;

        for (int i = 0; i < this.blocks.size(); i++) {
            System.out.println("Baggen2");
            System.out.println("CheckInsert:DateBegin:" +sdf.format(b.getDateBegin().getTime()));
            if(b.blackTime==null){
                //b.blackTime=(Calendar)b.getDateBegin().clone();
                System.out.println("BlackTime == null");
            }else{
                System.out.println("CheckInsert:BlackTime:" +sdf.format(b.blackTime.getTime()));
            }
            int scip = this.getScip(i, b.blackTime);
            System.out.println("Baggen3");

            //if (b.getIdOperation() == blocks.get(i).getIdOperation()) {
            if (scip > timeBlock) {
                System.out.println("Baggen4");
                if (i > 0) {
                    System.out.println("Baggen5");
                    if (b.blackTime != null) {
                        System.out.println("Baggen6");
                        String s = sdf.format(b.blackTime.getTime());
                        if (b.blackTime.after(blocks.get(i - 1).getDateEnd())) {
                            System.out.println("Baggen7");
                            b.setDateBegin((Calendar) b.blackTime.clone());
                            blocks.add(i, b);
                            
                        } else {
                            System.out.println("Baggen8");
                            b.setDateBegin((Calendar) blocks.get(i - 1).getDateEnd().clone());
                            //LIB изменяю остальным блокам которые позже вставляемого
                            //время начала обработки
                            for(Block bl:this.getListBlock()){
                                if(b.getDateBegin().compareTo(bl.getDateBegin())<=0){
                                    bl.setTime(timeBlock);
                                }
                            }
                            //LIB...
                            blocks.add(i, b);
                        }
                    } else {
                        System.out.println("Baggen9");
                        b.setDateBegin((Calendar) blocks.get(i - 1).getDateEnd().clone());
                        blocks.add(i, b);

                    }

                } else {
                    System.out.println("Baggen10");
                    //LIB b.setDateBegin((Calendar) this.dateStartPlan.clone());
                    //если это вставка блока в общем возможно несколько вариантов
                    //хотя здесь нет.
                    
                    //Block startBlock = new Block();
                    //Calendar temp = (Calendar) b.getDateBegin().clone();
                    //int width = database.getWidthOperationStart(b.getIdOperation());
                    //startBlock.setParam(temp, 0, 0, width, 0, null, "00FFFF", b.idOrder, null);
                    //this.blocks.add(0,startBlock);         
                    
                    //System.out.println(startBlock.toString());
                    
                    int d=0;
                    for(Block bl:this.getListBlock()){
                        if(bl.getDateBegin().compareTo(b.getDateBegin())>0){
                            d++;
                        }
                    }
                    if(d==0){
                        b.setTime(-start);
                    }
                    Block oldInitBlock=blocks.get(0);
                    oldInitBlock.setParam((Calendar)b.getDateBegin().clone(), 
                            0, 0, database.getWidthOperationStart(b.getIdOperation()), 
                            0, null, "00FFFF", b.idOrder, null);
                    oldInitBlock.setDateBegin((Calendar)b.getDateBegin().clone());
                    
                    blocks.add(1, b);
                    for(int j=2;j<blocks.size();j++){
                        //blocks.get(j).setTime(-database.getWidthOperationStart(b.getIdOperation()));
                    }
                }

                String s = sdf.format(b.getDateBegin().getTime());
                //b.setTime(timeBlock);
                String sasd = sdf.format(b.getDateEnd().getTime());
                res = true;
                i = this.blocks.size();
                b.blackTime = (Calendar) b.getDateEnd().clone();
            }
            //}
            //if (scip > timeBlock && b.getIdOperation() != blocks.get(i).getIdOperation()) {
            // res = true;
            // int width = b.getWidth() + start + end;
            // if (width < scip) {
            // return res;
            // }
            // }
            System.out.println("Baggen11");
        }
        return res;
        
    }
    */
    /*
    public Calendar addBlock(Block b) {
        WorkDB database = new WorkDB();
        Calendar temp;
        if (this.blocks.isEmpty()) {
            int width = database.getWidthOperationStart(b.getIdOperation());
            if(b.getDateBegin().compareTo(dateStartPlan)==0){   //если время старта задачи 
                Block startBlock = new Block(); //совпадает со временем старта проекта
                temp = (Calendar) b.getDateBegin().clone();         
                startBlock.setParam(temp, 0, 0, width, 0, null, "00FFFF", b.idOrder, null);
                this.blocks.add(startBlock);
                b.setTime(width);
                this.blocks.add(b);
            }else{  //иначе наверное можно подготовить линию заранее
                Block startBlock = new Block(); //совпадает со временем старта проекта
                b.setTime(-width);
                temp = (Calendar) b.getDateBegin().clone();         
                startBlock.setParam(temp, 0, 0, width, 0, null, "00FFFF", b.idOrder, null);
                this.blocks.add(startBlock);
                //b.setTime(width);
                this.blocks.add(b);
            }
            Block endBlock = new Block();
            temp = (Calendar) b.getDateEnd().clone();
            width = database.getWidthOperationEnd(b.getIdOperation());
            endBlock.setParam(temp, 0, 0, width, 0, null, "00CC66", b.idOrder, null);
            this.blocks.add(endBlock);

            return (Calendar) b.getDateEnd().clone();
        } else {

            boolean insert = checkInsert(b);///////////////////////////////////////////////////////

            if (insert == true) {       
                
                String sasd = sdf.format(b.getDateEnd().getTime());
                return (Calendar) b.getDateEnd().clone();
            }
            if (b.getIdOperation() == this.blocks.get(this.blocks.size() - 2).getIdOperation()) {
                System.out.println("NewBaggen1");
                //одинаковые операции            
                this.blocks.remove(this.blocks.size() - 1);
                int id=this.blocks.get(this.blocks.size() - 1).getOrderId();
                //LIB         
                //b.setTime(-database.getWidthOperationStart(b.getIdOperation()));
                //LIB...
                int width = database.getWidthOperationEnd(b.getIdOperation());

                String timess = sdf.format(b.getDateBegin().getTime());

                Calendar newCalendar = Calendar.getInstance();
                newCalendar = (Calendar) b.getDateEnd().clone();
                newCalendar.add(Calendar.SECOND, -width + 1);
                if (newCalendar.after(b.getDateEnd())) {
                    System.out.println("NewBaggen2");
                    //b.setTime(-width);
                }
                this.blocks.add(b);
                
                Block endBlock = new Block();
                temp = (Calendar) b.getDateEnd().clone();
                width = database.getWidthOperationEnd(b.getIdOperation());
                //LIB
                //endBlock.setParam(temp, 0, 0, width, 0, null, "00CC66", b.idOrder, null);
                endBlock.setParam(temp, 0, 0, width, 0, null, "00CC66", id, null);

                this.blocks.add(endBlock);
                System.out.println("NewBaggen3");                
                return (Calendar) b.getDateEnd().clone();
            } else {
                //Различные операции
                //1.Add start
                System.out.println("NewBaggen4");                
                Block startBlcok = new Block();
                temp = (Calendar) this.blocks.get(this.blocks.size() - 1).getDateEnd().clone();                
                int width = database.getWidthOperationEnd(b.getIdOperation());
                startBlcok.setParam(temp, 0, 0, width, 0, null, "00FFFF", b.idOrder, null);
                
                //2.Add blcok
                b.setTime(width);
                this.blocks.add(b);
                
                //3.Add complite

                Block endBlock = new Block();
                temp = (Calendar) b.getDateEnd().clone();
                width = database.getWidthOperationEnd(b.getIdOperation());
                endBlock.setParam(temp, 0, 0, width, 0, null, "00CC66", b.idOrder, null);
                this.blocks.add(endBlock);            
                return (Calendar) b.getDateEnd().clone();
            }

        }
    }*/

    public void setWorkTime(float WorkTime) {
        this.WorkTime = WorkTime;
    }
    
    
}
