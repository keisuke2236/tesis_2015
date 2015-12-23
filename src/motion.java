public class Mode_Motion_Nomal extends Abstract_Mode_parts {
    @Override
    public String Action() throws IOException {
        String motion = match();
        return motion;
    }

    private String match() {
        GoogleApi google = (GoogleApi) database.getData("google");
        LinkedHashMap<String, LinkedHashMap> googleResults = google.getGoogleResultALLData();
        HashMap<String, Integer> last = new HashMap<String, Integer>();

        for (String key : googleResults.keySet()) {last = googleResults.get(key);}

        MongoList MList = new MongoList();
        MList.SplitArray();
        HashMap<String, HashMap<String, HashMap<String, Integer>>> SuzukiDatabase = MList.getdbMap();

        int score = 0;
        String name = "";

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
}
