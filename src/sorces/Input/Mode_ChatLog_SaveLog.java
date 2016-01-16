package Input;

import Database.DataBase;
import Utils_SystemFile.GoogleApi;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * チャットのログを解析するクラス 正直これをモードとして実装するのはどうかと思っているが 全体として同じ形で処理を行いたいため、モードとして記述している。
 * ログを保存する知能を別に作りたい人が居た場合、AnalyzeLevelを1以上にすればそちらが優先されます。
 */
public class Mode_ChatLog_SaveLog extends Abstract_Mode_parts {

    public Mode_ChatLog_SaveLog(DataBase data) throws IOException {
        super(data);
        about = "パズドラ";
    }

    @Override
    public boolean ChatAnalyze(String chat) {
        //ユーザーの入力情報をgoogleapiに投げて保存しておく
        GoogleApi google = (GoogleApi) database.getData("google");
        stack.push(chat);

        if (google == null) {
            try {
                google = new GoogleApi(database);
            } catch (IOException ex) {
                Logger.getLogger(Mode_ChatLog_SaveLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String[] a = {chat};
        try {
            google.searchWords_Merge(a);
        } catch (IOException ex) {Logger.getLogger(Mode_ChatLog_SaveLog.class.getName()).log(Level.SEVERE, null, ex);}
        database.setData("google", google);

        return true;
    }

    @Override
    public boolean TouchAnalyze(String bui) {
        return false;
    }

    @Override
    public boolean addObjAnalyze(String name, String bui) {
        return false;
    }

    @Override
    public int getAnalyzeLevel() {
        return 1;
    }

}
