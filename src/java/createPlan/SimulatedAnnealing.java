package createPlan;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import folderClasses.WorkDB;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SimulatedAnnealing {

    private double temperature = 100;
    int[] plan,test_plan;
    float e,e_test,be;
    private Calendar dateBeginPlan; 
    Line[] lines,test_lines;
    Operation[] operations; 
    Order[] orders;
    
    private DataModel primaryDataModel;
    private DataModel testDataModel;
    
    public void setDateBeginPlan(Calendar c){
        dateBeginPlan=c;
        
    }
    
    private int saveDatamodel(State state) { // сохранить модель
        WorkDB wdb=new WorkDB();
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2017-04-14 12:00:00
        String dateComleted = sdfNow.format(state.getPlanEndDate().getTime());
        String dateStart = sdfNow.format(state.getDateBeginPlan().getTime());
        wdb.QueryDB(
                "INSERT INTO datamodel(" +
                "Created," +
                "Datecompleted," +
                "id_shop," +
                "startTime)" +
                "VALUES(" +
                "now()," +
                "'"+dateComleted+"'," +
                "1," +
                "'"+dateStart+"');");
        ArrayList<Object[]> data= wdb.GetSelectFromDB("select max(id_datamodel) as id from datamodel");
        return (Integer)data.get(0)[0];
    }
    
    public int saveLine(Line line,int idDatamodel){ // сохранить линию
        WorkDB wdb=new WorkDB();
        wdb.QueryDB(
                "INSERT INTO line" +
                "(id_line," +
                "Name," +
                "id_wodkplace," +
                "id_datamodel)" +
                "VALUES" +
                "(null," +
                "''," +
                line.idWorkplace+"," +
                idDatamodel+");");
        ArrayList<Object[]> data= wdb.GetSelectFromDB("select max(id_line) as id from line");
        return (Integer)data.get(0)[0];
    }
    
    public void saveBlock(Block block,int idLine,String timeStart){ // сохранить блок
        String color;
        switch(block.getIdOperation()){
            case -1:
                color="0000FF";
                break;
            case -2:
                color="00FF00";
                break;
            case -3:
                color="FF0000";
                break;
            default:
                color="0C0C0C";                
        }
        new WorkDB().QueryDB(
                "INSERT INTO block" +
                "(id_Block," +
                "id_detail," +
                "id_order," +
                "timeStart," +
                "time," +
                "id_operation," +
                "color," +
                "numberDetails," +
                "id_Line)" +
                "VALUES" +
                "(null," +
                block.getIdDetail()+"," +
                block.getOrderId()+"," +
                "'"+timeStart+"'," +
                ((int)block.getWidth())+ "," +
                block.getIdOperation()+ "," +
                "'"+color+"'," +
                block.getNumberDetail()+"," +
                idLine+");");
    }
        
    public void savePlan(State state){ // сохранить план
        WorkDB wdb = new WorkDB();
        try {
            wdb.QueryDB("DELETE FROM datamodel");
            wdb.QueryDB("DELETE FROM line");
            wdb.QueryDB("DELETE FROM block");
            int idDatamodel = saveDatamodel(state);
            System.out.println("idDatamodel="+idDatamodel);
            boolean HaveBlocks;
            Calendar timeStart;
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2014-04-14 12:00:00     

            for(Line l:lines){
                HaveBlocks=false;
                for(int i=0;i<l.getListBlock().size();i++){
                    if(l.getBlock(i).getIdOperation()>=0){
                        HaveBlocks=true;
                        break;
                    }
                }
                ArrayList<Block> bl;
                ArrayList<String> dt;
                
                /*if(HaveBlocks){
                    timeStart=(Calendar)state.getDateBeginPlan().clone();
                    ArrayList<Block> blocks = new ArrayList<Block>(l.getListBlock());
                    for(Block b:blocks){
                        b.setDateBegin(timeStart);
                        Calendar timeStartCheck = wdb.checkWorkTime(timeStart, (int)b.getWidth());
                        if (timeStartCheck.get(Calendar.DATE) != b.getDateBegin().get(Calendar.DATE)) {
                            Calendar c = Calendar.getInstance();
                            
                            Block b1 = new Block(0, 0, 0, 0, 0, b.getStart(), 0);
                            Block b2 = new Block(0, 0, 0, 0, 0, 320, 0);
                            c.setTime(timeStartCheck.getTime());
                            c.set(Calendar.HOUR_OF_DAY, 9);
                            c.set(Calendar.MINUTE, 0);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                            b1.setParam(b.getDateBegin(), b.getIdDetail(), b.getIdOperation(), (int)((int)b.getWidth() - (timeStartCheck.getTimeInMillis()- c.getTimeInMillis()) / 1000 - 360), b.getNumberDetail(), b.getIdTask(), b.getColor(), b.getIdOrder(), b.getBlackTime());
                            c.add(Calendar.SECOND, 320);
                            b2.setParam(c, b.getIdDetail(), b.getIdOperation(), (int)(b.getWidth() - b1.getWidth()), b.getNumberDetail(), b.getIdTask(), b.getColor(), b.getIdOrder(), b.getBlackTime());
                                    
                            Block b_start = new Block(320, -1, -1, -1, -1, 0, 0);
                                                        
                            c.setTime(b.getDateBegin().getTime());
                            c.set(Calendar.HOUR_OF_DAY, 16);
                            c.set(Calendar.MINUTE, 54);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                            Block b_end = new Block(360, -1, -2, -1, -1, b1.getStart() + b1.getWidth(), b1.getStart() + b1.getWidth());
                            
                            l.setWorkTime(l.getWorkTime() - (int)b.getWidth() + (int)b1.getWidth());
                            b.setWidth((int)b1.getWidth());
                                    
                            timeStart = wdb.checkWorkTime(timeStart, (int)b1.getWidth());
                            
                            l.AddBlock(b_end);
                            //saveBlock(b_end,idLine,sdfNow.format(timeStart.getTime()));
                            timeStart = wdb.checkWorkTime(timeStart, (int)b_end.getWidth());
                            
                            l.AddBlock(b_start);
                            //saveBlock(b_start,idLine,sdfNow.format(timeStart.getTime()));
                            timeStart = wdb.checkWorkTime(timeStart, (int)b_start.getWidth());
                            
                            l.AddBlock(b2);
                            //saveBlock(b2,idLine,sdfNow.format(timeStart.getTime()));
                            timeStart = wdb.checkWorkTime(timeStart, (int)b2.getWidth());
                        }
                        else {
                            timeStart = wdb.checkWorkTime(timeStart, (int)b.getWidth());
                        }  
                    }
                }*/

                if(HaveBlocks){
                    int idLine=saveLine(l,idDatamodel);
                    timeStart=(Calendar)state.getDateBeginPlan().clone();
                    for(Block b:l.getListBlock()){
                        saveBlock(b,idLine,sdfNow.format(timeStart.getTime()));
                        timeStart = wdb.checkWorkTime(timeStart, (int)b.getWidth()); 
                    }
                }
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public int getMaxWidth(){
        int m=-1;
        for(int i=0;i<lines.length;i++){
            if(lines[i].getWorkTime()>m){
                m=(int)lines[i].getWorkTime();
            }
        }
        return m;
    }
    
    public int[] Init(Operation[] o,Line[] l,Order[] or){ // инициализация начального плана
        operations=o;
        lines=l;
        test_lines=l.clone();
        orders=or;
        int c=0;
        for(int i=0;i<orders.length;i++){
            c=c+orders[i].GetOrderOperations().length;
        }
        plan=new int[c];
        test_plan=new int[c];        
        c=0;
        for(int i=0;i<orders.length;i++){
            for(int j=0;j<orders[i].GetOrderOperations().length;j++){
                plan[c]=i;
                test_plan[c]=i;
                c++;
            }
        }
        //тестовый план построен
        //теперь нужно создать блоки...
        CreateBlocks(lines,plan);
        //и ещё рассчитать начальную энергию
        e=CalcE(lines);
        return plan;
    }
    
    public void startAnneling(State state) { // построение тестового плана
        this.setDateBeginPlan(state.getDateBeginPlan());
        ArrayList<Order> or=new ArrayList<Order>();   
        for(Order order:state.getOrders()){ // цикл по заказам
            int[] op=new int[order.getSizeOperationsList()];
            for(int i=0;i<order.getSizeOperationsList();i++){ // цикл по операциям
                op[i]=order.getIdOperation(i); // массив операций
            }
            or.add(new Order(order.getNumberDetail(),op,order.getIdOrder(),order.getIdDetail())); // список заказов
        }
        Init(state.getOperations(), state.getLines(),or.toArray(new Order[or.size()])); // инициализация начального плана
        //System.out.println("Начальный план:"+Arrays.toString(plan));
        //ShowPlan(lines);
    
    }
    /* Не эффективно - попытка изобразить множество случайных планов и просто
    выбрать оптимальный по энергии
    private DataModel getOptimalState(State state) {
        ArrayList<DataModel> list = new ArrayList<DataModel>();
        for (int i = 0; i < 100; i++) {
            list.add(state.getRandState());
        }
        DataModel res = list.get(0);
        for (int i = 1; i < 100; i++) {
            if (list.get(i).getEnergy() < res.getEnergy()) {
                res = list.get(i);
            }
        }
        return res;
    }
    */
    /* 
    public double getProbability(DataModel primaryState, DataModel testState) {
        return Math.exp((primaryState.getEnergy() - testState.getEnergy()) / temperature);
    }

    private void changeTemperature() {
        temperature = temperature * alpha;
    }
    */
    private void ShowPlan(Line[] lines){
        //System.out.println("t:"+temperature);
        //System.out.println("e:"+CalcE(lines));
        //for(int i=0;i<lines.length;i++){
            //System.out.println("Line"+i+":\t"+lines[i].toString());
        //}
    }
    
    private void CreateBlocks(Line[] lines,int[] plan){ // построение блоков
        
        //будем считать эпохи пока все задачи не закончатся, это самый простой способ
        //пока для упрощения создадим для всех линий блоки инициализации - потом можно будет
        //перетасовать, в общем погоды не делает
        for(int i=0;i<lines.length;i++){
            lines[i].DelBlocks();            //очищаю линию от блоков
            lines[i].AddBlock(new Block(lines[i].begin,-1,-1,-1,-1,0,0));
        }
        float ep=0;   //эпоха, счётчик..
        int n=0;      // переменная n, пригодилась
        float width;
        int order;
        int operation;            
        //ещё пригодится массив с количеством пройденных операций каждого заказа
        //чтобы повторно по плану не бегать
        
        int[] complate_operations=new int[orders.length];   //нулями забит по умолчанию
        float[] fin=new float[orders.length];   //время завершения текущей операции заказа
        Calendar end_time = (Calendar)dateBeginPlan.clone();
        end_time.add(Calendar.SECOND, 320);
        
        while(n<plan.length){
            order=plan[n];  //номер заказа из плана
            operation=orders[order].GetOrderOperations()[complate_operations[order]];
            //ищу продолжительность операции по её коду
            int operationIndex=-1;
            for(int i=0;i<operations.length;i++){
                if(operation==operations[i].getIdOPeration()){
                    operationIndex=i;
                    break;
                }
            }        
            width=orders[order].getNumberDetail()*operations[operationIndex].width; //выполнение операции над всеми детальками            
            //пока не будем создавать блок
            //определим минимальное время через которое освободится хотя бы одна линия
            int index=0;
            for(int i=1;i<lines.length;i++){
                if(lines[i].getWorkTime()<lines[index].getWorkTime()){
                    index=i;
                }
            }
            if(lines[index].getWorkTime()-ep>0){    //если все линии заняты нет смысла 
                ep+=lines[index].getWorkTime()-ep;  //идти далее нужно подождать пока хотябы одна линия освободится
                continue;               //ждём до освобождения хотябы одной линии
            }
            //теперь проверю все заказы, должен быть хотябы один
            index=0;
            for(int i=0;i<orders.length;i++){
                if(fin[i]<fin[index]){
                    index=i;
                }
            }
            if(fin[index]-ep>0){
                ep+=fin[index]-ep;  //ждём до освобождения хотябы одного процесса
                continue;
            }
            //Если оказались здесь ,значит есть свободная линия
            //и есть свободный процесс, не факт что они подходят но пока это не важно!!!
            //Уточним - очередной блок должен принадлежать свободному процессу
            if(fin[order]>ep){      
                ep=fin[order];      
                continue;
            }
            //теперь нужно удостовериться что есть свободная линия для выполнения 
            //очередного блока, cоздадим массив с номерами линий которые 
            //признают эту операцию...
            ArrayList<Integer> right_lines=new ArrayList<Integer>();
            for(int j=0;j<lines.length;j++){
                for(int l=0;l<lines[j].operations.length;l++){
                    if(operation==lines[j].operations[l]){
                        if(lines[j].getWorkTime()-ep>0){   //если линия ещё не доступна, её добавлять не стоит
                            continue;
                        }
                        right_lines.add(j);
                        break;
                    }
                }
            }
            if(right_lines.size()==0){  //если нет свободных линий признающих очередную операцию
                //ищем минимальное время через которое освободится линия признающая операцию
                
                index=-1;
                for(int j=0;j<lines.length;j++){    //пробегаю все линии
                    for(int l=0;l<lines[j].operations.length;l++){  //операции линии
                        if(operation==lines[j].operations[l]){  //если линия признаёт операцию
                            if((index==-1)||(lines[j].getWorkTime()<lines[index].getWorkTime())){   //это первая линия или линия освободится раньше предыдущей
                                index=j;    //запомнил номер
                            }
                            break;  //к следующей линии
                        }
                    }
                }
                ep+=lines[index].getWorkTime()-ep;  //ждём пока освободится нужная линия
                continue;
            }
            // если я здесь значит есть линия признающая очередную операцию
            //из доступных линий выбиру ту которая меньше всех работала
            index=0;
            for(int j=1;j<right_lines.size();j++){
                if(lines[right_lines.get(j)].getWorkTime()<lines[right_lines.get(index)].getWorkTime()){
                    index=j;
                }
            }
            index=right_lines.get(index);
            
            Block block=new Block(width,orders[order].getIdOrder(),operation,orders[order].getNumberDetail(),orders[order].getIdDetail(),ep,fin[order]);
            //блок создали теперь до победного будет его пробовать пристроить на линии
            //и ещё блок мы создали, это блок выполняющий операцию, нам ещё может 
            //понадобится блок простоя, но его создадим позже если понадобится
            if(lines[index].getWorkTime()<ep){  //есть простой
                lines[index].AddBlock(new Block(ep-lines[index].getWorkTime(),-1,-3,-1,-1,ep,ep));  //добавим блок простоя
                end_time.add(Calendar.SECOND, (int)(ep-lines[index].getWorkTime())); // пересчитали время окончания
            }
            
            /*Calendar curr_time = (Calendar)end_time.clone();
            end_time.add(Calendar.SECOND, (int)(width));
            if (end_time.get(Calendar.HOUR_OF_DAY) >= 17 || end_time.get(Calendar.DATE) != curr_time.get(Calendar.DATE)) {
                            Calendar c = Calendar.getInstance();
                            
                            c.setTime(curr_time.getTime());
                            c.set(Calendar.HOUR_OF_DAY, 17);
                            c.set(Calendar.MINUTE, 0);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                            
                            long over_second = (end_time.getTimeInMillis() - c.getTimeInMillis()) / 1000;
                            Block b1 = new Block(width - over_second - 360, orders[order].getIdOrder(),operation,orders[order].getNumberDetail(),orders[order].getIdDetail(), ep, fin[order]);
                            
                            lines[index].setWorkTime(lines[index].getWorkTime() - (int)width + (int)b1.getWidth());
                            lines[index].AddBlock(b1);
                            //fin[order]=ep+b1.getWidth();
                            //ep += b1.getWidth();
                                    
                            Block b_end = new Block(360, -1, -2, -1, -1, ep+b1.getWidth(), ep+b1.getWidth());
                            lines[index].AddBlock(b_end);
                            
                            //ep += b_end.getWidth();
                            //fin[order]=ep+b_end.getWidth();
                            
                            Block b_start = new Block(320, -1, -1, -1, -1, ep+b1.getWidth()+b_end.getWidth(), ep+b1.getWidth()+b_end.getWidth());
                            lines[index].AddBlock(b_start);
                            
                            //ep += b_start.getWidth();
                            //fin[order]=ep+b_start.getWidth();
                            
                            Block b2 = new Block(width - b1.getWidth(), orders[order].getIdOrder(),operation,orders[order].getNumberDetail(),orders[order].getIdDetail(), ep+b1.getWidth()+b_end.getWidth()+b_start.getWidth(), ep+b1.getWidth()+b_end.getWidth()+b_start.getWidth());
                            lines[index].AddBlock(b2);
                            
                            //ep += b2.getWidth();
                            //fin[order]=ep+b2.getWidth();

                //ShowPlan(lines,t,e);
                //continue;   //время наращивать не нужно, возможно несколько блоков смогут начаться в одно время
            } else {*/
                lines[index].AddBlock(block);
                //ещё после того как я разместил блок нужно запомнить что 
                //заказ будет некоторое время занят
                fin[order]=ep+width;    
            //}
            
        n++;    //перешли к следующему блоку
        complate_operations[order]++;
        }
            
        for(int i=0;i<lines.length;i++){
            lines[i].AddBlock(new Block(lines[i].end,-1,-2,-1,-1,lines[i].getWorkTime(),lines[i].getWorkTime())); 
        }               //добавим блоки завершения
                       
        //и ещё одна проверка - если после блока инициализации есть
        //блок простоя - меняю их местами
        for(int i=0;i<lines.length;i++){
            if(lines[i].getBlock(1).idOrder==-1){
                lines[i].SwapBlocks(0,1);//поменял местами блоки
                
            }
        }         
        
    }     
    //математически всё верно,но можно изменить параметры функции рассчитывающую энергию:
    private float CalcE(Line[] lines){ // подаем на вход функции массив линий
        float res=0; // предварительно обнулили результат работы функции
        boolean EmptySpace; // признак того что перед блоком был блок простоя
        for(Line l:lines){ // запускаем цикл по всем линиям
            EmptySpace=false; // установим признак простоя false
            for(Block b:l.getListBlock()){ // запускаем цикл по всем блокам на текущей линии
                if(b.getIdOperation()>=0){  //если это выполнение какой то полезной операции, то есть не подготовка или очистка оборудования
                    if(b.HavePause() && EmptySpace){    //если операция началась мозже чем закончилась предыдущая операция заказа и есть пустой блок перед этим блоком
                        res+=10;  //повышаем энергию
                    }
                    EmptySpace=false;   //запомнили что это не блок простоя
                }
                if((b.getOrderId()==-1)&&(b.getIdOperation()==-3)){ // если id  заказа равен -1, то есть блок не связан с каким либо заказом и id операции равен -3, то есть операция простоя
                    res+=b.getWidth(); // результату присваиваем ширину блока простоя
                    EmptySpace=true;    //запомнили блок простоя
                }
            }
        }
        return res;
    }    
 
    //Имитация отжига
    public void Alg(int begin_t,float koef){
        temperature=begin_t; // задаем начальную температуру
        int[] bp=plan; // массив из индексов заказов, размерностью общим количеством операций по всем заказам
        int i1,i2,tmp; // индексы
        float me=e; // задаем минмальную энергию значением начальной энергии
        float s=0; // продолжительность всех задач
        for(Line l:lines){ // запускаем цикл по всем линиям
            for(Block b:l.getListBlock()){ // запускаем цикл по всем блокам на текущей линии
                if(b.getOrderId()>-1){ // если текущий блок не блок простоя, либо не подготовка, очистка оборудования
                    s+=b.getWidth(); // вычисляем общую продолжительность задач, суммируя продолжительность блоков полезных операций
                }
            }
        }
        //System.out.println("Общая продолжительность выполнения всех задач:"+s);
        //plan=new int[]{5, 3, 1, 3, 1, 5, 0, 3, 7, 1, 6, 7, 5, 4, 7, 0, 2, 5, 7, 4, 5, 0, 2, 6, 4, 0, 6, 4};
        while (temperature > 1) { // запускаем цикл с условием, пока температура больше 1
            test_plan=plan.clone(); //скопировали текущий план во временную переменную для дальнейшей обработки
            //int temp=-1;
            //for(int r=0;r<2;r++){            
                i1=(int)Math.round(Math.random()*(test_plan.length-1)); // случайным образом выбираем 2 индекса операций из временно скопированного плана 
                i2=(int)Math.round(Math.random()*(test_plan.length-1));           
                while(test_plan[i1]==test_plan[i2]){ // если выбранные индексы операций, указывают на операции из одного заказа, то запускаем цикл и выбираем второй индекс до тех пор, пока заказы не будут разные 
                    i2=(int)Math.round(Math.random()*(test_plan.length-1));        
                }
                //выбрали 2 разные операции принадлежащие разным заказам
                //меняем их местами в плане через временную переменную tmp
                tmp=test_plan[i1];
                test_plan[i1]=test_plan[i2];
                test_plan[i2]=tmp;   
            //}
            //System.out.println("Текущий план\t\t:"+Arrays.toString(plan));
            //System.out.println("Тестируемый план\t:"+Arrays.toString(test_plan));            
            CreateBlocks(test_lines,test_plan); //теперь построим блоки для нового тестируемого плана
            //ShowPlan(test_lines,t,e);
            e_test=CalcE(test_lines); // вычислим энергию на текущий момент
            if(e_test<=e){  //если энергия уменьшилась
                if(me>e_test){ // проверяем если минимальная энергия больше текущей
                    me=e_test; // то переопределяем минимальную энергию
                    //System.out.println("t:"+temperature);
                    //ShowPlan(lines);
                    //System.out.println("Текущий план\t\t:"+Arrays.toString(plan));
                    //System.out.println("Тестируемый план\t:"+Arrays.toString(test_plan));            
                    bp=test_plan.clone(); // копируем тестируемый план с минимальной энергией на данный момент в переменную bp
                }
                lines=test_lines; // переопределяем линии теструемыми линиями
                plan=test_plan; // переопределяем план тестируемым планом
                e=e_test; // переопределяем энергию тестируемой энергией
            }else{ // иначе энергия увеличилась
                double r=Math.random(); // задаем случайным образом некоторое вещественное число r
                double p=Math.exp(-(e_test-e)/temperature); // рассчитываем число p как единица, деленная на экспоненту в степени разница тестируемой и текущей энергий деленная на текущую температуру
                if(p>r){ // если полученное число больше случайного r , то всё же выполнили переход в новое состояние
                    if(me>e_test){ // опять проверяем если минимальная энергия больше текущей
                        me=e_test; // то переопределяем минимальную энергию
                        //System.out.println("t:"+temperature);
                        //ShowPlan(lines);
                        //System.out.println("Текущий план\t\t:"+Arrays.toString(plan));
                        //System.out.println("Тестируемый план\t:"+Arrays.toString(test_plan));
                        bp=test_plan.clone(); // копируем тестируемый план с минимальной энергией на данный момент в переменную bp
                    }
                    lines=test_lines; // переопределяем линии теструемыми линиями
                    plan=test_plan; // переопределяем план тестируемым планом
                    e=e_test; // переопределяем энергию тестируемой энергией
                }
            }
            temperature*=(1-koef);    //уменьшаем температуру умножением на 1-koef
        } // вышли из цикла, значит температура меньше равна 1, то есть мы получили оптимальный план
        plan=bp; // присваиваем переменной plan построенный оптимальный план
        CreateBlocks(lines,plan); // строим блоки оптимального плана
        //ShowPlan(lines);
        //System.out.println("Финальный план\t\t:"+Arrays.toString(plan));
        //System.out.println("THE END.");
        //System.out.println("P.S. Общая продолжительность выполнения всех задач:"+s);
    }
    /*
    public void startAneling(State state) {
        primaryDataModel = state.getRandState();
        while (temperature > 95) {

            testDataModel = getOptimalState(state);

            if (testDataModel.getEnergy() < primaryDataModel.getEnergy()) {
                primaryDataModel = testDataModel;
            } else {
                if (getProbability(primaryDataModel, testDataModel) < 0.7) {
                    primaryDataModel = testDataModel;
                }
            }
            changeTemperature();
        }
        state.setDataModel(primaryDataModel);
    }
    */
}
