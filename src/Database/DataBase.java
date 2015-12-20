package Database;

import java.util.HashMap;

public class DataBase {

    boolean isEmpty = true;
    HashMap<String, Object> objList;
    int acsess = 0;

    public DataBase() {
        objList = new HashMap<String, Object>();
    }


    //データベースへ解析した結果を入れる

    public void setData(String className, Object value) {
        objList.put(className, value);
        acsess++;
        //同じクラスネームが来た場合異なる処理をするならここでするべき
    }

    //解析済みデータを取得する
    public Object getData(String key) {
        //showData();
        if(objList.get(key)==null){
            //System.out.println("Databaseより出力、getData("+key+")がしっぱいしました");
            return null;
        }
        return objList.get(key);
    }

    //全てのデータ表示
    public void showData() {
        
        System.out.println("------------------------------------");
        for (String key : objList.keySet()) {
            System.out.println("DB      :"+key);
        }
        System.out.println("------------------------------------");
        
        
    }
    
    
    //同じ種類の情報を合わせて制度の向上を行いたい場合は個々に記述する
    private void mergeData() {
        //System.out.println("マージコンプリート");
    }
    
    public int check(){
        if(!objList.isEmpty()){
            return 1;
        }
        if(acsess == 0){
            return 0;
        }

        return 0;
    }
}
