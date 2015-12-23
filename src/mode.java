package Input;

import Database.DataBase;
import Utils_SystemFile.GoogleApi;
import Utils_SystemFile.InputBasicData;
import Utils_SystemFile.WordCounter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

abstract public class Abstract_Mode {

    String modeName;
    InputBasicData inputData;
    DataBase database;
    LinkedList<Abstract_Mode_parts> analyzers;
    GoogleApi google;
    WordCounter counter = new WordCounter();
    LinkedHashMap<String, LinkedHashMap> googleResults;

    public Abstract_Mode(DataBase database) throws IOException {
        this.googleResults = new LinkedHashMap<String, LinkedHashMap>();
        this.database = database;

    }

    public void init() throws IOException {
        google = googleUpdate();
        String[] SearchStrings = new String[analyzers.size()];
        for (int i = 0; this.analyzers.size() > i; i++) {
            SearchStrings[i] = analyzers.get(i).about;
            if(SearchStrings[i]==""){
                SearchStrings[i]="人工知能　会話例";
            }
        }
        googleResults = google.searchWords(SearchStrings);
        database.setData("google", google);
    }

    public Abstract_Mode_parts getAnalyzeParts() throws IOException {
        google = googleUpdate();
        int height = 0;
        

        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, LinkedHashMap> searchResult = new LinkedHashMap();
        
        String last = inputData.getMessage();
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
        Input.Abstract_Mode_parts kotaeru = analyzers.get(0);
        //System.out.println("----------------結果-------------");
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
        for (Input.Abstract_Mode_parts tk : analyzers) {
            if (tk.about.equals(ansKey)) {
                kotaeru = analyzers.get(a);
            }
            a++;
        }
        System.out.println(kotaeru.about + "が解析します");
        database.setData("google", google);
        
        return kotaeru;
    }

    public void SetBasicData(InputBasicData inputData) {
        this.inputData = inputData;
    }

    //この入力モード（話しかけられた時など）の時に呼び出すべき解析クラスをanalyzersから選択する
    public void analyzeChat() throws IOException {
        
        Abstract_Mode_parts obj = this.getAnalyzeParts();
        if (obj.ChatAnalyze(inputData.getMessage())) {
            obj.saveData();
        }
    }

    public void analyzeTouch() throws IOException {
        
        Abstract_Mode_parts obj = this.getAnalyzeParts();
        if (obj.TouchAnalyze(inputData.getMessage())) {
            obj.saveData();
        }
    }

    public void analyzeAddObj() throws IOException {
        Abstract_Mode_parts obj = this.getAnalyzeParts();
        
        if (obj.addObjAnalyze(inputData.getMessage(), inputData.getBui())) {
            obj.saveData();
        }
    }

    private GoogleApi googleUpdate() throws IOException {
        google = (GoogleApi) database.getData("google");
        if (google == null) {
            google = new GoogleApi(database);
        }
        return google;
    }

}
