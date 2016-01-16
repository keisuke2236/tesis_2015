package Output;

import Database.DataBase;
import java.io.IOException;

public class Mode_Chat extends Abstract_Mode {
    
    public Mode_Chat(DataBase database) throws IOException {
        super(database);
       
        //--------個々に作成した会話知能を登録---------
        //普通の会話知能
        etcTalker.add(new Mode_Chat_Game(database));
        //質問回答知能
        etcTalker.add(new Mode_Chat_Ryori(database));
        etcTalker.add(new Mode_Chat_Mario(database));

        //----------------------------------------
        init();
    }
}
