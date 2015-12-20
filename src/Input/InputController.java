package Input;

import java.io.IOException;
import Database.DataBase;
import Utils_SystemFile.InputBasicData;
import java.util.LinkedList;
import javax.swing.Timer;
import net.arnx.jsonic.JSON;
/*
 このクラスは入力情報を解析知能に渡して，解析知能からreturnされた情報をデータベースに叩き込む
 */

public class InputController {
    Timer timer;
    DataBase database;
    InputBasicData basicData;
    
    LinkedList<Abstract_Mode> AnalyzeMode;
    //入力によるStatus変更クラス

       public InputController(DataBase database) throws IOException {
        this.database = database;
        
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




    //ユーザから何かしらインプットがあった時の処理
    public void InputData(String inputJson) throws IOException {
        //一時的にこうするだけ
        
        
        
        System.out.println("入力　　　　【" + inputJson + "】");
        basicData = JSON.decode(inputJson, InputBasicData.class);
        System.out.println("----------ALIVE-------------");
        //登録されているモードを実行
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
