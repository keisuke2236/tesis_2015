package Output;

import Database.DataBase;
import Utils_SystemFile.GoogleApi;
import Utils_SystemFile.WordCounter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Stack;

abstract public class Abstract_Mode {

    DataBase database;
    LinkedList<Abstract_Mode_parts> etcTalker;
    GoogleApi google;
    WordCounter counter = new WordCounter();
    LinkedHashMap<String, LinkedHashMap> googleResults;

    public Abstract_Mode(DataBase database) {
        this.database = database;
        etcTalker = new LinkedList<Abstract_Mode_parts>();

    }

    public void init() throws IOException {
        googleUpdate();
        String[] SearchStrings = new String[etcTalker.size()];
        for (int i = 0; this.etcTalker.size() > i; i++) {
            SearchStrings[i] = etcTalker.get(i).about;
        }
        //if(!google.check(SearchStrings)){
            googleResults = google.searchWords(SearchStrings);
            database.setData("google", google);
        //}
    }

    public String getOutput() throws IOException {
        System.out.println("出力に入ります");
        google = googleUpdate();
        int height = 0;

        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, LinkedHashMap> searchResult = new LinkedHashMap();
        
        Stack chatLog = (Stack) database.getData("Mode_ChatLog_SaveLog_stack");
        String last = chatLog.peek().toString();
        searchResult.put(last, counter.wordcount(google.serch(last, 10000), 0));
        //searchResult = google.searchWords(new String[]{last});
        for (String key1 : searchResult.keySet()) {
            LinkedHashMap<String, Integer> map1 = searchResult.get(key1);
            for (String key2 : googleResults.keySet()) {
                LinkedHashMap<String, Integer> map2 = googleResults.get(key2);
                for (String key4 : map1.keySet()) {
                    for (String key3 : map2.keySet()) {
                        if (key3.equals(key4)) {
                            //System.out.println(key4+":" + map2.get(key3));
                            height += Math.min(map1.get(key3), map2.get(key3));
                        }
                    }
                }
                result.put(key2, height);
                height = 0;
            }
        }
        Abstract_Mode_parts kotaeru = etcTalker.get(0);
        int a = 0;
        int most = 0;
        String ansKey = "";
        for (String key : result.keySet()) {
            System.out.println(key + " : " + result.get(key));
            if (most <= result.get(key)) {
                most = result.get(key);
                ansKey = key;
            }
        }
        
        for(Abstract_Mode_parts tk :etcTalker){
            if(tk.about.equals(ansKey)){
                kotaeru = etcTalker.get(a);
            }
            a++;
        }
        kotaeru.dataRefresh();
        database.setData("google", google);
        return kotaeru.Action();
    }

    public String getTimerAction() throws IOException {
        int mostKotaetai = 0;
        Abstract_Mode_parts kotaeru = etcTalker.get(0);
        kotaeru.dataRefresh();
        for (int i = 0; etcTalker.size() > i; i++) {
            etcTalker.get(i).dataRefresh();
            int thisKotaetai = etcTalker.get(i).getTimeActionLevel();
            if (mostKotaetai < thisKotaetai) {
                mostKotaetai = thisKotaetai;
                kotaeru = etcTalker.get(i);
            }
        }
        return kotaeru.TimeAction();
    }
    private GoogleApi googleUpdate() throws IOException {
        google = (GoogleApi) database.getData("google");
        if (google == null) {
            google = new GoogleApi(database);
        }
        return google;
    }

}

/*LinkedHashMap<String, Integer> map;
 System.out.println("一個づつやるよりも");
 googleResults = new LinkedHashMap<String, LinkedHashMap>();
 for (int i = 0; this.etcTalker.size() > i; i++) {
 String search = etcTalker.get(i).about;
 map = counter.wordcount(google.serch(search, 800), 0);
 googleResults.put(search, map);
 }*/

        //-----------------------------------------------------
