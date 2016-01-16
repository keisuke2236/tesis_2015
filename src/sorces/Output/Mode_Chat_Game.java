package Output;

import Database.DataBase;
import java.io.IOException;


public class Mode_Chat_Game extends Abstract_Mode_parts {
    public Mode_Chat_Game(DataBase database) throws IOException {
        super(database);
        about = "ゲーム";
    }


    
    @Override
    public String Action() {
        String reply = "ゲームって楽しいよね";
        if (!chatLog.empty()) {
            String s1 = (String) chatLog.peek();//最後の会話ログを用いる
            //chatLog.push(s1);
            String s = "勝";
            if (s1.indexOf(s) != -1) {
                reply = "おめでとぉー";
            }
            s = "負";
            if (s1.indexOf(s) != -1) {
                reply = "ゲームオーバーだねー";
            }
            s = "こんにち";
            if (s1.indexOf(s) != -1) {
                reply = "はわわわ、司令官さん、こんにちはなのです";
            }

            s = "しようよ";
            if (s1.indexOf(s) != -1) {
                reply = "はい！";
            }

            s = "よね";
            if (s1.indexOf(s) != -1) {
                reply = "そうですね";
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
