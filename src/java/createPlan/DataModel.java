package createPlan;

import java.util.ArrayList;
import java.util.Calendar;

public class DataModel {

    public ArrayList<Line> lines = new ArrayList<Line>();
    public Calendar StartTimeDataModel = Calendar.getInstance();

    @Override
    public String toString(){   //вывод в текст для дебага
        String s= "\n\t\t1234567890123456789012345678901234567890123456789012345678901234567890";
        s=          "\t\t         1         2         3         4         5         6         7"+s;
        for(Line l:lines){
            s=s+"\n"+l.toString();
        }
        return s;
    }
    
    public long getEnergy() {
        return getEndDate().getTimeInMillis();
    }
    
    public void addLine(Line l){
        this.lines.add(l);
    }

    public Calendar getEndDate() {
        Calendar res = Calendar.getInstance();
        res = lines.get(0).getEndDate();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getEndDate().before(res)) {
                res = lines.get(i).getEndDate();
            }
        }
        return res;
    }

    public int getSizeLines() {
        return this.lines.size();
    }

    public String getNameLine(int i) {
        return this.lines.get(i).getName();
    }

    public Line getLine(int i) {
        return this.lines.get(i);
    }
}
