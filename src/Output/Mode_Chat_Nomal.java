/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Output;

import Database.DataBase;
import java.io.IOException;

public class Mode_Chat_Nomal extends Abstract_Mode_parts {

    public Mode_Chat_Nomal(DataBase database) throws IOException {
        super(database);
        about = "日常会話フレーズ";
    }

    //しかし、返答はなにが来たとしてもそうだよとしか返さない
    @Override
    public String Action() {
        String reply = "なるほど";
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
                reply = "はい！";
            }

            s = "よね";
            if (s1.indexOf(s) != -1) {
                reply = "そうですね";
            }
            
             s = "あ";
            if (s1.indexOf(s) != -1) {
                reply = "たしかに";
            }

            return reply;
        }
        return reply;
    }

    //自分から質問したいレベルを設定する
    @Override
    public int getTimeActionLevel() {
        return 0;
    }
    
    //ここには質問するならどういう質問をするかを記述
    @Override
    public String TimeAction() {
        return "自発的に質問をさせたい場合TimeActionLevelを上げましょう";
    }
    
        /*質問系統の内容が来たら絶対に返答したい！と１００を返す
    @Override
    public int getActionLevel() {
        /*
        String s1;
        database.showData();
        s1 = (String) chatLog.pop();
       
        chatLog.push(s1);
        
        if (s1.indexOf("?") != -1) {
            return 100;
        }
        
        return 0;
    }*/



}
