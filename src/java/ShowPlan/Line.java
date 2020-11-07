package ShowPlan;

import java.util.ArrayList;
import java.util.Calendar;

public class Line {

    private Calendar startTimeModel; // начало операций на линии
    private int idLine; // id линии
    private String name; // название линии
    private String idWorkplace; // id рабочего центра
    private ArrayList<Block> blocks = new ArrayList<Block>(); // список блоков на линии

    public ArrayList<Block> getBlocks(){
        return blocks;
    }
    
    public String getIdWorkplace() {
        return this.idWorkplace;
    }

    public String getCurrentName(){
        return name;
    }
    
    public void setIdWorkplace(String idWorkplace) {
        this.idWorkplace = idWorkplace;
    }

    public void setStartTimeModel(Calendar cal) {
        this.startTimeModel = (Calendar) cal.clone();
    }

    public void setIdLine(int id) {
        this.idLine = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListBlock(ArrayList<Block> b) {
        this.blocks = b;
    }

    public int getIdLine() {
        return idLine;
    }

    public int getSize() {
        return this.blocks.size();
    }

    public Block getBlock(int index) {
        return blocks.get(index);
    }

    public String getName() {
        return Integer.toString(this.idLine);
    }

    public int getSkip(int blok) { // получить простой для блока
        long res = 0;
        if (blok == 0) { // если первый блок на линии
            long le = this.blocks.get(blok).getDateBegin().getTimeInMillis(); // время начала блока
            long l2 = this.startTimeModel.getTimeInMillis(); // время начала линии
            res = (le - l2) / 1000;
            return (int) res;
        } else { // (blok > 0) 
            res = (this.blocks.get(blok).getDateBegin().getTimeInMillis() - this.blocks.get(blok - 1).getDateBegin().getTimeInMillis()) / 1000; // разница между началом текущего и предыдущего блоков
            //res = res - (long)(this.blocks.get(blok - 1).get getWidth() * 1000);
            return ((int) res) - this.blocks.get(blok - 1).getWidth(); // вычисленная разница минус длина предыдущего блока и есть простой для текущего блока
        }

    }

    public void addBlock(Block b) {
        this.blocks.add(b);
    }

    public Calendar getEndDate() {
        if (!blocks.isEmpty()) {
            return this.blocks.get(blocks.size() - 1).getEndDate();
        } else {
            return null;
        }
    }

}
