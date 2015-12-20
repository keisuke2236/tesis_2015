/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Database.DataBase;
import java.io.IOException;


public class Mode_Topic_PazzleAndDragons extends Abstract_Mode_parts {

    public Mode_Topic_PazzleAndDragons(DataBase database) throws IOException {
        super(database);
        about = "パズドラ";
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
        String[] kouryaku = {"降臨","クリア","コンボ","パズル","盤面","根性","先制","敵","HP","防御","スキル","LS","力","ドロップ"};
        if (match(kouryaku,chat) | wadai!="") {wadai = "攻略";}
        String[] PT = {"パテ","パーティー","編成","PT","チーム","総合HP","サブ","リーダー","要員"};
        if (match(PT,chat)| wadai!="") {wadai = "パーティー編成";}
        String[] clear = {"ワンパン","クリア","倒した","ノーコン"};
        if (match(clear,chat)| wadai!="") {wadai = "クリア";}
        String[] syukai = {"周回","高速","プラマラ","システム","効率","降臨","10倍","神々","星宝","魔窟","たまドラの里","高速","海原","コインダンジョン","降臨","ゲリラ","レベリング"};
        if (match(syukai,chat)| wadai!="") {wadai = "周回";}
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
