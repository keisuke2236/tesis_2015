package Output;

import Database.DataBase;
import java.io.IOException;


public class Mode_Chat_kankore extends Abstract_Mode_parts {
    public Mode_Chat_kankore(DataBase database) throws IOException {
        super(database);
        about = "艦これ";
    }


    
    @Override
    public String Action() {
        String reply = "！すでのな";
        if (!chatLog.empty()) {
            String s1 = (String) chatLog.peek();//最後の会話ログを用いる
            //chatLog.push(s1);
            String s = "天気";
            if (s1.indexOf(s) != -1) {
                reply = "今日の海域も天気が良いのです！";
            }
            s = "こんにち";
            if (s1.indexOf(s) != -1) {
                reply = "はわわわ、司令官さん、こんにちはなのです";
            }

            s = "しようよ";
            if (s1.indexOf(s) != -1) {
                reply = "なのです！";
            }

            s = "よね";
            if (s1.indexOf(s) != -1) {
                reply = "そうですね、さすが提督さんなのです";
            }

            return reply;
        }
        return reply;
    }

    @Override
    public int getTimeActionLevel() {
        return 5;
    }
    
    @Override
    public String TimeAction() {
        return "やっほーって自分からはなしかけちゃうんだ";
    }
}
