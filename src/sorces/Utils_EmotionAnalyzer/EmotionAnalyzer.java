package Utils_EmotionAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/*
 * すべての感情を統括するエモーションアナライザー
 * 様々な感情とそれに対応する単語一覧表を所持している
 */
public class EmotionAnalyzer {

    
    HashMap<String, Feelings> map = new HashMap<String, Feelings>();//感情名とその感情オブジェクトを保存したMAP

    public EmotionAnalyzer() throws IOException {
        String filePath = "/Users/rorensu/NetBeansProjects/com.mycompany_websocket_war_1.0-SNAPSHOT_6/src/main/java/Utils_EmotionAnalyzer/Types/";
        File[] files = new File(filePath).listFiles();
        System.out.println(files.toString());
        for (int i = 0; i < files.length; i++) {
            String path = files[i].toString();
            String a = files[i].toString().substring(filePath.length());
            String feelName = a.substring(0, a.indexOf(".txt"));
            map.put(feelName, new Feelings(feelName, path));//ディレクトリにあるファイル名から新しい感情を生成
        }
    }

    
    public HashMap<String, Feelings> analyze(String say) {
        System.out.println(say);
        for (Map.Entry<String, Feelings> e : map.entrySet()) {
            e.getValue().calcurate(say);
        }
        return map;
    }
}
