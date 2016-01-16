package Input;

import Database.DataBase;
import java.io.IOException;
import java.util.LinkedList;

public class Mode_Feeling extends Abstract_Mode {

    public Mode_Feeling(DataBase database) throws IOException {
        super(database);
        analyzers = new LinkedList<Abstract_Mode_parts>();
        //-----触られた時に利用する解析知能一覧-------
        //環境解析知能
        analyzers.add(new Mode_Feeling_Nomal(database));
        //etc...
        //------------------------------
        init();
    }
}
