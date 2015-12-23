public class InputController {
       public InputController(DataBase database) throws IOException {
        AnalyzeMode = new LinkedList<Abstract_Mode>();
        //-----入力の種類別のモード----
        //話題解析
        AnalyzeMode.add(new Mode_Topic(database));
        //感情解析
        AnalyzeMode.add(new Mode_Feeling(database));
        //ログの保存
        AnalyzeMode.add(new Mode_ChatLog(database));
        //-----------------------------
    }
}
