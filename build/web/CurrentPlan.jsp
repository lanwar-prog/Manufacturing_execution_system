<%-- 
    Document   : CurrentPlanN
    Created on : 05.05.2017, 11:42:22
    Author     : Ковалев Алексей
--%>

<%@page import="ShowPlan.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <% 
            int role = 0;
            Model model = new Model();
            String pul = "";
            String pul2 = "";
            String pul_view = "";
            String pul2_view = "";

            pul = model.getDateBegin();
            pul2 = model.getEndDate();
            pul_view = model.getDateBeginView();
            pul2_view = model.getEndDateView();
            //model = new Model();
        %>
        
                <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }

            if (session.getAttribute("login") != null) {
                WorkDB database = new WorkDB();
                String log = (String) session.getAttribute("login");
                role = database.getRole(log);
            }

        %>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <%! String pgname = "Просмотр текущего плана";%>
        <title><%= pgname%></title>
        <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
        <link rel="stylesheet" type="text/css" href="styles/table.css">
        <script type="text/javascript">

            function showModalWin2(popup) {
 
                var darkLayer = document.createElement('div'); // слой затемнения
                darkLayer.id = 'shadow'; // id чтобы подхватить стиль
                document.body.appendChild(darkLayer); // включаем затемнение
 
                var modalWin = document.getElementById(popup); // находим наше "окно"
                modalWin.style.display = 'block'; // "включаем" его
 
                darkLayer.onclick = function () {  // при клике на слой затемнения все исчезнет
                    darkLayer.parentNode.removeChild(darkLayer); // удаляем затемнение
                    modalWin.style.display = 'none'; // делаем окно невидимым
                    return false;
                };
            }
            /*function showModalWin(id_line) {
                if(id_line=="popupWin"){
                    showModalWinOrders("popupWin");
                    return;
                }
                var darkLayer = document.createElement('div'); // слой затемнения
                darkLayer.id = 'shadow'; // id чтобы подхватить стиль
                document.body.appendChild(darkLayer); // включаем затемнение
                CreateLinePlan(id_line);
                var modalWin = document.getElementById("popupWin" + id_line); // находим наше "окно"
                modalWin.style.display = 'block'; // "включаем" его
 
                darkLayer.onclick = function () {  // при клике на слой затемнения все исчезнет
                    darkLayer.parentNode.removeChild(darkLayer); // удаляем затемнение
                    modalWin.style.display = 'none'; // делаем окно невидимым
                    return false;
                };
            }*/    
            //например буду считать что время в БД и в плане выражено в минутах
            var tm=600;  
            var lines=[];
            var dateExecution=[];
            //будет lines=и строка json
            //и dateExecution=и строка json
            
            function ReInitData(){
                var l=[];
                var d=[];
                <%out.print(model.getJSONData(-1));%>
                lines=l;
                dateExecution=d;
                //переведу даты окончания заказов и строк в даты
                for(order_id in dateExecution){
                    dateExecution[order_id]=new Date(Date.parse(dateExecution[order_id]));
                }
            }
                       
            //функция проверяющая остались ли ещё незавершенные блоки
            function haveWork(lines){
                var i=0,j=0;
                for(i=0;i<lines.length;i++){
                    if(lines[i]["blocks"].length>0){
                        return true;
                    }
                }
                return false;
            }
            var mul=0;
            function MullWidth(tm){
                mul=tm;
                var i=0,j=0;
                for(i=0;i<lines.length;i++){
                    for(j=0;j<lines[i]["blocks"].length;j++){
                        lines[i]["blocks"][j]["width"]*=tm;   //перевожу продолжительности работы в секунды
                    }
                }
            }
            function getColorByOperation(operation,block,timeBegin){  //для отображения
                var tb=new Date(timeBegin);
                switch(operation){  //просроченных блоков нужно время начала
                    case -1:return "#16D6C2";   //00CC66 подготовка
                    case -2:return "#003399";    // CC3333 очистка
                    case -3:return "#DCDCDA";
                    case -4:return "#CAC9C1";   // 33DCDA до начала после окончания 
                    case -5:return "#DCDC33";   //для просроченных                    
                    default:
                        if(dateExecution[block["id_order"]]>tb.setSeconds(tb.getSeconds()+block["width"]))
                            return "#99CCFF";
                        return "#ED1B24";// 808080 рабочий просроченный блок
                }
            }
            function getBlockTitle(block){
                var res="";
                if(block["id_operation"]==-1){
                    res=
                        "Подготовка оборудования\n"+
                        "Продолжительность "+block["time_length"];
                }
                if(block["id_operation"]==-2){
                    res=
                        "Очистка оборудования\n"+
                        "Продолжительность "+block["time_length"];
                }        
                if(block["id_operation"]==-3){
                    res=
                        "Простой\n"+
                        "Продолжительность "+block["time_length"];
                } 
                if(block["id_operation"]>-1){
                    res=
                        "Код детали "+block["id_detail"]+"\n"+
                        "Код операции "+block["id_operation"]+"\n"+
                        "Код заказа "+block["id_order"]+"\n"+
                        "Количество деталей "+block["number_details"]+"\n"+
                        "Продолжительность "+block["time_length"];
                }
                return res;
            }
            
            function GetSumm(s){
                var a=jQuery("#table_line_"+s+" td");
                s=0;
                for(var i=0;i<a.length;i++){
                    s+=Number(a[i].width);
                }
                return s;
            }
            
            //функция отображающая день на таблице времени и на линиях
            function AddDay(lines,startDay,endDay){
                //ещё раз на данный момент предполагаем что время в блоках указано в секундах
                //например для отображения одного дня установим ширину 480 px для часа - 60px
                var hour_width=60*m;
                var day_width=480*m;
                
                var fd={
                    year:"numeric",
                    month:"numeric",
                    day:"numeric"
                };
                var ft={
                    hour: 'numeric',
                    minute: 'numeric',
                    second: 'numeric'
                }
                var ar=["",""];
                //с заголовком относительно просто - это всегда дата+часы, ну ок
                ar[0]="<td width='"+day_width+"'><table class='time_table' cellpadding=\"0\" cellspacing='0' style='table-layout: fixed; width: "+day_width+"px;'><tr><td width='"+day_width+"' colspan='9'>"+startDay.toLocaleString("ru",fd)+"</td></tr><tr>";
                for(var i=9;i<=17;i++){
                    ar[0]+="<td width='"+hour_width+"'>"+i+":00</td>";
                }
                ar[0]+="</tr></table></td>";
                //со временем разобрались теперь отобразим время на линиях
                //если время в startDay - не начало рабочего дня - к каждой линии
                //нужно добавить ещё 1 блок
                var blocks_for_day="";
                var start_block="";
                if(startDay.getHours()!=9 || startDay.getMinutes()!=0 || startDay.getSeconds()!=0){
                    //получается что 1 минута у нас равна одному пикселу
                    var begin_day=new Date(startDay);
                    begin_day.setHours(9);
                    begin_day.setMinutes(0);
                    begin_day.setSeconds(0);
                    var w=((startDay-begin_day)*m)/1000/60;
                    start_block="<td title='До запуска' bgcolor='"+getColorByOperation(-4)+"' width='"+w+"'></td>";                    
                }
                var current_time;   //текущее время для линии
                for(var i=0;i<lines.length;i++){
                    blocks_for_day=start_block;
                    current_time=new Date(startDay);
                    //теперь стаскиваем с линии блоки пока они помещаются
                    //или не закончатся, для каждой линии храним время, если день
                    //заполнен не полностью и блок не помещается целиком, отгрызаю
                    //кусочек чтобы заполнить день
                    var name,cl,events,props;
                    while(current_time!=endDay){
                        name="";
                        cl="";
                        events="";
                        props="";
                        if(lines[i]["blocks"].length==0){   //если на линии закончились блок
                            var w=((endDay-current_time)*m)/60000;
                            blocks_for_day+="<td bgcolor='"+getColorByOperation(-4)+"' title='После завершения' width='"+w+"'></td>";
                            current_time=endDay;
                        }else{  //если ещё есть блок -
                            //считаю сколько ещё секунд, СЕКУНД рабочего времени
                            //осталось, впрочем я постоянно это делаю
                            if(lines[i]["blocks"][0]["id_operation"]>-1){
                                name=lines[i]["blocks"][0]['id_order'];
                                cl=" class='line_"+lines[i]["id_line"]+" selectable_order order_"+name+"' ";
                                events=" onmouseenter='SetCurrentOrder("+name+");' onmouseout='ClearCurrentOrder();' ";
                                props=" current_time='"+current_time.toLocaleString("ru",fd)+" "+current_time.toLocaleString("ru",ft)+"' "+
                                    " id_detail='"+lines[i]["blocks"][0]["id_detail"]+"' "+
                                    " id_operation='"+lines[i]["blocks"][0]["id_operation"]+"' "+
                                    " number_details='"+lines[i]["blocks"][0]["number_details"]+"' ";
                            }else{
                                props=" current_time='"+current_time.toLocaleString("ru",fd)+" "+current_time.toLocaleString("ru",ft)+"' "+
                                    " id_operation='"+lines[i]["blocks"][0]["id_operation"]+"' ";
                                cl=" class='line_"+lines[i]["id_line"]+"' ";          
                            }
                            var s=(endDay-current_time)/1000;
                            if(lines[i]["blocks"][0]["width"]<=s){
                                var w=lines[i]["blocks"][0]["width"]*m/60;//в блоке время указано в секундах
                                //перевожу в минуты и соответственно пикселы
                                blocks_for_day+="<td period='"+lines[i]["blocks"][0]["width"]+"' "+cl+events+props+" bgcolor='"+getColorByOperation(lines[i]["blocks"][0]["id_operation"],lines[i]["blocks"][0],current_time)+"' title='"+getBlockTitle(lines[i]["blocks"][0])+"' width='"+w+"'>"+name+"</td>";
                                current_time.setSeconds(current_time.getSeconds()+lines[i]["blocks"][0]["width"]);
                                //выкидываем один элемент из массива блоков
                                lines[i]["blocks"].splice(0,1);
                            }else{  //если блок не помещается в этом дне его нужно разбить на 2 блока
                                //на нулевой блок можно не обращать внимания, он будет удалён
                                var w=((endDay-current_time)*m)/1000/60;
                                //перевожу в минуты и соответственно пикселы
                                blocks_for_day+="<td period='"+((endDay-current_time)/1000)+"' "+cl+events+props+" bgcolor='"+getColorByOperation(lines[i]["blocks"][0]["id_operation"],lines[i]["blocks"][0],current_time)+"' title='"+getBlockTitle(lines[i]["blocks"][0])+"' width='"+w+"'>"+name+"</td>";
                                //просто уменьшим время блока на то что отпилили
                                lines[i]["blocks"][0]["width"]-=(endDay-current_time)/1000;
                                current_time=endDay;
                            }
                        }   
                    }
                    ar[1]+="<tr><td><table class='table_lines' id='table_line_"+i+"' cellpadding=\"0\" style='height:20px;' cellspacing='0'><tr>"+blocks_for_day+"</tr></table></td></tr>";
                }
                ar[1]="<td id='td_line_table'><div id=\"client_div\" onmouseover=\"addLine ()\" onmouseout=\"removeLine ()\"><div id=\"int\"></div><table class='table_lines_day' cellpadding=\"0\" cellspacing='0'>"+ar[1]+"</table><div></td>";
                return ar;
            }
           /* 
            function CreateLinePlan(id_line){
                var arr=jQuery("td.line_"+id_line);
                var html="";
                for(var i=0;i<arr.length;i++){
                    html+="<tr>";
                    html+="<td>"+(i+1)+"</td>";
                    html+="<td>"+arr[i].getAttribute("current_time")+"</td>";
                    html+="<td>"+(arr[i].getAttribute("period")/mul)+"</td>";
                    if(arr[i].getAttribute("id_operation")>-1){
                        html+="<td>"+arr[i].getAttribute("id_detail")+"</td>";
                        html+="<td>"+arr[i].getAttribute("number_details")+"</td>";
                        html+="<td>"+arr[i].getAttribute("id_operation")+"</td>"; 
                    }else{
                        html+="<td></td>";
                        html+="<td></td>";
                        switch(Number(arr[i].getAttribute("id_operation"))){
                            case -1:html+="<td>Подготовка оборудования</td>";break;
                            case -2:html+="<td>Очистка оборудования</td>";break;
                            case -3:html+="<td>Простой оборудования</td>";break;
                            default:html+="<td></td>";
                                
                        }
                    }                   
                    html+="</tr>";                    
                }
                jQuery("#table_content_line_plan tbody").html(html);
                jQuery("#h2_line_name").html("Линия №"+id_line);
            }
            */
            function ClearCurrentOrder(){
                jQuery(".current_order").removeClass("current_order");
            } 
            
            function SetCurrentOrder(order){
                jQuery(".order_"+order).addClass("current_order");
            }
            
            function CreatePlan(){            
                var time_table="";
                var line_table="";
                var startDay=new Date(Date.parse("<%=pul%>"));  //дата начала первого дня
                if(startDay.getHours()<9){  //если решили начать до начала рабочего дня
                    startDay.setHours(9);    //переношу на начало рабочего дня
                    startDay.setMinutes(0);
                    startDay.setSeconds(0);
                }
                if(startDay.getHours()>=17){    //если решили начать после окончания
                    startDay.setDate(startDay.getDate()+1);//переношу на следующий день
                    startDay.setHours(9);    //на начало дня 
                    startDay.setMinutes(0);
                    startDay.setSeconds(0);
                }
                var endDay=new Date(startDay);  //тот же рабочий день
                endDay.setHours(17);            //только его окончание
                endDay.setMinutes(0);
                endDay.setSeconds(0);
                var ar;

                while(haveWork(lines)){
                    ar=AddDay(lines,startDay,endDay);
                    time_table+=ar[0];
                    line_table+=ar[1];
                    //переходим к следующему дню
                    startDay.setDate(startDay.getDate()+1);
                    startDay.setHours(9);
                    startDay.setMinutes(0);
                    startDay.setSeconds(0);
                    endDay.setDate(endDay.getDate()+1);                
                }
                //time_table="<table cellspacing='0'><tr>"+time_table+"</tr></table>";
                //line_table="<table cellspacing='0'><tr>"+line_table+"</tr></table>";
                var headers = "<td><div id='headers'><table cellspacing='0'>";
                for(var i=0;i<lines.length;i++){
                               headers += "<tr><td style='height:20px;' align=\"right\" style=\"white-space: nowrap;margin-top:10pt\" height=\"10px\"><a href=\"javascript: showModalWin2('popupWin"+lines[i]["id_line"]+"');\"><font color=#FFFFCC><nobr>Линия №\n\
                        " +lines[i]["id_line"]+"\t"+lines[i]["name"] + "\n\
                        </nobr></font></a></td></tr>";
                }
                headers += "</table></div></td>";
                window.document.getElementById("td_time_table").innerHTML="<td> <font color=#FFFFFF><strong>Линии:</strong></font></td>";
                window.document.getElementById("td_time_table").innerHTML+=time_table;
                window.document.getElementById("out").innerHTML=headers;
                window.document.getElementById("out").innerHTML+=""+line_table+"<div style='width:50px'></div>";  
            }
        </script>

        <style>
            .current_order{
                background-color: #69c03b;
            }
            .table_line_day{
                width:100%;
            }
            .table_line_day td{
                cursor:pointer;
                box-shadow: 0px 0px 1px gray inset;
            }
            
            .table_lines{
                border-collapse: collapse;
            }
            
            #table_lines td{
                border-top: 1px solid transparent;
                border-bottom: 1px solid transparent;
            }
            
            #td_line_table td{
                border-top: 1px solid gray;
                border-bottom: 1px solid gray;
                text-align: center;
                
            }
            
            .table_lines td {
                border: 1px solid gray;
            }
            
            .time_table td{
                border:solid 1px gray;
            }
            
            .time_table td:nth-child(2n){
                background-color: #F4E8BD;
            }
            .time_table td:nth-child(2n+1){
                background-color: #C2B6A0; 
            }
            .shadow {
                margin: 10px;
                background-color: #E8D3A4;
                background: linear-gradient(to left, #5a567f,#E8D3A4,#5a567f);
                background: linear-gradient(to right, #5a567f,#E8D3A4);
                box-shadow: 0.4em 0.4em 20px rgba(0,0,0,5);
                border-radius: 4px;
                padding: 10px;
                border: 4px solid #F0E3C6;
/* прозрачность*/				-moz-opacity: 0;
                -khtml-opacity: 0;
                font-size: 15px;
                opacity: 0.9;
                width: 900px;
                overflow: auto;
            }

            .m td{
                outline: 1pt solid #3300CC;
            }
            .mainClass tr:hover {
                background-color:#cfc;
            }
            .mainClass td:hover{
                background-color:#fcf;
            }

            /*#out {position: relative; border: 0px solid #000; margin: 0 0}*/
            #out {position: relative; border: 0px solid #000; width:110%; margin: 0 0}
            /*#int {position: absolute; width: 1px; oveflow: hidden; background-color: red; top: 0; display: none}*/
            #int {position: absolute; width: 1px; oveflow: hidden; background-color: red; display: none}


            #out2 {
		opacity:0.9;		
                position: relative;
		border: 0px solid #000;
		padding: 40px;
		text-align: center;
}




        </style>
        <%-- <script lang="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script> --%> 
        <script>
            function addLine()
            {
                if (!document.getElementById('client_div').getAttribute('lft'))
                {
                    var l = 0, obj = document.getElementById('client_div');
                    while (obj.offsetParent)
                        l += obj.offsetLeft, obj = obj.offsetParent;
                    document.getElementById('client_div').setAttribute('lft', l + document.getElementById('client_div').offsetWidth
                            - document.getElementById('client_div').clientWidth);
                    document.getElementById('int').style.height = document.getElementById('client_div').clientHeight + 'px';
                }
                document.getElementById('int').style.display = 'block';
                document.onmousemove = function(e)
                {
                    var e = e || window.event;
                    document.getElementById('int').style.left = (e.pageX ? e.pageX : e.clientX)
                            - document.getElementById('client_div').getAttribute('lft')
                            + document.getElementById('headers').clientWidth + /*document.getElementById('out2').style.getPropertyValue('padding')*/40 - 2
                            + document.getElementById('out2').scrollLeft + 'px';
                }
            }
            function removeLine() {
                document.getElementById('int').style.display = 'none';
                document.onmousemove = ''
            }

            function myTime()
            {
                var mydate = new Date();
                s = mydate.getHours() + "       Часов" + mydate.getMinutes() + " Мин," + mydate.getSeconds() + " Сек   ";
                document.myform.mytext.value = s;
                setTimeout("myTime()", 1000);
            }


            function myFuncOver(e)
            {
                var e = e || window.event, o = e.srcElement || e.target;
                var s = o.className;
                if (s != "")
                    //while (o.tagName != 'TABLE')
                    //o = o.parentNode;
                    for (var j = 0, o = document.getElementsByTagName('TD'), J = o.length; j < J; j++)
                        if (o [j].className == s)
                            o [j].style.backgroundColor = 'lime';
            }
            function myFuncOut(e)
            {
                var e = e || window.event, o = e.srcElement || e.target;
                var s = o.className;
                if (o.className != "")
                    //while (o.tagName != 'TABLE')
                    //o = o.parentNode;
                    for (var j = 0, o = document.getElementsByTagName('TD'), J = o.length; j < J; j++)
                        if (o [j].className == s)
                            o [j].style.backgroundColor = '#A5CBF0';
            }
            var m=1;
            function ChangeM(){
                m=jQuery("#select_m").val();
                ReInitData();    //должно быть 1
                MullWidth(mul);
                CreatePlan();
                jQuery("#out").removeAttr("lft");
            }
            
        </script>

    </head>
     

    
    <body bgcolor="#DCDCDA">
        
           
               <div class="Divbg bg2" ></div>  
           
                    

        

			<%@include file="HeadSapcon.jsp"%>

        <table align="center">
            <tr>
                <td>
                   
                    <table align="center">
		<tr>
		<td colspan="2"> 
          
                     <%if (role == 1) {
                    %>
                   <%@include file="menuHead.jsp" %>
    
                    <%
                    } else if (role == 3) {
                    %>
                    <%@include file="menuDuty.jsp" %>
                    >

                    <%
                        }
                    %>
  
  
                 
                    
&nbsp;</td>
						</tr>
					
		</table>
                </td>
            </tr>

            <tr>
                <td>
                    <div class="tableinput2" title="Информация" style="background-color:#DCDCDA; width:400px">
                        Цех №1<br/>

                        Дата начала выполнения плана : <%=pul_view%><br/>
                        
                        Дата завершения выполнения плана : <%=pul2_view%><br/>          
   					
                      
            <a href="javascript: showModalWin2('popupWin');">Список заказов</a><br/><br/> 
            
     
                        <table style="width: 411px">
                            <tr>
                                <td bgcolor="16D6C2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Подготовка оборудования</td>
                            </tr>
                            <tr>
                                <td bgcolor="003399">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Очистка оборудования</td>
                            </tr>
                            <tr>
                                <td bgcolor="99CCFF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Режим работы</td>
                            </tr>
                            <tr>
                                <td bgcolor="ED1B24">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Просроченные заказы</td>
                            </tr>
                             <tr>
                                <td bgcolor="69c03b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Выбранный заказ</td>
                            </tr>
                             <tr>
                                <td bgcolor="CAC9C1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Время до и после завершения работы линии</td>
                            </tr>
                            <tr>
                                <td bgcolor="DCDCDA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td> - Простой линии</td>
                            </tr>
                            <tr>
                                <td>
                                    Масштаб
                                </td>
                                <td>
                                    <select id="select_m" onChange="ChangeM();">
                                        <option value="1">X 1</option>
                                        <option value="2">X 2</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>                    
                </td>
            </tr>
            <tr>
                <td>    
                    <br><br>
                    
                    <%            out.print(model.getHtmlPlan());
                    %>
                     
                </td>
            </tr>
            
        </table>
 <!-- Наше модальное всплывающее окно -->
        <div id="popupWin" class="modalwin add" style="text-align: center" >
            <iframe src="OrderContr?action=list2&title=true" frameborder="0" onload="this.height=this.contentWindow.document.body.scrollHeight; this.width=this.contentWindow.document.body.scrollWidth;" scrolling="no"></iframe>
        </div>
 
        <div style="text-align: center;" id="line_plan" class="modalwin">
            <h2 id="h2_line_name"></h2>
            <div style="max-height:386px;overflow-y: auto;">
                <table align ="center" border="1" class="table_blur" id="table_content_line_plan">
                    <thead>
                        <tr>
                            <th>
                                Номер
                            </th>
                            <th>
                                Время начала                    
                            </th>
                            <th>
                                Продолжительность
                            </th>
                            <th>
                                Деталь
                            </th>
                            <th>
                                Кол-во деталей
                            </th>
                            <th>
                                Код операции
                            </th>                
                        </tr>
                    </thead>
                        <tbody>
                        </tbody>
                </table>
            </div>
        </div>
        <script>
            ReInitData();    //должно быть 1
            MullWidth(1);
            CreatePlan();
        </script>
    </body>
</html>