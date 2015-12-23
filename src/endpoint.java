@ServerEndpoint("/endpoint")
public class NewWSEndpoint {
    public NewWSEndpoint() throws IOException {
        this.timer = new Timer(1000, TimerAction);
        this.TimerAction = new TimerTick(outputter, database);
        this.outputter = new OutputController(database);
        this.inputter = new InputController(database);
        TimerAction.setController(timer);
        timer.start();
    }

    private static ArrayList<Session> sessionList = new ArrayList<Session>();
    DataBase database = new DataBase();

    InputController inputter;
    OutputController outputter;
    TimerTick TimerAction;
    Timer timer;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.toString()+"が接続しました");
        sessionList.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session.toString()+"が切断されました");
        sessionList.remove(session);
    }

    @OnMessage
    public void onMessage(String msg) throws IOException {
        System.out.println(msg);
        inputter.InputData("{'mode':'1','message':'" + msg + "','bui':''}");
        String json = outputter.getJson();
        System.out.println("クライアントへ送る：" + json);
        for (Session session : sessionList) {
            session.getBasicRemote().sendText(json);
        }
    }

    @OnMessage
    public void processUpload(byte[] b) throws UnsupportedEncodingException, IOException {
      System.out.println("Unityからの入力");
      System.out.println(b);
      String result = new String(b, "UTF-8");
      inputter.InputData(result);
      String json = outputter.getJson();
      for (Session session : sessionList) {
        session.getBasicRemote().sendText(json);
    }
}
