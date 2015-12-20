/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Database.DataBase;
import java.io.IOException;
import java.util.LinkedList;

public class Mode_Topic extends Abstract_Mode {

    public Mode_Topic(DataBase database) throws IOException {
        super(database);
        analyzers = new LinkedList<Abstract_Mode_parts>();
        //-----会話解析に利用する解析知能一覧-------
        //会話ログを収集する知能
        analyzers.add(new Mode_Topic_kankore(database));
        analyzers.add(new Mode_Topic_PazzleAndDragons(database));
        //analyzers.add(new Mode_ChatLog_SaveLog(database));
        //etc...
        //------------------------------
        init();
    }
}
