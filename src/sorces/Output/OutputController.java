package Output;

import Database.DataBase;
import java.io.IOException;
import java.util.LinkedList;
/*
 このクラスは出力をまとめるもの
 */

public class OutputController {

    LinkedList<Abstract_Mode> outputList;
    DataBase database;

    public OutputController(DataBase database) throws IOException {
        outputList = new LinkedList<Abstract_Mode>();
        this.database = database;

        //----------出力モード新規登録場所------------
        outputList.add(new Mode_Motion(database));
        outputList.add(new Mode_Chat(database));
        //-----------------------------------
        
    }
    
    public String getJson() throws IOException {
        String makeJson = "{";
        for (int i = 0; outputList.size() > i; i++) {
            if (!"{".equals(makeJson)) {
                makeJson += ",";
            }
            makeJson += "'" + outputList.get(i).getClass().getSimpleName() + "':'";
            makeJson += outputList.get(i).getOutput() + "'";
        }
        makeJson += "}";
        
        return makeJson;
    }
    
    //時間経過で呼び出される動作
    public String getTimeAction() throws IOException{
        String makeJson = "{";
        for (int i = 0; outputList.size() > i; i++) {
            if (!"{".equals(makeJson)) {
                makeJson += ",";
            }
            makeJson += "'" + outputList.get(i).getClass().getSimpleName() + "':'";
            makeJson += outputList.get(i).getOutput() + "'";
        }
        makeJson += "}";
        return makeJson;
    }
    
    public LinkedList<Abstract_Mode> getList(){
        return this.outputList;
    }
    public int getListSize(){
        return this.outputList.size();
    }
}
