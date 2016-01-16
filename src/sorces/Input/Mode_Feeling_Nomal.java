/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Database.DataBase;
import Utils_EmotionAnalyzer.EmotionAnalyzer;
import Utils_EmotionAnalyzer.Feelings;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mode_Feeling_Nomal extends Abstract_Mode_parts {

    public Mode_Feeling_Nomal(DataBase database) throws IOException {
        super(database);
        about = "普通の会話";
    }

    @Override
    public int getAnalyzeLevel() {
        return 10;
    }

    @Override
    public boolean ChatAnalyze(String chat) {
        try {
            EmotionAnalyzer emo = new EmotionAnalyzer();
            HashMap<String, Feelings> analyze = emo.analyze(chat);
            HashMap<String, Integer> analyzed = new HashMap<String, Integer>();
            for (String key : analyze.keySet()) {
                analyzed.put(key, analyze.get(key).getPoint());
            }

            if (!integerMap.isEmpty()) {
                for (String key : analyzed.keySet()) {
                    if(analyzed.get(key)==0&&integerMap.get(key)>0){
                        integerMap.put(key, integerMap.get(key)-1);    
                    }else{
                        integerMap.put(key, analyzed.get(key)+integerMap.get(key));    
                    }   
                }   
            }else{
                integerMap = analyzed;
            }
            for(String key: integerMap.keySet()){
                System.out.println(key + ":" + integerMap.get(key));
            }
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Mode_Feeling_Nomal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean TouchAnalyze(String bui) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addObjAnalyze(String name, String bui) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
