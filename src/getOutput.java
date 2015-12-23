public String getOutput() throws IOException {
    google = googleUpdate();
    int height = 0;

    LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
    LinkedHashMap<String, LinkedHashMap> searchResult = new LinkedHashMap();

    Stack chatLog = (Stack) database.getData("Mode_ChatLog_SaveLog_stack");
    String last = chatLog.peek().toString();
    searchResult.put(last, counter.wordcount(google.serch(last, 10000), 0));
    for (String key1 : searchResult.keySet()) {
        LinkedHashMap<String, Integer> map1 = searchResult.get(key1);
        for (String key2 : googleResults.keySet()) {
            LinkedHashMap<String, Integer> map2 = googleResults.get(key2);
            for (String key4 : map1.keySet()) {
                for (String key3 : map2.keySet()) {
                    if (key3.equals(key4)) {
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