public class WordCounter {

    HashMap<String, Integer> wordMap;
    public HashMap<String, Integer> wordcount(String text) {
       return wordcount(text,0);
   }
   public LinkedHashMap<String, Integer> wordcount(String text,int border) {
    wordMap = new HashMap<String, Integer>();
        // この２行で解析できる
    Tokenizer tokenizer = Tokenizer.builder().build();
    List<Token> tokens = tokenizer.tokenize(text);

        // 結果を出力してみる
    for (Token token : tokens) {
        if (token.getPartOfSpeech().contains("固有名詞") && !token.getPartOfSpeech().contains("助詞") && !token.getPartOfSpeech().contains("動詞") && !token.getPartOfSpeech().contains("副詞") && !token.getPartOfSpeech().contains("助動詞") && !token.getPartOfSpeech().contains("記号") && !token.getPartOfSpeech().contains("接頭詞")) {
            String word = token.getSurfaceForm();
                //System.out.println(token.getSurfaceForm() + " : " + token.getPartOfSpeech());
            if (wordMap.get(word) == null) {
                wordMap.put(word, 1);
            } else {
                wordMap.put(word, wordMap.get(word) + 1);
            }
        }
    }
    List<Map.Entry> entries = new ArrayList<Map.Entry>(wordMap.entrySet());
    Collections.sort(entries, new Comparator() {
        public int compare(Object o1, Object o2) {
            Map.Entry e1 = (Map.Entry) o1;
            Map.Entry e2 = (Map.Entry) o2;
            return ((Integer) e1.getValue()).compareTo((Integer) e2.getValue());
        }
    });
    LinkedHashMap<String,Integer> result = new LinkedHashMap<String,Integer>();
    for (Map.Entry entry : entries) {
        String key = entry.getKey().toString();
        int val = Integer.parseInt(entry.getValue().toString());
        if(val>border){
            result.put(key, val);
        }
    }
    return result;
}

