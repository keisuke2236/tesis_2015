
public class GoogleApi {

    DataBase database;
    LinkedHashMap<String, LinkedHashMap> googleResultsAllData = new LinkedHashMap<String, LinkedHashMap>();
    TextWriter tw;

    public GoogleApi(DataBase database) throws IOException {
        this.tw = new TextWriter();
        googleResultsAllData = tw.getHashMap();
        this.database = database;
    }

    public LinkedHashMap<String, LinkedHashMap> getGoogleResultALLData() {
        return googleResultsAllData;
    }

    public String serch(String str, int kensu, int mode) throws UnsupportedEncodingException, IOException {
        // Googleの検索用URL
        String url = "http://www.google.co.jp/search?&num=" + kensu + "&q=";

        //-----------------------------検索用キーワード生成----------------------------------
        Tokenizer.Builder builder = Tokenizer.builder();
        builder.mode(Tokenizer.Mode.SEARCH);
        Tokenizer search = builder.build();
        List<org.atilika.kuromoji.Token> tokensSearch = search.tokenize(str);
        String SearchString = "";

        //形態素解析を用いて適切な検索を行えるようにする
        for (org.atilika.kuromoji.Token token : tokensSearch) {
            if (!token.getPartOfSpeech().contains("助詞") && !token.getPartOfSpeech().contains("助動詞")) {
                SearchString += token.getSurfaceForm();
                SearchString += " ";
            }
        }

        url += URLEncoder.encode(SearchString, "utf-8");
        //--------------------------------------------------------------------------------
        //System.out.println(url);
        System.out.println(SearchString + "　　重複チェック：" + check(SearchString));
        System.out.println("検索しますAPI利用");

        // HttpClientでリクエスト
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<List<SearchModel>> handler = new GoogleSerchResultAnalyzer();
        List<SearchModel> list = client.execute(httpGet, handler);
        String ret = "\n";
        // 解析結果を見てみる
        for (SearchModel model : list) {
            switch (mode) {
                case 5://タイトルのみ
                    ret += model.getTitle();
                    ret += "\n";
                    break;

                case 6://URLのみ
                    ret += model.getHref();
                    ret += "\n";
                    break;

                case 3://内容のみ
                    ret += model.getDescription();
                    ret += "\n";
                    break;

                case 4://タイトルと内容
                    ret += model.getTitle();
                    ret += model.getDescription();
                    ret += "\n";
                    break;
                case 1://日常会話モード（日本語のみ利用する）
                    ret += model.getTitle();
                    ret += model.getDescription();
                    break;
                case 2://記号を含まない英数字を含む検索
                    ret += model.getTitle();
                    ret += model.getDescription();
                    break;
            }
        }
        //System.out.println("検索結果：" + ret);
        //無駄な文字をフィルタリングする
        if (mode == 1) {
            ret = ret.replaceAll("[a-zA-Z.∥-◯&;]", "");
            ret = filterling(ret);
        } else if (mode == 2) {
            ret = ret = ret.replaceAll("[.∥-◯&;]", "");
            ret = filterling(ret);
        }

        // 終了処理
        client.getConnectionManager().shutdown();
        return ret;
    }

    private String filterling(String ret) {
        ret = ret.replaceAll("日本", "");
        ret = ret.replaceAll("日前", "");
        ret = ret.replaceAll("キャッシュ", "");
        ret = ret.replaceAll("類似", "");
        ret = ret.replaceAll("http", "");
        ret = ret.replaceAll("//", "");
        ret = ret.replaceAll(":", "");
        ret = ret.replaceAll(".jp", "");
        return ret;

    }

    public String serch(String str) throws UnsupportedEncodingException, IOException {
        return serch(str, 2, 1);
    }

    public String serch(String str, int kensu) throws UnsupportedEncodingException, IOException {
        return serch(str, kensu, 1);
    }

    //こっちを使うとすでに検索済みの単語も一緒に獲得できる（便利）
    public LinkedHashMap<String, LinkedHashMap> searchWords_Merge(String[] searchs) throws IOException {
        searchWords(searchs);
        return googleResultsAllData;
    }

//文字列の配列を渡すと検索結果と検索ワードがマップになって帰ってくる便利ちゃん
    public LinkedHashMap<String, LinkedHashMap> searchWords(String[] searchs) throws IOException {
        LinkedHashMap<String, LinkedHashMap> googleResults = new LinkedHashMap<String, LinkedHashMap>();
        int threadNumber = searchs.length;
        // 8スレッド用意
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);

        // 結果を入れる配列
        LinkedHashMap<String, Integer>[] results = new LinkedHashMap[threadNumber];

        // タスクのリストを作る
        List<Callable<LinkedHashMap<String, Integer>>> tasks = new ArrayList<Callable<LinkedHashMap<String, Integer>>>();

        //System.out.println("同時に初期化するほうが圧倒的に早い");
        for (int i = 0; threadNumber > i; i++) {
            tasks.add(new ParallelInit(searchs[i], googleResultsAllData, this));
        }

        try {
            // 並列実行
            List<Future<LinkedHashMap<String, Integer>>> futures = null;
            try {
                futures = executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            //System.out.println("-----------");

            // 結果をresultsに入れる
            for (int i = 0; i < threadNumber; i++) {
                try {
                    results[i] = (futures.get(i)).get();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } finally {
            // 終了
            if (executor != null) {
                executor.shutdown();
            }

            int id = 0;
            // 結果の配列の中身
            for (LinkedHashMap<String, Integer> result : results) {
                System.out.println(searchs[id] + result);
                googleResults.put(searchs[id], result);
                id++;
            }
        }
System.out.println("第3");
        for (String key : googleResults.keySet()) {
            googleResultsAllData.put(key, googleResults.get(key));
        }
        System.out.println("第4");
        tw.saveHashMap(googleResultsAllData);
System.out.println("第5");
        //マージはしておくけど、今回のしか返さない
        return googleResults;
    }

    public boolean check(String str) {
        if (googleResultsAllData.containsKey(str)) {
            return true;
        }
        return false;
    }

    public boolean check(String[] str) {
        System.out.println("現在ある単語をチェック");
        for (String one : str) {
            System.out.println(one);
            if (googleResultsAllData.containsKey(one)) {
                System.out.println("はすでにある");
                return true;
            }
        }
        System.out.println("はない");
        return false;
    }

}

/**
 * Google検索の中身をHttpCleanerを使用して解析し、 検索結果のリンク先をList<String>で返す
 */
class GoogleSerchResultAnalyzer
        implements ResponseHandler<List<SearchModel>> {

    @Override
    public List<SearchModel> handleResponse(final HttpResponse response)
            throws HttpResponseException, IOException {
        StatusLine statusLine = response.getStatusLine();

        // ステータスが400以上の場合は、例外をthrow
        if (statusLine.getStatusCode() >= 400) {
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }

        // contentsの取得
        HttpEntity entity = response.getEntity();
        String contents = EntityUtils.toString(entity);

        // HtmlCleaner召還
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(contents);

        // 解析結果格納用リスト
        List<SearchModel> list = new ArrayList<SearchModel>();

        // まずliを対象にする
        TagNode[] liNodes = node.getElementsByName("li", true);
        for (TagNode liNode : liNodes) {
            // クラスがgじゃなかったら、多分リンクじゃないので除外
            if (!"g".equals(liNode.getAttributeByName("class"))) {
                continue;
            }

            SearchModel model = new SearchModel();

            // タイトルの取得
            TagNode h3Node = liNode.findElementByName("h3", false);
            if (h3Node == null) {
                continue;
            }
            model.setTitle(h3Node.getText().toString());

            // URLの取得
            TagNode aNode = h3Node.findElementByName("a", false);
            if (aNode == null) {
                continue;
            }
            model.setHref(aNode.getAttributeByName("href"));

            // 概要の取得
            TagNode divNode = liNode.findElementByName("div", false);
            if (divNode == null) {
                continue;
            }
            model.setDescription(divNode.getText().toString());

            list.add(model);
        }

        return list;
    }

}

/**
 * 検索結果格納クラス
 */
class SearchModel {

    String href;
    String title;
    String description;
    String div;
    String span;

    public String getHref() {
        return href;
    }

    public String getDiv() {
        return div;
    }

    public String getSpan() {
        return span;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public void setSpan(String span) {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

class ParallelInit implements Callable<LinkedHashMap<String, Integer>> {

    String search;
    LinkedHashMap<String, LinkedHashMap> googleResultsAllData;
    GoogleApi google, google2;
    WordCounter counter;
    DataBase database;

    public ParallelInit(String last, LinkedHashMap<String, LinkedHashMap> googleAll, GoogleApi google) throws IOException {
        this.counter = new WordCounter();
        search = last;
        googleResultsAllData = google.googleResultsAllData;
        this.google = google;
        this.database = google.database;
        database.setData("googleResultsAllData", googleResultsAllData);

    }

    //ここだけ並列処理できれば良い
    @Override
    public LinkedHashMap<String, Integer> call() throws Exception {
        //もし、過去に検索したことのある単語の場合検索を省略する

        google.googleResultsAllData = (LinkedHashMap<String, LinkedHashMap>) google.database.getData("googleResultsAllData");

        if (google.googleResultsAllData.containsKey(search)) {
            //System.out.println(search + "を検索しなくて省エネだよ！");
            return google.googleResultsAllData.get(search);
        }
                System.out.println("第0" + search);

        LinkedHashMap<String, Integer> wordcount = counter.wordcount(google.serch(search, 800), 0);
        System.out.println("第一");
        google.database.setData("googleResultsAllData", google.googleResultsAllData);
        System.out.println("第2");
        return wordcount;
    }

}
