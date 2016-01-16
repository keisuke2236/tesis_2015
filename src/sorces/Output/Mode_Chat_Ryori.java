/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Output;

import Database.DataBase;
import java.io.IOException;
import java.util.Stack;

public class Mode_Chat_Ryori extends Abstract_Mode_parts {

    public Mode_Chat_Ryori(DataBase database) throws IOException {
        super(database);
        about = "料理";
    }

    //しかし、返答はなにが来たとしてもそうだよとしか返さない
    private String tekitou(String s1){
        String reply="お料理するのは好き，私の作ったお料理を食べて，笑顔になってくれるのはもっとすき";
        Stack wadai = (Stack)database.getData("Mode_Topic_Ryori_stack");
        String nowwadai = (String) wadai.peek();
        if(nowwadai == "作"){
            reply = "隠し味にチョコレートいれちゃう？";
        }
        if(nowwadai == "食"){
            reply = "それ，私が作ったのよ";
        }
        if(nowwadai == "買"){
            reply = "今日の夕飯はカレーにしましょ";
        }
        return reply;
    }
    @Override
    public String Action() {
        String reply = "たしかに";
        
        database.showData();
        Stack wadai = (Stack)database.getData("Mode_Topic_Ryori_stack");
        System.out.println("現在の話題は："+wadai.peek());
        if (!chatLog.empty()) {
            String s1 = (String) chatLog.peek();//最後の会話ログを用いる
            reply = tekitou(s1);
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
