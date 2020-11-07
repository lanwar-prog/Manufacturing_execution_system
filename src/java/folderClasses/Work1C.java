/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folderClasses;

import java.sql.Types;
import java.util.ArrayList;


public class Work1C {
    public void ReloadArticleRestFrom1C(){        
        Connect1C C1C=Connect1C.getInstance("file=\"X:\\db\";usr=\"admin\";pwd=\"1\"");
        String OldWorkHouse="";
        Object[] Row;
        WorkDB wdb=new WorkDB();
        //отправка запроса к 1с, получение результатов
        ArrayList<Object[]> Data=C1C.GetFrom1C(
            "ВЫБРАТЬ различные \n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Номенклатура.Наименование КАК НоменклатураНаименование,\n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Номенклатура.Код КАК НоменклатураКод,\n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Склад.Наименование КАК СкладНаименование,\n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Склад.Код КАК СкладКод,\n" +
            "    СУММА(ТоварыНаСкладахОстаткиИОбороты.КоличествоПриход) как Приход,\n" +
            "    СУММА(ТоварыНаСкладахОстаткиИОбороты.КоличествоРасход) как Расход,    \n" +
            "    СУММА(ТоварыНаСкладахОстаткиИОбороты.КоличествоПриход - ТоварыНаСкладахОстаткиИОбороты.КоличествоРасход) КАК Остаток,    \n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Номенклатура.БазоваяЕдиницаИзмерения.Наименование как ЕдиницаИзмеренияНаименование, "+
            "    ТоварыНаСкладахОстаткиИОбороты.Номенклатура.БазоваяЕдиницаИзмерения.Код как ЕдиницаИзмеренияКод "+	  
            "ИЗ\n" +
            "    РегистрНакопления.ТоварыНаСкладах.ОстаткиИОбороты(, , Регистратор, , ) КАК ТоварыНаСкладахОстаткиИОбороты\n" +
            "СГРУППИРОВАТЬ ПО\n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Номенклатура,\n" +
            "    ТоварыНаСкладахОстаткиИОбороты.Склад\n" +
            "упорядочить по\n" +
            "	ТоварыНаСкладахОстаткиИОбороты.Склад.Код,\n" +
            "	ТоварыНаСкладахОстаткиИОбороты.Номенклатура.Код",
            new String[]{
                "НоменклатураНаименование",
                "НоменклатураКод",
                "СкладНаименование",
                "СкладКод",
                "Приход",
                "Расход",
                "Остаток",
                "ЕдиницаИзмеренияНаименование",
                "ЕдиницаИзмеренияКод"},
            new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.FLOAT,
                Types.FLOAT,
                Types.FLOAT,                    
                Types.VARCHAR,
                Types.VARCHAR
            });
        wdb.DelAllArticleRest();                        //выбрасываю все старые данные из таблицы
        for(int i=0;i<Data.size();i++){
            Row=Data.get(i);
            if(!OldWorkHouse.equals((String)Row[3])){
                wdb.AddEditWorkHouse((String)Row[3], (String)Row[2]);  //запись в MySQL нового склада
                OldWorkHouse =(String)Row[3];
            }
            wdb.AddEditEdIzm((String)Row[8], (String)Row[7]);
            wdb.AddEditArticle((String)Row[1],(String)Row[8], (String)Row[0]);        //запись в MySQL нового товара
            wdb.AddEditArticleRest((String)Row[1], (String)Row[3], (Float)Row[4], (Float)Row[5], (Float)Row[6]);  //запись остатка товара в таблицу article_rest
        }
    }

    //загрузка данных по остаткам из 1с в mysql
    public void ReloadArticleExpFrom1C(){        
        Connect1C C1C=Connect1C.getInstance("file=\"X:\\db\";usr=\"admin\";pwd=\"1\"");
        Object[] Row;
        WorkDB wdb=new WorkDB();
        //отправка запроса к 1с, получение результатов
        ArrayList<Object[]> Data=C1C.GetFrom1C(
            "ВЫБРАТЬ \n" +
            "	ТоварыВЭксплуатации.Подразделение.Код Как ПодразделениеКод,\n" +
            "	ТоварыВЭксплуатации.Подразделение.Наименование как ПодразделениеНаименование,\n" +
            "	ТоварыВЭксплуатации.ФизЛицо.Код как ФизЛицоКод,\n" +
            "	ТоварыВЭксплуатации.ФизЛицо.Наименование как ФизЛицоНаименование,\n" +
            "	ТоварыВЭксплуатации.Номенклатура.Код как НоменклатураКод,\n" +
            "	ТоварыВЭксплуатации.Номенклатура.Наименование Как НоменклатураНаименование,\n" +
            "   СУММА(ТоварыВЭксплуатации.КоличествоПриход) как Приход,\n" +
            "   СУММА(ТоварыВЭксплуатации.КоличествоРасход) как Расход,    \n" +
            "   СУММА(ТоварыВЭксплуатации.КоличествоПриход - ТоварыВЭксплуатации.КоличествоРасход) КАК Остаток,\n" +
            "	ТоварыВЭксплуатации.Номенклатура.БазоваяЕдиницаИзмерения.Код как ЕдиницаИзмеренияКод\n "+
            "ИЗ\n" +
            "    РегистрНакопления.МатериалыВЭксплуатации.ОстаткиИОбороты(, , Регистратор, , ) КАК ТоварыВЭксплуатации\n" +
            "СГРУППИРОВАТЬ ПО\n" +
            "	ТоварыВЭксплуатации.ФизЛицо,\n" +
            "    ТоварыВЭксплуатации.Номенклатура,\n" +
            "	ТоварыВЭксплуатации.Подразделение\n" +
            "упорядочить по\n" +
            "    ТоварыВЭксплуатации.Подразделение.Код,\n" +
            "    ТоварыВЭксплуатации.Номенклатура.Код,\n" +
            "    ТоварыВЭксплуатации.ФизЛицо.Код",
            new String[]{
                "ПодразделениеКод",
                "ПодразделениеНаименование",
                "ФизЛицоКод",
                "ФизЛицоНаименование",
                "НоменклатураКод",
                "НоменклатураНаименование",
                "Приход",
                "Расход",
                "Остаток",
                "ЕдиницаИзмеренияКод"},
            new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.FLOAT,
                Types.FLOAT,
                Types.FLOAT,
                Types.VARCHAR
            });

        String OldUnit="",OldArticle="";
        wdb.DelAllArticleExp();                        //выбрасываю все старые данные из таблицы
        for(int i=0;i<Data.size();i++){
            Row=Data.get(i);
            if(!OldUnit.equals((String)Row[0])){
                wdb.AddEditUnit((String)Row[0], (String)Row[1]);  //запись в MySQL нового подразделения
                OldUnit =(String)Row[0];
            }
            if(!OldArticle.equals((String)Row[4])){
                wdb.AddEditArticle((String)Row[4],(String)Row[9], (String)Row[5]);  //запись в MySQL нового материала
                OldArticle =(String)Row[4];
            }
            wdb.AddEditEmployee1C((String)Row[2], (String)Row[3]);        //запись в MySQL нового работника           
            //int id_article,int id_unit,int id_employee,float coming,float expense,float rest
            wdb.AddEditArticleExp((String)Row[4],(String)Row[0],(String)Row[2],
                    (Float)Row[6],(Float)Row[7],(Float)Row[8]);  //запись материала в эксплуатации в таблицу article_exp
        }
    }

    private String FloatFormat(float f){
        String s="";
        if(f%1==0){ //целое
            s=String.valueOf((int)f);
        }else{
            Float.toString(f);
        }
        return s;
    }
    //формирование отчёта по остаткам товаров - подключается к mysql, забирает данные 
    //и выбрасывает html в out
    public void ShowReportArticleRest(){
        WorkDB wdb=new WorkDB();
        ArrayList<Object[]> Data=wdb.GetArticleRest();
        String OldWorkHouse="";
        Object[] Row;
        System.out.println(
                "<table id=\"table_article_rest\">\n"+
                "<tr><th>Склад</th><th></th><th colspan=\"3\">Количество</th></tr>\n"+
                "<tr><th>Номенклатура</th><th>Базовая единица измерения</th><th>Приход</th><th>Расход</th><th>Конечный остаток</th></tr>");
        if(Data.size()>0){
            for(int i=0;i<Data.size();i++){
                Row=Data.get(i);
                if(!OldWorkHouse.equals((String)Row[1])){
                    System.out.println("<tr><td class=\"dt_level_0\">"+(String)Row[3]+"</td><td colspan=\"4\"></td></tr>");
                    OldWorkHouse =(String)Row[1];
                }
                System.out.println("<tr><td class=\"dt_level_1\">"+(String)Row[2]+"</td><td>"+(String)Row[7]+"</td><td>"+FloatFormat((Float)Row[4])+"</td><td>"+FloatFormat((Float)Row[5])+"</td><td>"+FloatFormat((Float)Row[6])+"</td></tr>");                
            }
        }else{
            System.out.println("<tr><td colspan=\"5\">Нет остатков товаров на складах</td></tr>");
        }
        System.out.println("</table>");
    }
    //формирование отчёта по материалам в эксплуатации - подключается к mysql, забирает данные 
    //и выбрасывает html в out
    public void ShowReportArticleExp(){
        WorkDB wdb=new WorkDB();
        ArrayList<Object[]> Data=wdb.GetArticleExp();
        String OldUnit="",OldArticle="";
        Object[] Row;
        System.out.println(
                "<table id=\"table_article_exp\">\n"+
                "<tr><th>Подразделение</th><th></th><th colspan=\"3\">Количество</th></tr>\n"+
                "<tr><th>Номенклатура</th><th rowspan=\"2\">Приход</th><th rowspan=\"2\">Расход</th><th rowspan=\"2\">Конечный остаток</th></tr>\n"+
                "<tr><th>Работник</th></tr>");
        if(Data.size()>0){   
            for(int i=0;i<Data.size();i++){
                Row=Data.get(i);
                if(!OldUnit.equals((String)Row[1])){
                    System.out.println("<tr><td class=\"dt_level_0\">"+(String)Row[7]+"</td><td colspan=\"3\"></td></tr>");
                    OldUnit =(String)Row[1];
                }
                if(!OldArticle.equals((String)Row[0])){
                    System.out.println("<tr><td class=\"dt_level_1\">"+(String)Row[6]+"</td><td colspan=\"3\"></td></tr>");
                    OldArticle =(String)Row[0];
                }
                System.out.println("<tr><td class=\"dt_level_2\">"+(String)Row[8]+"</td><td>"+FloatFormat((Float)Row[3])+"</td><td>"+FloatFormat((Float)Row[4])+"</td><td>"+FloatFormat((Float)Row[5])+"</td></tr>");                
            }  
        }else{
            System.out.println("<tr><td colspan=\"4\">Нет материалов в эксплуатации.</td></tr>");
        }
        System.out.println("</table>");
    }

}
