
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

