/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Database.DataBase;
import java.io.IOException;


public class Mode_Topic_Ryori extends Abstract_Mode_parts {

    public Mode_Topic_Ryori(DataBase database) throws IOException {
        super(database);
        about = "料理";
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
        String[] make = {"作","準備","お腹"};
        if (match(make,chat) | wadai!="") {wadai = "作";}
        
        String[] PT = {"食べよう","食事","お昼","夕飯","美味しい","まずい","まずい","満腹","いっぱい","食べれない","食べたい"};
        if (match(PT,chat)| wadai!="") {wadai = "食";}
        
        String[] junbi = {"何に","食べようかな","買","でかける","準備","手料理","支度","作る"};
        if (match(junbi,chat)| wadai!="") {wadai = "買";}
        if(wadai==""){
            wadai="料理";
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
