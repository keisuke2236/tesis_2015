package Output;

import Database.DataBase;
import java.io.IOException;

public class Mode_Motion extends Abstract_Mode { 
    
    public Mode_Motion(DataBase database) throws IOException {
        super(database);
        
        //--------個々に作成した会話知能を登録---------
        //普通の会話知能
        etcTalker.add(new Mode_Motion_Nomal(database));
        //etcTalker.add(new Mode_Motion_Dance(database));
        //----------------------------------------*/
        init();
    }

}
