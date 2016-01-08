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
    public void InputData(String inputJson) throws IOException {
        basicData = JSON.decode(inputJson, InputBasicData.class);
        for (int i = 0; AnalyzeMode.size() > i; i++) {
            AnalyzeMode.get(i).SetBasicData(basicData);
            switch(basicData.getMode()){
                case 1:
                    AnalyzeMode.get(i).analyzeChat();
                    break;
                case 2:
                    AnalyzeMode.get(i).analyzeTouch();
                    break;
                case 3:
                    AnalyzeMode.get(i).analyzeAddObj();
                    break;
            }
        }
    }
}
