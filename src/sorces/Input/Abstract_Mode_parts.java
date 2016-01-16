package Input;

import Database.DataBase;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

/**
 * これは入力系統のスーパークラス， InputControllerの下でうごくたくさんの入力系統クラスの
 */
abstract public class Abstract_Mode_parts {

    int level = 10;
    DataBase database;
    //解析結果保存用データ
    HashMap<String, String> stringMap = new HashMap();
    HashMap<String, Long> longMap = new HashMap();
    HashMap<String, Integer> integerMap = new HashMap();
    HashMap<String, Boolean> booleanMap = new HashMap();
    HashMap<String, Object> objectMap = new HashMap();
    HashMap<String, Double> doubleMap = new HashMap();
    HashMap<String, HashMap> maps = new HashMap();
    Stack stack = new Stack();

    String about;

    public Abstract_Mode_parts(DataBase database) throws IOException {
        this.database = database;
        about = "挨拶";
    }

    //解析したいレベルが帰る
    abstract public int getAnalyzeLevel();

    public int getAnalyzeLevelAuto() {
        return 0;
    }

    //ユーザに話しかけられた時の処理 正常に処理ができたらTrue
    abstract public boolean ChatAnalyze(String chat);

    //ユーザに触られた時の処理
    abstract public boolean TouchAnalyze(String bui);

    //何かしらのオブジェクトの追加があった時の処理
    abstract public boolean addObjAnalyze(String name, String bui);

    //処理でTrueを返した時に呼ばれる、現在存在するデータをすべて保存するメソッド
    public boolean saveData() {
        database.showData();
        String className = this.getClass().getSimpleName();
        if (!stringMap.isEmpty()) {
            database.setData(className + "_stringMap", stringMap);
            database.showData();
            return true;
        } else if (!longMap.isEmpty()) {
            database.setData(className + "_longMap", longMap);
            database.showData();
            return true;
        } else if (!integerMap.isEmpty()) {
            database.setData(className + "_integerMap", integerMap);
            return true;
        } else if (!booleanMap.isEmpty()) {
            database.setData(className + "_booleanMap", booleanMap);
            return true;
        } else if (!objectMap.isEmpty()) {
            database.setData(className + "_objectMap", objectMap);
            return true;
        } else if (!doubleMap.isEmpty()) {
            database.setData(className + "_doubleMap", doubleMap);
            return true;
        } else if (!maps.isEmpty()) {
            database.setData(className + "_maps", maps);
            return true;
        } else if (!stack.empty()) {
            database.setData(className + "_stack", this.stack);
            database.showData();
            return true;
        }

        return false;
    }

}
