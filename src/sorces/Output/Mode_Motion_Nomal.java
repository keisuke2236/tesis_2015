package Output;

import Database.DataBase;
import Database.MongoList;
import Utils_SystemFile.GoogleApi;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Mode_Motion_Nomal extends Abstract_Mode_parts {

    public Mode_Motion_Nomal(DataBase database) throws IOException {
        super(database);
        about = "日常会話フレーズ";
    }

    @Override
    public String Action() throws IOException {
        String motion = match();

        return motion;
    }

    private String match() {

        GoogleApi google = (GoogleApi) database.getData("google");
        LinkedHashMap<String, LinkedHashMap> googleResults = google.getGoogleResultALLData();
        HashMap<String, Integer> last = new HashMap<String, Integer>();
        //最後に格納した会話内容を取得する
        for (String key : googleResults.keySet()) {last = googleResults.get(key);} 

        /*MongoList MList = new MongoList();
        MList.SplitArray();
        HashMap<String, HashMap<String, HashMap<String, Integer>>> SuzukiDatabase = MList.getdbMap();
        */
        
        HashMap<String, HashMap<String, HashMap<String, Integer>>> SuzukiDatabase = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
        HashMap<String, HashMap<String, Integer>> tango = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, HashMap<String, Integer>> tango2 = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        HashMap<String, Integer> map2 = new HashMap<String, Integer>();

        //鈴木氏のデータベースから情報を持ってくることにする
        //すこしは日本語入力できるようになったかな
        //-----------ここから---------------
        map.put("パズドラ", 1000);
        map.put("速報", 5);
        map.put("エキドナ", 5);
        tango.put("パズドラ", map);
        SuzukiDatabase.put("JUMP00", tango);

        map2.put("艦これ", 1000);
        map2.put("速報", 5);
        map2.put("エキドナ", 5);
        tango2.put("艦これ", map2);
        SuzukiDatabase.put("WAIT02", tango2);
        System.out.println(SuzukiDatabase.get("JUMP00").get("パズドラ"));
        System.out.println(SuzukiDatabase.get("JUMP00").get("艦これ"));
        //--------------ここまでは設定------------------*/

        int score = 0;
        String name = "";
        
        System.out.println("鈴木の中身");
        for (String key : SuzukiDatabase.keySet()) {
            System.out.println(key);
        }

        for (String key : SuzukiDatabase.keySet()) {
            int thisscore = 0;
            HashMap<String, HashMap<String, Integer>> serchwords = SuzukiDatabase.get(key);
            for (String key2 : serchwords.keySet()) {
                System.out.println("    " + key2);
                HashMap<String, Integer> hikaku = serchwords.get(key2);
                for (String key3 : hikaku.keySet()) {
                    System.out.println(key3+":"+last.get(key3));
                    if (last.get(key3) != null) {
                        thisscore = Math.min(last.get(key3), hikaku.get(key3));
                    }
                }
            }
            if (score <= thisscore) {
                name = key;
                score = thisscore;
            }
        }
        return name;
    }

    @Override
    public int getTimeActionLevel() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String TimeAction() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
