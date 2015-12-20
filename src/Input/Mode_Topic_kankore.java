/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Database.DataBase;
import java.io.IOException;


public class Mode_Topic_kankore extends Abstract_Mode_parts {

    public Mode_Topic_kankore(DataBase database) throws IOException {
        super(database);
        about = "艦これ";
    }

    @Override
    public boolean ChatAnalyze(String chat) {
        stack.push(chat);
        return true;
    }

    @Override
    public boolean TouchAnalyze(String bui) {
        
        return true;
    }

    @Override
    public boolean addObjAnalyze(String name, String bui) {
        return true;
    }

    @Override
    public int getAnalyzeLevel() {
        return 10;
    }


    
}
