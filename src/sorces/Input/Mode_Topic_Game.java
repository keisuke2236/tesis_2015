/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Database.DataBase;
import java.io.IOException;


public class Mode_Topic_Game extends Abstract_Mode_parts {

    public Mode_Topic_Game(DataBase database) throws IOException {
        super(database);
        about = "ゲーム";
    }

    private boolean match(String[] hairetu,String sagasu){
        for(String in :hairetu){
            if(in.indexOf(sagasu) != -1){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean ChatAnalyze(String chat) {
        //そう、例えばここでは話題を詰め込む
        String wadai = "";
        String[] make = {"勝","やった","倒","クリア"};
        if (match(make,chat) | wadai!="") {wadai = "勝利";}
        
        String[] PT = {"やばい","HP","倒せない","倒したい","MP","つらい","たのしい","つまらない","強い","弱い"};
        if (match(PT,chat)| wadai!="") {wadai = "戦闘中";}
        
        String[] junbi = {"何","育","レベル","強化","武器","レベリング","準備","街","NPC"};
        if (match(junbi,chat)| wadai!="") {wadai = "通常";}
        
        if(wadai==""){
            wadai="ゲーム";
        }
        
        System.out.println("解析結果"+wadai);
        stack.push(wadai);
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
        return 10;
    }


    
}
